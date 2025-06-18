package um.edu.uy.entities;


import com.google.gson.*;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.converter.GeneroJson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static um.edu.uy.tads.Sorting.*;

public class UMovie {
    private Map<Integer, Pelicula> peliculas;
    private Map<Integer, Coleccion> colecciones;
    private Map<Integer, Director> directores;
    private Map<Integer, Genero> generos;

    /// cuando haya que crear es mas eficiente para ver si existe

    public UMovie() {
        this.peliculas = new Hashtable<>(45500);
        this.colecciones = new HashMap<>();
        this.directores = new Hashtable<>();
        this.generos = new Hashtable<>();
    }

    public void cargarPeliculas(String nombreArchivo) {
        try (CSVReader reader = new CSVReader(new FileReader(nombreArchivo))) {
            try {
                reader.readNext(); // Suponiendo que hay encabezado
            } catch (CsvValidationException | IOException e) {
                throw new RuntimeException("Error al leer el encabezado del archivo CSV", e);
            }
            String[] linea = null;
            int numeroLinea = 1;
            while (true) {
                try {
                    linea = reader.readNext();
                    if (linea == null) {
                        break;
                    }
                    numeroLinea++;
                    Pelicula pelicula = mapLineaToPelicula(linea);
                    if (pelicula == null) {
                        System.err.println("Ignorando línea " + numeroLinea + " debido a error en parseo.");
                        continue;
                    }

                    String coleccionJson = linea[1];
                    Gson gson = new Gson();

                    if (!coleccionJson.trim().isEmpty()) {
                        pelicula.setPerteneceAColeccion(true);
                        Coleccion coleccion = gson.fromJson(coleccionJson, Coleccion.class);
                        Coleccion coleccionExistente = colecciones.get(coleccion.getId()); //seria usar el pertence de hash
                        if (coleccionExistente == null) { //sería si es true
                            colecciones.put(coleccion.getId(), coleccion);
                            coleccionExistente = coleccion;
                        }
                        coleccionExistente.agregarPelicula(pelicula);
                    }

                    String generosJson = linea[3];

                    if (!generosJson.trim().isEmpty()) {
                        JsonArray array = JsonParser.parseString(generosJson.replace('\'','"')).getAsJsonArray();
                        for (JsonElement elem : array) {
                            GeneroJson gJson = gson.fromJson(elem, GeneroJson.class);

                            Genero generoExistente = generos.get(gJson.getId());
                            if (generoExistente == null) {
                                generoExistente = new Genero();
                                generoExistente.setId(gJson.getId());
                                generoExistente.setNombre(gJson.getName());
                                generos.put(gJson.getId(), generoExistente);
                            }
                            pelicula.agregarGenero(generoExistente);
                        }
                    }
                    peliculas.put(pelicula.getId(), pelicula);
                } catch (CsvValidationException | IOException e) {
                    System.err.println("Error al leer una línea del CSV.");
                } catch (Exception e) {
                    System.err.println("Error en línea " + numeroLinea + ": " + Arrays.toString(linea));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir o cerrar el archivo: " + nombreArchivo, e);
        }
    }
    public Pelicula mapLineaToPelicula(String[] linea) {
        try {
            Pelicula p = new Pelicula(); ///Va a haber que pasarle parametros
            /*
            if (!linea[13].isBlank()) {
                p.setIngresos(Double.parseDouble(linea[13]));
            }

             */

            p.setId(Integer.parseInt(linea[5]));
            p.setTitulo(linea[18]);
            p.setIdiomaOriginal(linea[7]);
            if (!linea[13].isBlank()) {
                p.setIngresos(Double.parseDouble(linea[13]));
            } else {
                p.setIngresos(0.0);
            }
            return p;
        } catch (Exception e) {
            System.err.println("Error parseando línea: " + Arrays.toString(linea));
            return null; // Indica que esta línea no es válida
        }
    }

    public void cargarEvaluaciones(String nombreArchivo) {
        int evaluacionesNoValidas = 0;
        try (FileReader fileReader = new FileReader(nombreArchivo)) {
            CsvToBean<Evaluacion> csvToBean = new CsvToBeanBuilder<Evaluacion>(fileReader).withType(Evaluacion.class).withSkipLines(1).build();

            for (Evaluacion evaluacion : csvToBean) {
                try {
                    Pelicula peliculaEvaluada = peliculas.get(evaluacion.getIdPelicula());
                    if (peliculaEvaluada == null) {
                        evaluacionesNoValidas++;
                        ///System.err.println("Pelicula no encontrada para evaluación con ID: " + evaluacion.getIdPelicula());
                        continue; // Saltea la evaluación si la película no existe
                    }

                    peliculaEvaluada.agregarEvaluacion(evaluacion);

                    for (Genero genero : peliculaEvaluada.getGeneros()) {
                        genero.agregarEvaluacion(evaluacion);
                    }
                } catch (Exception e) {
                    System.err.println("Error al procesar una evaluación: " + evaluacion);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + nombreArchivo, e);
        } catch (IOException e) {
            throw new RuntimeException("Error de I/O al procesar el archivo: " + nombreArchivo, e);
        } catch (RuntimeException e) {
            ///Errores del CSVToBean
            System.err.println("Error al parsear el archivo CSV: " + e.getMessage());
            throw e;
        }
        ///System.out.println(evaluacionesNoValidas);
    }

    /// Falta cargar actores y directores del csv credits

    public PeliculaPorEvaluaciones convertirAPeliculaPorEvaluaciones(Pelicula p) {
        PeliculaPorEvaluaciones nueva = new PeliculaPorEvaluaciones();
        nueva.setId(p.getId());
        nueva.setTitulo(p.getTitulo());
        nueva.setIdiomaOriginal(p.getIdiomaOriginal());
        nueva.setEvaluaciones(p.getEvaluaciones());
        return nueva;
    }

    public ListaPeliculas filtrarPeliculasPorIdioma() {
        PeliculaPorEvaluaciones[] ingles = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] frances = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] espaniol = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] italiano = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] portugues = new PeliculaPorEvaluaciones[5];
        int posVaciaEn = 0;
        int posVaciaFr = 0;
        int posVaciaEs = 0;
        int posVaciaIt = 0;
        int posVaciaPt = 0;

