package um.edu.uy.consultas;

import um.edu.uy.entities.Director;
import um.edu.uy.entities.UMovie;
import um.edu.uy.tads.hash.HashTableL;

import static um.edu.uy.tads.Sorting.agregarOrdenado;
import static um.edu.uy.tads.Sorting.ordenarUltimo;

public class TopDirectores {
    public Director[] filtrarDirectores(UMovie umovie) {
        HashTableL<Integer, Director> directores = umovie.getDirectores();
        Director[] top = new Director[10];
        int posVacia = 0;

        for (Director director : directores.values()) {
            if (director.cantidadPeliculas() > 1 && director.cantidadEvaluaciones() > 100) {
                director.calcularMediana();
                if (posVacia < 10) {
                    agregarOrdenado(director, top, posVacia);
                    posVacia++;
                } else {
                    if (director.getMediana() > top[9].getMediana()) {
                        top[9] = director;
                        ordenarUltimo(top, 9);
                    }
                }
            }
        }
        return top;
    }

    public void ejecutar(UMovie umovie) {
        Director[] top10 = filtrarDirectores(umovie);

        for (int i = 0; i < 10; i++) {
            System.out.println(top10[i].getNombre() + ", " + top10[i].cantidadPeliculas() + ", " +  top10[i].getMediana());
        }
    }
}
