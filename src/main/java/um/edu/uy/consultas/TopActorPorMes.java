package um.edu.uy.consultas;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.UMovie;
import um.edu.uy.tads.hash.HashTableL;

public class TopActorPorMes {
    public Actor[] filtrarActores(UMovie uMovie){
        HashTableL<Integer, Actor> actores = uMovie.getActores();
        Actor [] topActorMes = new Actor[12];

        for (Actor actor : actores.values()) {
            for (Pelicula pelicula : actor.getPeliculas()) {
                for (Evaluacion evaluacion : pelicula.getEvaluaciones()) {
                    int valorAntiguo = actor.getCantidadEvaluacionesPorMes().get(evaluacion.getFecha());
                    actor.getCantidadEvaluacionesPorMes().replace(evaluacion.getFecha(), valorAntiguo + 1);
                }
                for (int i = 0; i <= 11; i++) {
                    if (topActorMes[i] == null) {
                        topActorMes[i] = actor;
                    }
                    else if (actor.getCantidadEvaluacionesPorMes().get(i) > topActorMes[i].getCantidadEvaluacionesPorMes().get(i)) {
                        topActorMes[i] = actor;
                    }
                }
            }
        }
        return topActorMes;
    }

    public void ejecutar(UMovie umovie) {
        Actor[] top = filtrarActores(umovie);
        String[] meses = new String[12];
        meses[0] = "Enero";
        meses[1] = "Febrero";
        meses[2] = "Marzo";
        meses[3] = "Abril";
        meses[4] = "Mayo";
        meses[5] = "Junio";
        meses[6] = "Julio";
        meses[7] = "Agosto";
        meses[8] = "Septiembre";
        meses[9] = "Octubre";
        meses[10] = "Noviembre";
        meses[11] = "Diciembre";

        for (int i = 0; i < 12; i++) {
            System.out.println(meses[i] + ", " + top[i].getNombre() + ", " + top[i].getCantidadEvaluacionesPorMes().get(i) + ", " + top[i].cantidadPeliculasPorMes(i));
        }
    }
}
