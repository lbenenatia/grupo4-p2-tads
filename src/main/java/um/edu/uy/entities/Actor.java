package main.java.um.edu.uy.entities;

import java.util.List;

public class Actor {
    private String nombre;
    private List<Pelicula> peliculas;

    public Actor() {
        this.peliculas = peliculas;
        this.nombre = nombre;
    }

    /// Ver tema tiempo/mes de las peliculas

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }
}
