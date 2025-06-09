package um.edu.uy.entities;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UMovie {
    private List<Pelicula> peliculas;
    private Map<Integer,Coleccion> colecciones; ///cuando haya que crear es mas eficiente para ver si existe

    public UMovie(List<Pelicula> peliculas) {
        this.peliculas = new ArrayList<>(); // Usar hash
        this.colecciones = new HashMap<>();
    }

    public void cargarPeliculas(String nombreArchivo) {
        try (CSVReader reader = new CSVReader(new FileReader(nombreArchivo))) {
            String[] linea;
            try { //Ver esto
                reader.readNext();
            } catch (CsvValidationException e) {
                throw new RuntimeException(e);
            }
            while ((linea = reader.readNext()) != null) { //ver manejo de excepción
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

                /// Habría que hacer lo mismo con el género para crearlo, asignarlo y agregarlo a una estructura
                peliculas.add(pelicula);
            }
        }


        CsvToBean<Pelicula> csvToBean = new CsvToBeanBuilder<Pelicula>(fileReader).withType(Pelicula.class).withSkipLines(1).build();

        for (Pelicula pelicula : csvToBean) {
            this.peliculas.add(pelicula);
        }
    catch(IOException e) {
        e.printStackTrace();
    }
        //Hacer el try/catch
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
                for (Pelicula pelicula : peliculas) {
                    if (evaluacion.getIdPelicula() == pelicula.getId()) {
                        pelicula.getEvaluaciones().add(evaluacion);
                        for (Genero genero : pelicula.getGeneros()) {
                            genero.getEvaluaciones().add(evaluacion);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ListaPeliculas filtrarPeliculas() {
        //Usaría arrays de tamaño 5
        List<Pelicula> ingles = new ArrayList<>();
        List<Pelicula> frances = new ArrayList<>();
        List<Pelicula> espaniol = new ArrayList<>();
        List<Pelicula> italiano = new ArrayList<>();
        List<Pelicula> portugues = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
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
