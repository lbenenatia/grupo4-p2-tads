package um.edu.uy.cargadoresDeDatos;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.HashTableL;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CargadorEvaluaciones {
    private final HashTableL<Integer, Pelicula> peliculas;

    public CargadorEvaluaciones(HashTableL<Integer, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void cargar(String nombreArchivo) {
        try (FileReader fileReader = new FileReader(nombreArchivo)) {
            CsvToBean<Evaluacion> csvToBean = new CsvToBeanBuilder<Evaluacion>(fileReader).withType(Evaluacion.class).withSkipLines(1).build();

            for (Evaluacion evaluacion : csvToBean) {
                try {
                    Pelicula peliculaEvaluada = peliculas.get(evaluacion.getIdPelicula());
                    if (peliculaEvaluada == null) {
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
    }
}