        for (Pelicula pelicula : peliculas.values()) { /// En nuestro caso probablemente tengamos que recorrer con un i
            if (pelicula.cantidadEvaluaciones() == 0) continue;
            String idioma = pelicula.getIdiomaOriginal();
            if (idioma.equals("en")) {
                PeliculaPorEvaluaciones nueva = convertirAPeliculaPorEvaluaciones(pelicula);
                if (posVaciaEn < 5) {
                    agregarOrdenado(nueva, ingles, posVaciaEn);
                    posVaciaEn++;
                } else {
                    if (nueva.compareTo(ingles[4])>0) {
                        ingles[4] = nueva;
                        ordenarUltimo(ingles, 4);
                    }
                }
            }
            else if (idioma.equals("fr")) {
                PeliculaPorEvaluaciones nueva = convertirAPeliculaPorEvaluaciones(pelicula);
                if (posVaciaFr < 5) {
                    agregarOrdenado(nueva, frances, posVaciaFr);
                    posVaciaFr++;
                } else {
                    if (nueva.compareTo(frances[4])>0) {
                        frances[4] = nueva;
                        ordenarUltimo(frances, 4);
                    }
                }
            }
            else if (idioma.equals("es")) {
                PeliculaPorEvaluaciones nueva = convertirAPeliculaPorEvaluaciones(pelicula);
                if (posVaciaEs < 5) {
                    agregarOrdenado(nueva, espaniol, posVaciaEs);
                    posVaciaEs++;
                } else {
                    if (nueva.compareTo(espaniol[4])>0) {
                        espaniol[4] = nueva;
                        ordenarUltimo(espaniol, 4);
                    }
                }
            }
            else if (idioma.equals("it")) {
                PeliculaPorEvaluaciones nueva = convertirAPeliculaPorEvaluaciones(pelicula);
                if (posVaciaIt < 5) {
                    agregarOrdenado(nueva, italiano, posVaciaIt);
                    posVaciaIt++;
                } else {
                    if (nueva.compareTo(italiano[4])>0) {
                        italiano[4] = nueva;
                        ordenarUltimo(italiano, 4);
                    }
                }
            }
            else if (idioma.equals("pt")) {
                PeliculaPorEvaluaciones nueva = convertirAPeliculaPorEvaluaciones(pelicula);
                if (posVaciaPt < 5) {
                    agregarOrdenado(nueva, portugues, posVaciaPt);
                    posVaciaPt++;
                } else {
                    if (nueva.compareTo(portugues[4])>0) {
                        portugues[4] = nueva;
                        ordenarUltimo(portugues, 4);
                    }
                }
            }
        }
        return new ListaPeliculas(ingles, frances, espaniol, italiano, portugues);
    }

    public String top5PorIdioma() {
        ListaPeliculas top5 = filtrarPeliculasPorIdioma();
        return top5.toString();
    }
    /// Retocar el metodo
    public PeliculaPorCalificacionMedia convertirAPeliculaPorCalificacionMedia(Pelicula p) {
        PeliculaPorCalificacionMedia nueva = new PeliculaPorCalificacionMedia();
        nueva.setId(p.getId());
        nueva.setTitulo(p.getTitulo());
        nueva.setCalificacionMedia(p.getCalificacionMedia());
        nueva.setEvaluaciones(p.getEvaluaciones());
        return nueva;
    }

    public PeliculaPorCalificacionMedia[] filtrarPorCalificacionMedia() {
        PeliculaPorCalificacionMedia[] top = new PeliculaPorCalificacionMedia[10];

        int posVacia = 0;

        for (Pelicula pelicula : peliculas.values()) {
            pelicula.calcularMedia();
            PeliculaPorCalificacionMedia nueva = convertirAPeliculaPorCalificacionMedia(pelicula);
            if (posVacia < 10) {
                agregarOrdenado(nueva, top, posVacia);
                posVacia++;
            } else {
                if (nueva.compareTo(top[9])>0) {
                    top[9] = nueva;
                    ordenarUltimo(top, 9);
                }
            }
        }
        return top;
    }

    public void top10CalificacionMedia() {
        PeliculaPorCalificacionMedia[] top10 = filtrarPorCalificacionMedia();

        for (int i = 9; i >= 0; i--) {
            System.out.println(top10[i].getId() + ", " + top10[i].getTitulo() + ", " + top10[i].getCalificacionMedia());
        }
    }
    /*  Ajuste a esta parte NECESARIO
    public Ingresable[] filtrarPorIngresos() {
        Ingresable[] top = new Ingresable[5];
        int posVacia = 0;

        for (Coleccion coleccion : colecciones.values()) {
            if (posVacia < 5) {
                agregarOrdenado(coleccion, top, posVacia);
                posVacia++;
            } else {
                if (coleccion.compareTo(top[4])>0) {
                    top[4] = coleccion;
                    ordenarUltimo(top, 4);
                }
            }
        }
        for (Pelicula pelicula : peliculas.values()) {
            if (!pelicula.isPerteneceAColeccion()) {
                if(posVacia < 5){
                    agregarOrdenado(pelicula, top, posVacia);
                    posVacia++;
                }
                if (pelicula.compareTo(top[4])>0) {
                    ordenarUltimo(top, 4);
                }
            }
        }


        return top;


    }

    public void top5Ingresos() {
        Ingresable[] top5 = filtrarPorIngresos();

        for (int i = 4; i >= 0; i--) {
            if (top5[i] instanceof Pelicula) {
                Pelicula pelicula = (Pelicula) top5[i];
                System.out.println(pelicula.getId() + ", " + pelicula.getTitulo() + ", " + pelicula.getIngresos());
            } else {
                Coleccion coleccion = (Coleccion) top5[i];
                System.out.println(coleccion.getId() + ", " + coleccion.getTitulo() + ", " + coleccion.cantidadPeliculas() +
                        ", " + coleccion.idPeliculas() + ", " + coleccion.getIngresos());
            }
        }
    }

    public Director[] filtrarDirectores() {
        Director[] top = new Director[10];
        int posVacia = 0;

        for (Director director : directores.values()) {
            if (director.cantidadPeliculas() > 1 && director.cantidadEvaluaciones() > 100) {
                director.calcularMediana();

                if (posVacia < 10) {
                    top[posVacia] = director;
                    posVacia++;
                    // Reordenar
                } else {
                    if (director.getMediana() > top[0].getMediana()) {
                        top[0] = director;
                        // Reordenar
                    }
                }
            }
        }
        return top;
    }

    public void top10Directores() {
        Director[] top10 = filtrarDirectores();

        for (int i = 0; i < 10; i++) {
            System.out.println(top10[i].getNombre() + top10[i].cantidadPeliculas() + top10[i].getMediana());
        }
    }

    public Actor[] filtrarActores(){
        Map<Integer, Actor> actores = new Hashtable<>();
        Actor [] topActorMes = new Actor[12];
        for (Pelicula pelicula : peliculas.values()) {
            for (Actor actor : pelicula.getActores()) {
                if (!actores.containsValue(actor)) { /// si no pertenece
                    actores.put(actor.getIdActor(), actor);
                }
            }
        }
        for (Actor actor : actores.values()) {
            for (Pelicula pelicula : actor.getPeliculas()) {
                for (Evaluacion evaluacion : pelicula.getEvaluaciones()) {
                    int valorAntiguo = actor.getCantidadEvaluacionesPorMes().get(evaluacion.getFecha());
                    actor.getCantidadEvaluacionesPorMes().replace(evaluacion.getFecha(), valorAntiguo + 1);
                }
                for (int i = 1; i <= 12; i++) {
                    if (topActorMes[i] == null) {
                        topActorMes[i] = actor;
                    }
                    if (actor.getCantidadEvaluacionesPorMes().get(i) > topActorMes[i].getCantidadEvaluacionesPorMes().get(i)) {
                        topActorMes[i - 1] = actor;
                    }
                }
            }
        }
        return topActorMes;
    }

    public void topActorPorMes() {
        Actor[] top = filtrarActores();
        String[] meses = new String[12];
        meses[0] = "Enero";
        meses[1] = "Febrero";
        meses[2] = "Marzo";
        meses[3] = "Abril";
        meses[4] = "Mayo";
        meses[5] = "Junio";
        meses[6] = "Julio";
        meses[7] = "Agosto";
        meses[8] = "Septiembre";
        meses[9] = "Octubre";
        meses[10] = "Noviembre";
        meses[11] = "Diciembre";

        for (int i = 0; i < 12; i++) {
            System.out.println(meses[i] + top[i].getNombre() + top[i].getCantidadEvaluacionesPorMes().get(i) + top[i].cantidadPeliculasPorMes(i));
        }
    }


     */

}
