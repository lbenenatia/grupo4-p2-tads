package um.edu.uy.entities;


import com.google.gson.*;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.cargadoresDeDatos.CargadorActoresDirectores;
import um.edu.uy.cargadoresDeDatos.CargadorEvaluaciones;
import um.edu.uy.cargadoresDeDatos.CargadorPeliculas;
import um.edu.uy.converter.ActorJson;
import um.edu.uy.converter.ColeccionJson;
import um.edu.uy.converter.DirectorJson;
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

    public UMovie() {
        this.peliculas = new Hashtable<>();
        this.colecciones = new Hashtable<>();
        this.directores = new Hashtable<>();
        this.generos = new Hashtable<>();
    }

    public void cargarDatos(){
        CargadorPeliculas cargadorPeliculas = new CargadorPeliculas(peliculas, colecciones, generos);
        cargadorPeliculas.cargar("movies_metadata.csv");

        CargadorEvaluaciones cargadorEvaluaciones = new CargadorEvaluaciones(peliculas);
        cargadorEvaluaciones.cargar("ratings_1mm.csv");

        CargadorActoresDirectores cargadorActoresDirectores = new CargadorActoresDirectores(peliculas, directores);
        cargadorActoresDirectores.cargar("credits.csv");
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
                        ///System.err.println("Ignorando línea " + numeroLinea + " debido a error en parseo.");
                        continue;
                    }

                    String coleccionJson = linea[1];
                    Gson gson = new Gson();

                    if (!coleccionJson.trim().isEmpty()) {
                        pelicula.setPerteneceAColeccion(true);
                        ColeccionJson coleccion = gson.fromJson(coleccionJson, ColeccionJson.class);
                        Coleccion coleccionExistente = colecciones.get(coleccion.getId()); //seria usar el pertence de hash
                        if (coleccionExistente == null) { //sería si es true
                            coleccionExistente = new Coleccion(coleccion.getId(), coleccion.getName());
                            colecciones.put(coleccionExistente.getId(), coleccionExistente);
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
                                generoExistente = new Genero(gJson.getId(), gJson.getName());
                                generos.put(gJson.getId(), generoExistente);
                            }
                            pelicula.agregarGenero(generoExistente);
                        }
                    }
                    peliculas.put(pelicula.getId(), pelicula);
                } catch (CsvValidationException | IOException e) {
                    System.err.println("Error al leer una línea del CSV.");
                } catch (Exception e) {
                    ///System.err.println("Error en línea " + numeroLinea + ": " + Arrays.toString(linea));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir o cerrar el archivo: " + nombreArchivo, e);
        }
    }
    public Pelicula mapLineaToPelicula(String[] linea) {
        try {
            Pelicula p = new Pelicula(Integer.parseInt(linea[5]), linea[18], linea[7], 0); ///Va a haber que pasarle parametros

            if (!linea[13].isBlank()) {
                p.setIngresos(Double.parseDouble(linea[13]));
            }
            return p;
        } catch (Exception e) {
            ///System.err.println("Error parseando línea: " + Arrays.toString(linea));
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

    public void cargarActoresDirectores(String nombreArchivo) {
        Map<Integer, Actor> actores; //Para no cargarlo en el programa principal
        actores = new Hashtable<>();
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
                    int idPelicula = Integer.parseInt(linea[2]);
                    Pelicula pelicula = peliculas.get(idPelicula); /// Se asume formato [{, ver como resolver [[
                    if (pelicula == null) {
                        ///System.err.println("Ignorando línea " + numeroLinea + " debido a que la pelicula no existe.");
                        continue;
                    }

                    Gson gson = new Gson();
                    DirectorJson parseador = new DirectorJson(directores);
                    Director director = parseador.convert(linea[1]);
                    if (director != null) {
                        director.agregarPelicula(pelicula);
                    }
                    String actoresJson = linea[0];

                    if (!actoresJson.trim().isEmpty()) {
                        JsonArray array = JsonParser.parseString(actoresJson.replace('\'','"')).getAsJsonArray();
                        for (JsonElement elem : array) {
                            ActorJson gJson = gson.fromJson(elem, ActorJson.class);
                            Actor actorExistente = actores.get(gJson.getId());
                            if (actorExistente == null) {
                                actorExistente = new Actor(gJson.getId(), gJson.getName());
                                actores.put(gJson.getId(), actorExistente);
                            }
                            actorExistente.agregarPelicula(pelicula); /// Ver si conviene esto o que se agreguen a una estructura desde el principio
                            pelicula.agregarActor(actorExistente);
                        }
                    }
                } catch (CsvValidationException | IOException e) {
                    System.err.println("Error al leer una línea del CSV.");
                } catch (Exception e) {
                    ///System.err.println("Error en línea " + numeroLinea + ": " + Arrays.toString(linea));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir o cerrar el archivo: " + nombreArchivo, e);
        }
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
            PeliculaPorEvaluaciones nueva = new PeliculaPorEvaluaciones(pelicula);
            if (idioma.equals("en")) {
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

    public PeliculaPorCalificacionMedia[] filtrarPorCalificacionMedia() {
        PeliculaPorCalificacionMedia[] top = new PeliculaPorCalificacionMedia[10];

        int posVacia = 0;

        for (Pelicula pelicula : peliculas.values()) {
            pelicula.calcularMedia();
            PeliculaPorCalificacionMedia nueva = new PeliculaPorCalificacionMedia(pelicula);
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

        for (int i = 0; i < 9; i++) {
            System.out.println(top10[i].getId() + ", " + top10[i].getTitulo() + ", " + top10[i].getCalificacionMedia());
        }
    }

    public Coleccion[] filtrarPorIngresos() {
        Coleccion[] top = new Coleccion[5];
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
                Coleccion coleccion = new Coleccion(pelicula.getId(), pelicula.getTitulo());
                coleccion.agregarPelicula(pelicula);
                if(posVacia < 5){
                    agregarOrdenado(coleccion, top, posVacia);
                    posVacia++;
                }
                if (coleccion.compareTo(top[4])>0) {
                    ordenarUltimo(top, 4);
                }
            }
        }
        return top;
    }

    public void top5Ingresos() {
        Coleccion[] top5 = filtrarPorIngresos();

        for (int i = 0; i < 4; i++) {
            Coleccion coleccion = top5[i];
            System.out.println(coleccion.getId() + ", " + coleccion.getTitulo() + ", " + coleccion.cantidadPeliculas() +
                    ", " + coleccion.idPeliculas() + ", " + coleccion.getIngresos());
        }
    }

    public Director[] filtrarDirectores() {
        Director[] top = new Director[10];
        int posVacia = 0;

        for (Director director : directores.values()) {
            if (director.cantidadPeliculas() > 1 && director.cantidadEvaluaciones() > 100) {
                director.calcularMediana();
                if (posVacia < 10) {
                    agregarOrdenado(director, top, posVacia);
                    posVacia++;
                } else {
                    if (director.getMediana() > top[9].getMediana()) {
                        top[9] = director;
                        ordenarUltimo(top, 9);
                    }
                }
            }
        }
        return top;
    }

    public void top10Directores() {
        Director[] top10 = filtrarDirectores();

        for (int i = 0; i < 10; i++) {
            System.out.println(top10[i].getNombre() + ", " + top10[i].cantidadPeliculas() + ", " +  top10[i].getMediana());
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

    public Map<Integer, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Map<Integer, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Map<Integer, Coleccion> getColecciones() {
        return colecciones;
    }

    public void setColecciones(Map<Integer, Coleccion> colecciones) {
        this.colecciones = colecciones;
    }

    public Map<Integer, Director> getDirectores() {
        return directores;
    }

    public void setDirectores(Map<Integer, Director> directores) {
        this.directores = directores;
    }

    public Map<Integer, Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(Map<Integer, Genero> generos) {
        this.generos = generos;
    }
}
