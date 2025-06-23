package um.edu.uy.cargadoresDeDatos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.converter.ColeccionJson;
import um.edu.uy.converter.GeneroJson;
import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.Pelicula;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class CargadorPeliculas {
    private final Map<Integer, Pelicula> peliculas;
    private final Map<Integer, Coleccion> colecciones;
    private final Map<Integer, Genero> generos;

    public CargadorPeliculas(Map<Integer, Pelicula> peliculas,
                             Map<Integer, Coleccion> colecciones,
                             Map<Integer, Genero> generos) {
        this.peliculas = peliculas;
        this.colecciones = colecciones;
        this.generos = generos;
    }

    public void cargar (String nombreArchivo) {
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

}
