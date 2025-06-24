package um.edu.uy.consultas;

import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.UMovie;

import java.util.Hashtable;
import java.util.Map;

public class TopActorPorMes {
    public Actor[] filtrarActores(UMovie uMovie){
        Map<Integer, Actor> actores = new Hashtable<>();
        Map<Integer, Pelicula> peliculas = uMovie.getPeliculas();
        Actor [] topActorMes = new Actor[12];
        for (Pelicula pelicula : peliculas.values()) {
            for (Actor actor : pelicula.getActores()) {
                if (!actores.containsValue(actor)) { /// si no pertenece
                    actores.put(actor.getIdActor(), actor);
                }
            }
        }
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
            System.out.println(meses[i] + top[i].getNombre() + top[i].getCantidadEvaluacionesPorMes().get(i) + top[i].cantidadPeliculasPorMes(i));
        }
    }
}
