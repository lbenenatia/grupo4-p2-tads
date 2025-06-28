package um.edu.uy.entities;

import um.edu.uy.cargadoresDeDatos.CargadorActoresDirectores;
import um.edu.uy.cargadoresDeDatos.CargadorEvaluaciones;
import um.edu.uy.cargadoresDeDatos.CargadorPeliculas;
import um.edu.uy.tads.hash.HashTableL;
import um.edu.uy.tads.hash.HashTableLinkedL;


public class UMovie {
    private HashTableL<Integer, Pelicula> peliculas;
    private HashTableL<Integer, Coleccion> colecciones;
    private HashTableL<Integer, Director> directores;
    private HashTableL<Integer, Genero> generos;
    private HashTableL<Integer, Actor> actores;

    public UMovie() {
        this.peliculas = new HashTableLinkedL<>();
        this.colecciones = new HashTableLinkedL<>();
        this.directores = new HashTableLinkedL<>();
        this.generos = new HashTableLinkedL<>();
        this.actores = new HashTableLinkedL<>();
    }

    public void cargarDatos(){
        CargadorPeliculas cargadorPeliculas = new CargadorPeliculas(peliculas, colecciones, generos);
        cargadorPeliculas.cargar("movies_metadata.csv");

        CargadorEvaluaciones cargadorEvaluaciones = new CargadorEvaluaciones(peliculas);
        cargadorEvaluaciones.cargar("ratings_1mm.csv");

        CargadorActoresDirectores cargadorActoresDirectores = new CargadorActoresDirectores(peliculas, directores, actores);
        cargadorActoresDirectores.cargar("credits.csv");
    }

    public HashTableL<Integer, Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(HashTableL<Integer, Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public HashTableL<Integer, Coleccion> getColecciones() {
        return colecciones;
    }

    public HashTableL<Integer, Director> getDirectores() {
        return directores;
    }

    public HashTableL<Integer, Genero> getGeneros() {
        return generos;
    }

    public HashTableL<Integer, Actor> getActores() {
        return actores;
    }
}
