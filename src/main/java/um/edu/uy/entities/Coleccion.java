package um.edu.uy.entities;

import java.util.List;

public class Coleccion {
    private int id;
    private List<Pelicula> peliculas;
    private double ingresos;
    private String titulo;

    public Coleccion() {
        this.id = id;
        this.peliculas = peliculas;
        this.ingresos = 0;
        this.titulo = titulo;
    }

    public void calcularIngresos() {
        for (Pelicula p : this.peliculas) {
            this.ingresos += p.getIngresos();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
