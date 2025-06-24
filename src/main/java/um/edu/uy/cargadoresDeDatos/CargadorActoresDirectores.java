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
    private final Map<Integer, Actor> actores;

    public CargadorActoresDirectores(Map<Integer, Pelicula> peliculas, Map<Integer,
                                    Director> directores, Map<Integer, Actor> actores) {
        this.peliculas = peliculas;
        this.directores = directores;
        this.actores = actores;
    }

    public void cargar2(String nombreArchivo) {
        try (CSVReader reader = new CSVReader(new FileReader(nombreArchivo))) {
            reader.readNext(); // Leer encabezado

            String[] linea;
            int numeroLinea = 1;

            while ((linea = reader.readNext()) != null) {
                numeroLinea++;

                if (linea.length < 3) continue;

                int idPelicula;
                try {
                    idPelicula = Integer.parseInt(linea[2]);
                } catch (NumberFormatException e) {
                    continue;
                }

                Pelicula pelicula = peliculas.get(idPelicula);
                if (pelicula == null) continue;

                String actoresRaw = linea[0];
                String equipoRaw = linea[1];

                if (actoresRaw != null && !actoresRaw.isEmpty()) {
                    procesarActores(actoresRaw, pelicula);
                }

                if (equipoRaw != null && equipoRaw.contains("'job': 'Director'")) {
                    procesarDirectores(equipoRaw, pelicula);
                }
            }

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Error al leer archivo: " + nombreArchivo, e);
        }
    }

    private void procesarActores(String entrada, Pelicula pelicula) {
        int pos = 0;
        Map<Integer,Boolean> actoresVistos = new Hashtable<>();

        while ((pos = entrada.indexOf("'id':", pos)) != -1) {
            try {
                int inicioId = pos + 6;
                int finId = entrada.indexOf(",", inicioId);
                int id = Integer.parseInt(entrada.substring(inicioId, finId).trim());

                int inicioNombre = entrada.indexOf("'name': '", finId);
                if (inicioNombre == -1) break;
                inicioNombre += 9;
                int finNombre = entrada.indexOf("'", inicioNombre);
                if (finNombre == -1) break;

                String nombre = entrada.substring(inicioNombre, finNombre);

                if (!actoresVistos.containsKey(id)) {
                    actoresVistos.put(id, true);
                    Actor actor = actores.get(id);
                    if (actor == null) {
                        actor = new Actor(id, nombre);
                        actores.put(id, actor);
                    }

                    actor.agregarPelicula(pelicula);
                    pelicula.agregarActor(actor);
                }

                pos = finNombre + 1;
            } catch (Exception e) {
                pos++; // Saltar al siguiente caracter si algo falla
            }
        }
    }

    private void procesarDirectores(String entrada, Pelicula pelicula) {
        int pos = 0;

        while ((pos = entrada.indexOf("'job': 'Director'", pos)) != -1) {
            try {
                int inicioId = entrada.lastIndexOf("'id':", pos);
                if (inicioId == -1) break;
                inicioId += 6;
                int finId = entrada.indexOf(",", inicioId);
                int id = Integer.parseInt(entrada.substring(inicioId, finId).trim());

                int inicioNombre = entrada.indexOf("'name': '", finId);
                if (inicioNombre == -1) break;
                inicioNombre += 9;
                int finNombre = entrada.indexOf("'", inicioNombre);
                if (finNombre == -1) break;

                String nombre = entrada.substring(inicioNombre, finNombre);

                Director director = directores.get(id);
                if (director == null) {
                    director = new Director(id, nombre);
                    directores.put(id, director);
                }

                director.agregarPelicula(pelicula);
                pos = finNombre + 1;

            } catch (Exception e) {
                pos++; // Continúa si hay error
            }
        }
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
