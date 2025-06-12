package um.edu.uy.entities;


import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static um.edu.uy.tads.Sorting.agregarOrdenado;
import static um.edu.uy.tads.Sorting.ordenarPrimero;

public class UMovie {
    private Map<Integer, Pelicula> peliculas;
    private Map<Integer, Coleccion> colecciones;
    private Map<Integer, Director> directores;

    /// cuando haya que crear es mas eficiente para ver si existe

    public UMovie() {
        this.peliculas = new Hashtable<>(45500);
        this.colecciones = new HashMap<>();
        this.directores = new Hashtable<>();
    }

    public void cargarPeliculas(String nombreArchivo) {
        try (CSVReader reader = new CSVReader(new FileReader(nombreArchivo))) {
            try {
                reader.readNext(); // Suponiendo que hay encabezado
            } catch (CsvValidationException | IOException e) {
                throw new RuntimeException("Error al leer el encabezado del archivo CSV", e);
            }
            String[] linea;
            while (true) {
                try {
                    linea = reader.readNext();
                    if (linea == null) {
                        break;
                    }
                    Pelicula pelicula = mapLineaToPelicula(linea);

                    String coleccionJson = linea[1];

                    if (!coleccionJson.trim().isEmpty()) {
                        pelicula.setPerteneceAColeccion(true);
                        Gson gson = new Gson();
                        Coleccion coleccion = gson.fromJson(coleccionJson, Coleccion.class);
                        Coleccion coleccionExistente = colecciones.get(coleccion.getId()); //seria usar el pertence de hash
                        if (coleccionExistente == null) { //sería si es true
                            colecciones.put(coleccion.getId(), coleccion);
                            coleccionExistente = coleccion;
                        }
                        coleccionExistente.agregarPelicula(pelicula);
                    }

                    /// No seria necesario agregar
                    peliculas.put(pelicula.getId(), pelicula);
                } catch (CsvValidationException | IOException e) {
                    System.err.println("Error al leer una línea del CSV.");
                } catch (Exception e) {
                    System.err.println("Error al procesar la linea (posiblemente formato incorrecto):");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al abrir o cerrar el archivo: " + nombreArchivo, e);
        }
    }

    public Pelicula mapLineaToPelicula(String[] linea) {
        Pelicula p = new Pelicula();
        p.setId(Integer.parseInt(linea[5]));
        p.setTitulo(linea[18]);
        p.setIdiomaOriginal(linea[7]);
        p.setIngresos(Double.parseDouble(linea[13]));
        return p;
    }

    public void cargarEvaluaciones(String nombreArchivo) {
        try (FileReader fileReader = new FileReader(nombreArchivo)) {
            CsvToBean<Evaluacion> csvToBean = new CsvToBeanBuilder<Evaluacion>(fileReader).withType(Evaluacion.class).withSkipLines(1).build();

            for (Evaluacion evaluacion : csvToBean) {
                try {
                    Pelicula peliculaEvaluada = peliculas.get(evaluacion.getIdPelicula());

                    if (peliculaEvaluada == null) {
                        System.err.println("Pelicula no encontrada para evaluación con ID: " + evaluacion.getIdPelicula());
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
    }

    /// Falta cargar actores y directores del csv credits

    public ListaPeliculas filtrarPeliculasPorIdioma() {
        //Usaría arrays de tamaño 5
        Pelicula[] ingles = new Pelicula[5];
        Pelicula[] frances = new Pelicula[5];
        Pelicula[] espaniol = new Pelicula[5];
        Pelicula[] italiano = new Pelicula[5];
        Pelicula[] portugues = new Pelicula[5];
        int posVaciaEn = 0;
        int posVaciaFr = 0;
        int posVaciaEs = 0;
        int posVaciaIt = 0;
        int posVaciaPt = 0;

        for (Pelicula pelicula : peliculas.values()) { /// En nuestro caso probablemente tengamos que recorrer con un i
            String idioma = pelicula.getIdiomaOriginal();
            if (idioma.equals("en")) {
                if (posVaciaEn < 5) {

                    //...Los primeros 5 tienen que estar ordenados...
                    agregarOrdenado(pelicula, ingles, posVaciaEn);
                    //ingles[posVaciaEn] = pelicula;
                    posVaciaEn++;
                } else {
                    if (pelicula.cantidadEvaluaciones() > ingles[0].cantidadEvaluaciones()) {
                        ingles[0] = pelicula;
                        ordenarPrimero(ingles);
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("fr")) {
                if (posVaciaFr < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    frances[posVaciaFr] = pelicula;
                    posVaciaFr++;
                } else {
                    if (pelicula.cantidadEvaluaciones() > frances[0].cantidadEvaluaciones()) {
                        frances[0] = pelicula;
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("es")) {
                if (posVaciaEs < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    ingles[posVaciaEs] = pelicula;
                    posVaciaEs++;
                } else {
                    if (pelicula.cantidadEvaluaciones() > espaniol[0].cantidadEvaluaciones()) {
                        espaniol[0] = pelicula;
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("it")) {
                if (posVaciaIt < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    ingles[posVaciaIt] = pelicula;
                    posVaciaIt++;
                } else {
                    if (pelicula.cantidadEvaluaciones() > italiano[0].cantidadEvaluaciones()) {
                        italiano[0] = pelicula;
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("pt")) {
                if (posVaciaPt < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    ingles[posVaciaPt] = pelicula;
                    posVaciaPt++;
                } else {
                    if (pelicula.cantidadEvaluaciones() > portugues[0].cantidadEvaluaciones()) {
                        portugues[0] = pelicula;
                        //...Reordenar...
                    }
                }
            }
        }
        return new ListaPeliculas(ingles, frances, espaniol, italiano, portugues);
    }

    public String top5PorIdioma() {
        return filtrarPeliculasPorIdioma().toString();
    }

    public Pelicula[] filtrarPorCalificacionMedia() {
        Pelicula[] top = new Pelicula[10];

        int posVacia = 0;

        for (Pelicula pelicula : peliculas.values()) {
            pelicula.calcularMedia();

            if (posVacia < 10) {
                top[posVacia] = pelicula;
                posVacia++;
            } else {
                if (pelicula.getCalificacionMedia() > top[0].getCalificacionMedia()) {
                    top[0] = pelicula;
                    // Reordenar
                }
            }
        }
        return top;
    }

    public void top10CalificacionMedia() {
        Pelicula[] top10 = filtrarPorCalificacionMedia();

        for (int i = 0; i < 10; i++) {
            System.out.println(top10[i].getId() + top10[i].getTitulo() + top10[i].getCalificacionMedia());
        }
    }

    public Ingresable[] filtrarPorIngresos() {
        Ingresable[] top = new Ingresable[5];
        int posVacia = 0;

        for (Coleccion coleccion : colecciones.values()) {
            if (posVacia < 5) {
                top[posVacia] = coleccion;
                posVacia++;
            } else {
                if (coleccion.getIngresos() > top[0].getIngresos()) {
                    top[0] = coleccion;
                    // Reordenar
                }
            }
        }

        for (Pelicula pelicula : peliculas.values()) {
            if (!pelicula.isPerteneceAColeccion()) {
                if (pelicula.getIngresos() > top[0].getIngresos()) {
                    top[0] = pelicula;
                    // Reordenar
                }
            }
        }
        return top;
    }

    public void top5Ingresos() {
        Ingresable[] top5 = filtrarPorIngresos();

        for (int i = 0; i < 5; i++) {
            if (top5[i] instanceof Pelicula) {
                Pelicula pelicula = (Pelicula) top5[i];
                System.out.println(pelicula.getId() + pelicula.getTitulo() + pelicula.getIngresos());
            } else {
                Coleccion coleccion = (Coleccion) top5[i];
                System.out.println(coleccion.getId() + coleccion.getTitulo() + coleccion.cantidadPeliculas() +
                        coleccion.idPeliculas() + coleccion.getIngresos());
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


}
