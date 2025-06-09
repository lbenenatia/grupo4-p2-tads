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

public class UMovie {
    private Map<Integer,Pelicula> peliculas;
    private Map<Integer,Coleccion> colecciones; ///cuando haya que crear es mas eficiente para ver si existe

    public UMovie(List<Pelicula> peliculas) {
        this.peliculas = new Hashtable<>(45500);
        this.colecciones = new HashMap<>();
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
                    System.err.println("Error al procesar la línea (posiblemente formato incorrecto):");
                }
            }
        }
        catch (IOException e) {
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
        }
    catch (FileNotFoundException e) {
        throw new RuntimeException("Archivo no encontrado: " + nombreArchivo, e);
    } catch (IOException e) {
        throw new RuntimeException("Error de I/O al procesar el archivo: " + nombreArchivo, e);
    } catch (RuntimeException e) {
        ///Errores del CSVToBean
        System.err.println("Error al parsear el archivo CSV: " + e.getMessage());
        throw e;
    }
    }

    public ListaPeliculas filtrarPeliculas() {
        //Usaría arrays de tamaño 5
        List<Pelicula> ingles = new ArrayList<>();
        List<Pelicula> frances = new ArrayList<>();
        List<Pelicula> espaniol = new ArrayList<>();
        List<Pelicula> italiano = new ArrayList<>();
        List<Pelicula> portugues = new ArrayList<>();

        for (Pelicula pelicula : peliculas.values()) { /// En nuestro caso probablemente tengamos que recorrer con un i
            String idioma = pelicula.getIdiomaOriginal();

            if (idioma.equals("en")) {
                if (ingles.size() < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    ingles.add(pelicula);
                } else {
                    if (pelicula.getEvaluaciones().size() > ingles.getFirst().getEvaluaciones().size()) {
                        ingles.removeFirst();
                        ingles.add(pelicula);
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("fr")) {
                if (frances.size() < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    frances.add(pelicula);
                } else {
                    if (pelicula.getEvaluaciones().size() > frances.getFirst().getEvaluaciones().size()) {
                        frances.removeFirst();
                        frances.add(pelicula);
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("es")) {
                if (espaniol.size() < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    espaniol.add(pelicula);
                } else {
                    if (pelicula.getEvaluaciones().size() > espaniol.getFirst().getEvaluaciones().size()) {
                        espaniol.removeFirst();
                        espaniol.add(pelicula);
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("it")) {
                if (italiano.size() < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    italiano.add(pelicula);
                } else {
                    if (pelicula.getEvaluaciones().size() > italiano.getFirst().getEvaluaciones().size()) {
                        italiano.removeFirst();
                        italiano.add(pelicula);
                        //...Reordenar...
                    }
                }
            }
            if (idioma.equals("pt")) {
                if (portugues.size() < 5) {
                    //...Los primeros 5 tienen que estar ordenados...
                    portugues.add(pelicula);
                } else {
                    if (pelicula.getEvaluaciones().size() > portugues.getFirst().getEvaluaciones().size()) {
                        portugues.removeFirst();
                        portugues.add(pelicula);
                        //...Reordenar...
                    }
                }
            }
        }
        return new ListaPeliculas(ingles, frances, espaniol, italiano, portugues);
    }

    public String top5Evaluaciones() {
        return filtrarPeliculas().toString();
    }
}
