package um.edu.uy.consultas;

import um.edu.uy.entities.Coleccion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.UMovie;
import um.edu.uy.tads.hash.HashTableL;

import static um.edu.uy.tads.Sorting.agregarOrdenado;
import static um.edu.uy.tads.Sorting.ordenarUltimo;

public class TopColeccionPorIngreso {
    public Coleccion[] filtrarPorIngresos(UMovie umovie) {
        HashTableL<Integer, Pelicula> peliculas = umovie.getPeliculas();
        HashTableL<Integer, Coleccion> colecciones = umovie.getColecciones();
        Coleccion[] top = new Coleccion[5];
        int posVacia = 0;

        for (Coleccion coleccion : colecciones.values()) {
            if (posVacia < 5) {
                agregarOrdenado(coleccion, top, posVacia);
                posVacia++;
            } else {
                if (coleccion.compareTo(top[4])>0) {
                    top[4] = coleccion;
                    ordenarUltimo(top, 4);
                }
            }
        }
        for (Pelicula pelicula : peliculas.values()) {
            if (!pelicula.isPerteneceAColeccion()) {
                Coleccion coleccion = new Coleccion(pelicula.getId(), pelicula.getTitulo());
                coleccion.agregarPelicula(pelicula);
                if(posVacia < 5){
                    agregarOrdenado(coleccion, top, posVacia);
                    posVacia++;
                }
                if (coleccion.compareTo(top[4])>0) {
                    ordenarUltimo(top, 4);
                }
            }
        }
        return top;
    }

    public void ejecutar(UMovie umovie) {
        Coleccion[] top5 = filtrarPorIngresos(umovie);

        for (int i = 0; i < 5; i++) {
            Coleccion coleccion = top5[i];
            System.out.println(coleccion.getId() + ", " + coleccion.getTitulo() + ", " + coleccion.cantidadPeliculas() +
                    ", " + coleccion.idPeliculas() + ", " + coleccion.getIngresos());
        }
    }
}
