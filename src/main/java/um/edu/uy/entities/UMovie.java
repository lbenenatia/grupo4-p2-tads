package um.edu.uy.entities;
import um.edu.uy.cargadoresDeDatos.CargadorActoresDirectores;
import um.edu.uy.cargadoresDeDatos.CargadorEvaluaciones;
import um.edu.uy.cargadoresDeDatos.CargadorPeliculas;
import java.util.*;


public class UMovie {
    private Map<Integer, Pelicula> peliculas;
    private Map<Integer, Coleccion> colecciones;
    private Map<Integer, Director> directores;
    private Map<Integer, Genero> generos;

    public UMovie() {
        this.peliculas = new Hashtable<>();
        this.colecciones = new Hashtable<>();
        this.directores = new Hashtable<>();
        this.generos = new Hashtable<>();
    }

    public void cargarDatos(){
        CargadorPeliculas cargadorPeliculas = new CargadorPeliculas(peliculas, colecciones, generos);
        cargadorPeliculas.cargar("movies_metadata.csv");

        CargadorEvaluaciones cargadorEvaluaciones = new CargadorEvaluaciones(peliculas);
        cargadorEvaluaciones.cargar("ratings_1mm.csv");

        CargadorActoresDirectores cargadorActoresDirectores = new CargadorActoresDirectores(peliculas, directores);
        cargadorActoresDirectores.cargar("credits.csv");
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

    public Map<Integer, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(Map<Integer, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Map<Integer, Coleccion> getColecciones() {
        return colecciones;
    }

    public void setColecciones(Map<Integer, Coleccion> colecciones) {
        this.colecciones = colecciones;
    }

    public Map<Integer, Director> getDirectores() {
        return directores;
    }

    public void setDirectores(Map<Integer, Director> directores) {
        this.directores = directores;
    }

    public Map<Integer, Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(Map<Integer, Genero> generos) {
        this.generos = generos;
    }
}
