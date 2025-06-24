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
    private Map<Integer, Actor> actores;

    public UMovie() {
        this.peliculas = new Hashtable<>();
        this.colecciones = new Hashtable<>();
        this.directores = new Hashtable<>();
        this.generos = new Hashtable<>();
        this.actores = new Hashtable<>();
    }

    public void cargarDatos(){
        CargadorPeliculas cargadorPeliculas = new CargadorPeliculas(peliculas, colecciones, generos);
        cargadorPeliculas.cargar("movies_metadata.csv");

        CargadorEvaluaciones cargadorEvaluaciones = new CargadorEvaluaciones(peliculas);
        cargadorEvaluaciones.cargar("ratings_1mm.csv");

        CargadorActoresDirectores cargadorActoresDirectores = new CargadorActoresDirectores(peliculas, directores, actores);
        cargadorActoresDirectores.cargar("credits.csv");

        System.out.println("Peliculas: " + peliculas.size());
        System.out.println("Colecciones: " + colecciones.size());
        System.out.println("Directores: " + directores.size());
        System.out.println("Generos: " + generos.size());
        System.out.println("Actores: " + actores.size());
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

    public Map<Integer, Actor> getActores() {
        return actores;
    }

    public void setActores(Map<Integer, Actor> actores) {
        this.actores = actores;
    }
}
