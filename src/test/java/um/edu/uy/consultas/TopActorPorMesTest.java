package um.edu.uy.consultas;

import org.junit.jupiter.api.Test;
import um.edu.uy.entities.Actor;
import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Pelicula;
import um.edu.uy.entities.UMovie;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TopActorPorMesTest {

    @Test
    public void testFiltrarActores_unSoloActorUnaEvaluacion() {
        // Crear evaluación en enero (mes 0)
        Evaluacion eval = new Evaluacion();
        eval.setFecha(0);

        // Crear película
        Pelicula pelicula = new Pelicula(1, "Test Movie", "Español", 1000.0);
        pelicula.getEvaluaciones().add(eval);

        // Crear actor
        Actor actor = new Actor(1, "Juan");
        actor.getPeliculas().add(pelicula);

        // Crear mapa de actores
        Map<Integer, Actor> actores = Map.of(1, actor);

        // Crear UMovie
        UMovie umovie = new UMovie();
        umovie.setActores(actores);

        // Ejecutar
        TopActorPorMes top = new TopActorPorMes();
        Actor[] topActores = top.filtrarActores(umovie);

        // Verificaciones
        assertNotNull(topActores[0]);
        assertEquals("Juan", topActores[0].getNombre());
        for (int i = 1; i < 12; i++) {
            assertNotNull(topActores[i]);
        }
    }

    @Test
    public void testFiltrarActores_dosActoresConDiferenteCantidad() {
        // Actor A con 2 evaluaciones en marzo
        Evaluacion evalA1 = new Evaluacion();
        evalA1.setFecha(2);
        Evaluacion evalA2 = new Evaluacion();
        evalA2.setFecha(2);

        Pelicula peliA = new Pelicula(1, "Peli A", "Español", 1500.0);
        peliA.getEvaluaciones().addAll(List.of(evalA1, evalA2));
        Actor actorA = new Actor(1, "Ana");
        actorA.getPeliculas().add(peliA);

        // Actor B con 1 evaluación en marzo
        Evaluacion evalB = new Evaluacion();
        evalB.setFecha(2);

        Pelicula peliB = new Pelicula(2, "Peli B", "Español", 500.0);
        peliB.getEvaluaciones().add(evalB);
        Actor actorB = new Actor(2, "Bruno");
        actorB.getPeliculas().add(peliB);

        // Crear mapa de actores
        Map<Integer, Actor> actores = Map.of(1, actorA, 2, actorB);
        UMovie umovie = new UMovie();
        umovie.setActores(actores);

        // Ejecutar
        TopActorPorMes top = new TopActorPorMes();
        Actor[] topActores = top.filtrarActores(umovie);

        assertEquals("Ana", topActores[2].getNombre()); // marzo
    }
}
