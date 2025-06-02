package main.java.um.edu.uy.entities;

import java.util.ArrayList;
import java.util.List;

public class Director {
    private String nombre;
    private List<Pelicula> peliculas;
    private double mediana;

    public Director() {
        this.nombre = nombre;
        this.peliculas = peliculas;
        this.mediana = 0;
    }

    /// Precisamos tener la lista ordenada
    public void calcularMediana() {
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

}
