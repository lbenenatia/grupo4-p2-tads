package um.edu.uy.cargadoresDeDatos;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Director;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.tads.hash.HashTableL;
import um.edu.uy.tads.hash.HashTableLinkedL;

import java.io.FileReader;
import java.io.IOException;

public class CargadorActoresDirectores {
    private final HashTableL<Integer, Pelicula> peliculas;
    private final HashTableL<Integer, Director> directores;
    private final HashTableL<Integer, Actor> actores;

    public CargadorActoresDirectores(HashTableL<Integer, Pelicula> peliculas, HashTableL<Integer,
                                    Director> directores, HashTableL<Integer, Actor> actores) {
        this.peliculas = peliculas;
        this.directores = directores;
        this.actores = actores;
    }

    public void cargar(String nombreArchivo) {
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
            System.err.println("Error al leer archivo: " + nombreArchivo);
        }
    }

    private void procesarActores(String entrada, Pelicula pelicula) {
        int pos = 0;
        HashTableL<Integer,Boolean> actoresVistos = new HashTableLinkedL<>();

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

}
