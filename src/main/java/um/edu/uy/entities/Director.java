package um.edu.uy.entities;

import java.util.ArrayList;
import java.util.List;

public class Director {
    private String nombre;
    private List<Pelicula> peliculas;
    private double mediana;

    public Director() {
        this.nombre = nombre;
        this.peliculas = new ArrayList<>();
        this.mediana = 0;
    }

    /// Precisamos tener la lista ordenada
    public void calcularMediana() { /// Preguntar si hacer mediana gral o a las medias de cada pelicula
        List<Double> listaPuntajes = new ArrayList<>();

        for (Pelicula p : this.peliculas) {
            for (Evaluacion e : p.getEvaluaciones()) {
                listaPuntajes.add(e.getPuntaje());
            }
        }

        if (listaPuntajes.size() % 2 == 1) {
            this.mediana = listaPuntajes.get((listaPuntajes.size() - 1) / 2 );
        }
        else {
            this.mediana = (listaPuntajes.get(listaPuntajes.size() / 2) + listaPuntajes.get((listaPuntajes.size() - 1) / 2 )) / 2;
        }
    }

    public int cantidadPeliculas() {
        return this.peliculas.size();
    }

    public int cantidadEvaluaciones() {
        int cant = 0;
        for (Pelicula pelicula : this.peliculas) {
            cant += pelicula.cantidadEvaluaciones();
        }
        return cant;
    }

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

    public double getMediana() {
        return mediana;
    }

    public void setMediana(double mediana) {
        this.mediana = mediana;
    }
}
