package um.edu.uy.consultas;

import um.edu.uy.entities.ListaPeliculas;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.PeliculaPorEvaluaciones;
import um.edu.uy.entities.UMovie;
import um.edu.uy.tads.hash.HashTableL;

import static um.edu.uy.tads.Sorting.agregarOrdenado;
import static um.edu.uy.tads.Sorting.ordenarUltimo;

public class TopPeliculasPorIdioma {
    public ListaPeliculas filtrarPorIdioma(UMovie umovie){
        HashTableL<Integer, Pelicula> peliculas = umovie.getPeliculas();
        PeliculaPorEvaluaciones[] ingles = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] frances = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] espaniol = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] italiano = new PeliculaPorEvaluaciones[5];
        PeliculaPorEvaluaciones[] portugues = new PeliculaPorEvaluaciones[5];
        int posVaciaEn = 0;
        int posVaciaFr = 0;
        int posVaciaEs = 0;
        int posVaciaIt = 0;
        int posVaciaPt = 0;

        for (Pelicula pelicula : peliculas.values()) {
            String idioma = pelicula.getIdiomaOriginal();
            PeliculaPorEvaluaciones nueva = new PeliculaPorEvaluaciones(pelicula);
            if (idioma.equals("en")) {
                if (posVaciaEn < 5) {
                    agregarOrdenado(nueva, ingles, posVaciaEn);
                    posVaciaEn++;
                } else {
                    if (nueva.compareTo(ingles[4])>0) {
                        ingles[4] = nueva;
                        ordenarUltimo(ingles, 4);
                    }
                }
            }
            else if (idioma.equals("fr")) {
                if (posVaciaFr < 5) {
                    agregarOrdenado(nueva, frances, posVaciaFr);
                    posVaciaFr++;
                } else {
                    if (nueva.compareTo(frances[4])>0) {
                        frances[4] = nueva;
                        ordenarUltimo(frances, 4);
                    }
                }
            }
            else if (idioma.equals("es")) {
                if (posVaciaEs < 5) {
                    agregarOrdenado(nueva, espaniol, posVaciaEs);
                    posVaciaEs++;
                } else {
                    if (nueva.compareTo(espaniol[4])>0) {
                        espaniol[4] = nueva;
                        ordenarUltimo(espaniol, 4);
                    }
                }
            }
            else if (idioma.equals("it")) {
                if (posVaciaIt < 5) {
                    agregarOrdenado(nueva, italiano, posVaciaIt);
                    posVaciaIt++;
                } else {
                    if (nueva.compareTo(italiano[4])>0) {
                        italiano[4] = nueva;
                        ordenarUltimo(italiano, 4);
                    }
                }
            }
            else if (idioma.equals("pt")) {
                if (posVaciaPt < 5) {
                    agregarOrdenado(nueva, portugues, posVaciaPt);
                    posVaciaPt++;
                } else {
                    if (nueva.compareTo(portugues[4])>0) {
                        portugues[4] = nueva;
                        ordenarUltimo(portugues, 4);
                    }
                }
            }
        }
        return new ListaPeliculas(ingles, frances, espaniol, italiano, portugues);
    }

    public String ejecutar(UMovie umovie){
        ListaPeliculas top5 = filtrarPorIdioma(umovie);
        return top5.toString();
    }
}
