package um.edu.uy.cargadoresDeDatos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.converter.ActorJson;
import um.edu.uy.converter.DirectorJson;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

public class CargadorActoresDirectores {
    private final Map<Integer, Pelicula> peliculas;
    private final Map<Integer, Director> directores;

    public CargadorActoresDirectores(Map<Integer, Pelicula> peliculas, Map<Integer,
                                    Director> directores) {
        this.peliculas = peliculas;
        this.directores = directores;
    }

    public void cargar(String nombreArchivo) {
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

}
