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
import um.edu.uy.tads.hash.HashTableL;

import java.io.FileReader;
import java.io.IOException;

public class CargadorPeliculas {
    private final HashTableL<Integer, Pelicula> peliculas;
    private final HashTableL<Integer, Coleccion> colecciones;
    private final HashTableL<Integer, Genero> generos;

    public CargadorPeliculas(HashTableL<Integer, Pelicula> peliculas,
                             HashTableL<Integer, Coleccion> colecciones,
                             HashTableL<Integer, Genero> generos) {
        this.peliculas = peliculas;
        this.colecciones = colecciones;
        this.generos = generos;
    }

    public void cargar (String nombreArchivo) {
        try (CSVReader reader = new CSVReader(new FileReader(nombreArchivo))) {
            try {
                reader.readNext(); // Suponiendo que hay encabezado
            } catch (CsvValidationException | IOException e) {
                System.err.println("Error al leer el encabezado del archivo CSV");
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
                    System.err.println("La línea está mal formateada");;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al abrir o cerrar el archivo: " + nombreArchivo);
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
            return null; // Indica que esta línea no es válida
        }
    }

}
