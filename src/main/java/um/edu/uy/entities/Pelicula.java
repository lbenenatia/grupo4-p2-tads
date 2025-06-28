package um.edu.uy.entities;

import um.edu.uy.tads.linkedlist.LinkedListL;
import um.edu.uy.tads.linkedlist.ListaL;

public class Pelicula implements Comparable<Pelicula> {
    private Integer id;
    private String titulo;
    private String idiomaOriginal;
    private double ingresos;
    private ListaL<Genero> generos;
    private boolean perteneceAColeccion = false;
    private ListaL<Actor> actores;
    private double calificacionMedia;
    private ListaL<Evaluacion> evaluaciones;

    public Pelicula(Integer id, String titulo, String idiomaOriginal, double ingresos) { /// Va a haber que agregarle las cosas necesarias al constructor (GENERA PROBLEMAS)
        this.id = id;
        this.titulo = titulo;
        this.idiomaOriginal = idiomaOriginal;
        this.ingresos = ingresos;
        this.generos = new LinkedListL<>();
        this.calificacionMedia = 0;
        this.perteneceAColeccion = perteneceAColeccion;
        this.evaluaciones = new LinkedListL<>();
        this.actores = new LinkedListL<>();
    }

    public void calcularMedia() {
        if(this.evaluaciones.isEmpty()) {
            return;
        }
        double puntajeTotal = 0;
        for (Evaluacion e : this.evaluaciones) {
            puntajeTotal += e.getPuntaje();
        }

        this.calificacionMedia = puntajeTotal / this.evaluaciones.size();
    }

    public void agregarEvaluacion(Evaluacion e) {
        this.evaluaciones.add(e);
    }

    public void agregarActor(Actor actor) {
        this.actores.add(actor);
    }

    public void agregarGenero(Genero g) {
        this.generos.add(g);
    }

    public int cantidadEvaluaciones() {
        return this.getEvaluaciones().size();
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public ListaL<Genero> getGeneros() {
        return generos;
    }

    public double getCalificacionMedia() {
        return calificacionMedia;
    }

    public void setCalificacionMedia(double calificacionMedia) {
        this.calificacionMedia = calificacionMedia;
    }

    public boolean isPerteneceAColeccion() {
        return perteneceAColeccion;
    }

    public void setPerteneceAColeccion(boolean perteneceAColeccion) {
        this.perteneceAColeccion = perteneceAColeccion;
    }

    public ListaL<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(ListaL<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    @Override
    public int compareTo(Pelicula otraPelicula) {
        return Double.compare(this.getCalificacionMedia(), otraPelicula.getCalificacionMedia());
    }
}
