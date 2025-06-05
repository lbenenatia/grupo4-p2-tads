package um.edu.uy.entities;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UMovie {
    private List<Pelicula> peliculas;
    private List<Coleccion> colecciones;

    public UMovie(List<Pelicula> peliculas) {
        this.peliculas = new ArrayList<>();
    }

    public void cargarPeliculas(String nombreArchivo) {
        try (FileReader fileReader = new FileReader(nombreArchivo)) {
            CsvToBean<Pelicula> csvToBean = new CsvToBeanBuilder<Pelicula>(fileReader).withType(Pelicula.class).withSkipLines(1).build();

            for (Pelicula pelicula : peliculas) {
                peliculas.add(pelicula);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Hacer el try/catch
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
