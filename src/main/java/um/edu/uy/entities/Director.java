package um.edu.uy.entities;

import java.util.ArrayList;
import java.util.List;

import static um.edu.uy.tads.Sorting.mergeSort;

public class Director implements Comparable<Director> {
    private int id;
    private String nombre;
    private List<Pelicula> peliculas;
    private double mediana;

    public Director(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.peliculas = new ArrayList<>();
        this.mediana = 0;
    }

    /// Precisamos tener la lista ordenada
    public void calcularMediana() { /// Preguntar si hacer mediana gral o a las medias de cada pelicula
        int cantidadPuntajes = 0;
        for (Pelicula p : peliculas) {
            for (Evaluacion e : p.getEvaluaciones()) {
                cantidadPuntajes++;
            }
        }
        Double[] listaPuntajes = new Double[cantidadPuntajes];
        int posVacia = 0;

        for (Pelicula p : this.peliculas) {
            for (Evaluacion e : p.getEvaluaciones()) {
                listaPuntajes[posVacia] = e.getPuntaje();
                posVacia++;
            }
        }

        listaPuntajes = mergeSort(listaPuntajes);

        if (cantidadPuntajes % 2 == 1) {
            this.mediana = listaPuntajes[(cantidadPuntajes-1) / 2];
        }
        else {
            this.mediana = (listaPuntajes[cantidadPuntajes / 2] + listaPuntajes[(cantidadPuntajes / 2)-1])/2;
        }
    }

    public void agregarPelicula(Pelicula p) {
        this.peliculas.add(p);
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

    @Override
    public int compareTo(Director otroDirector) {
        return Double.compare(this.mediana, otroDirector.mediana);
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
