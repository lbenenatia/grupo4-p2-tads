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
        cargadorPeliculas.cargar("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\movies_metadata.csv");

        CargadorEvaluaciones cargadorEvaluaciones = new CargadorEvaluaciones(peliculas);
        cargadorEvaluaciones.cargar("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\ratings_1mm.csv");

        CargadorActoresDirectores cargadorActoresDirectores = new CargadorActoresDirectores(peliculas, directores, actores);
        cargadorActoresDirectores.cargar2("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\credits.csv");
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
