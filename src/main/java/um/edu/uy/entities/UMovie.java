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

        System.out.println("Peliculas: " + peliculas.size());
        System.out.println("Colecciones: " + colecciones.size());
        System.out.println("Directores: " + directores.size());
        System.out.println("Generos: " + generos.size());
        System.out.println("Actores: " + actores.size());
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

    public void setColecciones(HashTableL<Integer, Coleccion> colecciones) {
        this.colecciones = colecciones;
    }

    public HashTableL<Integer, Director> getDirectores() {
        return directores;
    }

    public void setDirectores(HashTableL<Integer, Director> directores) {
        this.directores = directores;
    }

    public HashTableL<Integer, Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(HashTableL<Integer, Genero> generos) {
        this.generos = generos;
    }

    public HashTableL<Integer, Actor> getActores() {
        return actores;
    }

    public void setActores(HashTableL<Integer, Actor> actores) {
        this.actores = actores;
    }
}
