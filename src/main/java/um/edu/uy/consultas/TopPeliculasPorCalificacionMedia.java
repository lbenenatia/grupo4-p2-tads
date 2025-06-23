package um.edu.uy.consultas;

import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.PeliculaPorCalificacionMedia;
import um.edu.uy.entities.UMovie;

import java.util.Map;

import static um.edu.uy.tads.Sorting.agregarOrdenado;
import static um.edu.uy.tads.Sorting.ordenarUltimo;

public class TopPeliculasPorCalificacionMedia {
    public PeliculaPorCalificacionMedia[] filtrarPorCalificacionMedia(UMovie umovie) {
        Map<Integer, Pelicula> peliculas = umovie.getPeliculas();
        PeliculaPorCalificacionMedia[] top = new PeliculaPorCalificacionMedia[10];

        int posVacia = 0;

        for (Pelicula pelicula : peliculas.values()) {
            pelicula.calcularMedia();
            int cantidad = pelicula.cantidadEvaluaciones();
            PeliculaPorCalificacionMedia nueva = new PeliculaPorCalificacionMedia(pelicula);
            if (cantidad > 100) {
                if (posVacia < 10) {
                    agregarOrdenado(nueva, top, posVacia);
                    posVacia++;
                } else {
                    if (nueva.compareTo(top[9]) > 0) {
                        top[9] = nueva;
                        ordenarUltimo(top, 9);
                    }
                }
            }
        }
        return top;
    }

    public void ejecutar(UMovie umovie) {
        PeliculaPorCalificacionMedia[] top10 = filtrarPorCalificacionMedia(umovie);

        for (int i = 0; i < 9; i++) {
            System.out.println(top10[i].getId() + ", " + top10[i].getTitulo() + ", " + top10[i].getCalificacionMedia());
        }
    }
}
