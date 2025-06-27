package um.edu.uy.consultas;

import org.junit.jupiter.api.Test;
import um.edu.uy.entities.*;
import um.edu.uy.tads.hash.HashTableL;
import um.edu.uy.tads.hash.HashTableLinkedL;

import static org.junit.jupiter.api.Assertions.*;

class TopPeliculasPorIdiomaTest {

    /// Se hará solo este test por lo tedioso que es crear material suficiente para poder realizar la consulta en condiciones óptimas

    @Test
    public void testFiltrarPorIdioma() {
        UMovie umovie = new UMovie();
        HashTableL<Integer, Pelicula> hashPeliculas = new HashTableLinkedL<>();

        Pelicula p1 = new Pelicula(1, "Inception", "en", 100);
        Pelicula p2 = new Pelicula(2, "Matrix", "en", 150);
        Pelicula p3 = new Pelicula(3, "Amelie", "fr", 70);
        Pelicula p4 = new Pelicula(4, "Intouchables", "fr", 120);
        Pelicula p5 = new Pelicula(5, "Roma", "es", 50);
        Pelicula p6 = new Pelicula(6, "El Secreto de sus Ojos", "es", 80);
        Pelicula p7 = new Pelicula(7, "La Vita è Bella", "it", 200);
        Pelicula p8 = new Pelicula(8, "Cidade de Deus", "pt", 110);

        //Solo interesa registrar la evaluacion puesto que interesa contar
        // p1: 2 evaluaciones
        Evaluacion e1 = new Evaluacion();
        e1.setIdUsuario(1);
        e1.setIdPelicula(p1.getId());
        e1.setPuntaje(4.5);
        e1.setFecha(20240101);
        p1.agregarEvaluacion(e1);

        Evaluacion e2 = new Evaluacion();
        e2.setIdUsuario(2);
        e2.setIdPelicula(p1.getId());
        e2.setPuntaje(4.5);
        e2.setFecha(20240102);
        p1.agregarEvaluacion(e2);


// p2: 3 evaluaciones
        Evaluacion e3 = new Evaluacion();
        e3.setIdUsuario(3);
        e3.setIdPelicula(p2.getId());
        e3.setPuntaje(4.8);
        e3.setFecha(20240103);
        p2.agregarEvaluacion(e3);

        Evaluacion e4 = new Evaluacion();
        e4.setIdUsuario(4);
        e4.setIdPelicula(p2.getId());
        e4.setPuntaje(4.7);
        e4.setFecha(20240104);
        p2.agregarEvaluacion(e4);

        Evaluacion e5 = new Evaluacion();
        e5.setIdUsuario(5);
        e5.setIdPelicula(p2.getId());
        e5.setPuntaje(4.9);
        e5.setFecha(20240105);
        p2.agregarEvaluacion(e5);


// p3: 1 evaluación
        Evaluacion e6 = new Evaluacion();
        e6.setIdUsuario(6);
        e6.setIdPelicula(p3.getId());
        e6.setPuntaje(4.6);
        e6.setFecha(20240106);
        p3.agregarEvaluacion(e6);


// p4: 4 evaluaciones
        Evaluacion e7 = new Evaluacion();
        e7.setIdUsuario(7);
        e7.setIdPelicula(p4.getId());
        e7.setPuntaje(5.0);
        e7.setFecha(20240107);
        p4.agregarEvaluacion(e7);

        Evaluacion e8 = new Evaluacion();
        e8.setIdUsuario(8);
        e8.setIdPelicula(p4.getId());
        e8.setPuntaje(4.9);
        e8.setFecha(20240108);
        p4.agregarEvaluacion(e8);

        Evaluacion e9 = new Evaluacion();
        e9.setIdUsuario(9);
        e9.setIdPelicula(p4.getId());
        e9.setPuntaje(4.8);
        e9.setFecha(20240109);
        p4.agregarEvaluacion(e9);

        Evaluacion e10 = new Evaluacion();
        e10.setIdUsuario(10);
        e10.setIdPelicula(p4.getId());
        e10.setPuntaje(4.7);
        e10.setFecha(20240110);
        p4.agregarEvaluacion(e10);


// p5: 1 evaluación
        Evaluacion e11 = new Evaluacion();
        e11.setIdUsuario(11);
        e11.setIdPelicula(p5.getId());
        e11.setPuntaje(4.7);
        e11.setFecha(20240111);
        p5.agregarEvaluacion(e11);


// p6: 2 evaluaciones
        Evaluacion e12 = new Evaluacion();
        e12.setIdUsuario(12);
        e12.setIdPelicula(p6.getId());
        e12.setPuntaje(4.8);
        e12.setFecha(20240112);
        p6.agregarEvaluacion(e12);

        Evaluacion e13 = new Evaluacion();
        e13.setIdUsuario(13);
        e13.setIdPelicula(p6.getId());
        e13.setPuntaje(5.0);
        e13.setFecha(20240113);
        p6.agregarEvaluacion(e13);


// p7: 3 evaluaciones
        Evaluacion e14 = new Evaluacion();
        e14.setIdUsuario(14);
        e14.setIdPelicula(p7.getId());
        e14.setPuntaje(4.8);
        e14.setFecha(20240114);
        p7.agregarEvaluacion(e14);

        Evaluacion e15 = new Evaluacion();
        e15.setIdUsuario(15);
        e15.setIdPelicula(p7.getId());
        e15.setPuntaje(4.8);
        e15.setFecha(20240115);
        p7.agregarEvaluacion(e15);

        Evaluacion e16 = new Evaluacion();
        e16.setIdUsuario(16);
        e16.setIdPelicula(p7.getId());
        e16.setPuntaje(4.8);
        e16.setFecha(20240116);
        p7.agregarEvaluacion(e16);


// p8: 3 evaluaciones
        Evaluacion e17 = new Evaluacion();
        e17.setIdUsuario(17);
        e17.setIdPelicula(p8.getId());
        e17.setPuntaje(4.7);
        e17.setFecha(20240117);
        p8.agregarEvaluacion(e17);

        Evaluacion e18 = new Evaluacion();
        e18.setIdUsuario(18);
        e18.setIdPelicula(p8.getId());
        e18.setPuntaje(4.7);
        e18.setFecha(20240118);
        p8.agregarEvaluacion(e18);

        Evaluacion e19 = new Evaluacion();
        e19.setIdUsuario(19);
        e19.setIdPelicula(p8.getId());
        e19.setPuntaje(4.6);
        e19.setFecha(20240119);
        p8.agregarEvaluacion(e19);

        PeliculaPorEvaluaciones p11 = new PeliculaPorEvaluaciones(p1);
        PeliculaPorEvaluaciones p12 = new PeliculaPorEvaluaciones(p2);
        PeliculaPorEvaluaciones p13 = new PeliculaPorEvaluaciones(p3);
        PeliculaPorEvaluaciones p14 = new PeliculaPorEvaluaciones(p4);
        PeliculaPorEvaluaciones p15 = new PeliculaPorEvaluaciones(p5);
        PeliculaPorEvaluaciones p16 = new PeliculaPorEvaluaciones(p6);
        PeliculaPorEvaluaciones p17 = new PeliculaPorEvaluaciones(p7);
        PeliculaPorEvaluaciones p18 = new PeliculaPorEvaluaciones(p8);


        umovie.getPeliculas().put(1, p12);
        umovie.getPeliculas().put(2, p13);
        umovie.getPeliculas().put(3, p14);
        umovie.getPeliculas().put(4, p15);
        umovie.getPeliculas().put(5, p16);
        umovie.getPeliculas().put(6, p17);
        umovie.getPeliculas().put(7, p18);
        umovie.getPeliculas().put(8, p11);


        TopPeliculasPorIdioma top = new TopPeliculasPorIdioma();
        ListaPeliculas lista = top.filtrarPorIdioma(umovie);

        assertEquals(p12, lista.getIngles()[0]);
        assertEquals(p11, lista.getIngles()[1]);

        assertEquals(p14, lista.getFrances()[0]);
        assertEquals(p13, lista.getFrances()[1]);

        assertEquals(p16, lista.getEspaniol()[0]);
        assertEquals(p15, lista.getEspaniol()[1]);

        assertEquals(p17, lista.getItaliano()[0]);

        assertEquals(p18, lista.getPortugues()[0]);

    }
}