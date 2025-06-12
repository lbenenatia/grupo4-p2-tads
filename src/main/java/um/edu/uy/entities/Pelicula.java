package um.edu.uy.entities;

import java.util.ArrayList;
import java.util.List;

public class Pelicula implements Comparable<Pelicula>, Ingresable {
    private int id;
    private String titulo;
    private String idiomaOriginal;
    private double ingresos;
    private List<Genero> generos;
    private boolean perteneceAColeccion = false;
    private List<Actor> actores;
    private double calificacionMedia;
    private List<Evaluacion> evaluaciones;

    public Pelicula() {
        this.id = id;
        this.titulo = titulo;
        this.idiomaOriginal = idiomaOriginal;
        this.ingresos = ingresos;
        this.generos = new ArrayList<>();
        this.calificacionMedia = 0;
        this.perteneceAColeccion = perteneceAColeccion;
        this.evaluaciones = new ArrayList<>();
        this.evaluaciones = evaluaciones;
    }

    public void calcularMedia() {
        double puntajeTotal = 0;
        for (Evaluacion e : this.evaluaciones) {
            puntajeTotal += e.getPuntaje();
        }

        this.calificacionMedia = puntajeTotal / this.evaluaciones.size();
    }

    public void agregarEvaluacion(Evaluacion e) {
        this.evaluaciones.add(e);
    }

    public int cantidadEvaluaciones() {
        return this.getEvaluaciones().size();
    }

    @Override
    public int compareTo(Pelicula otraPelicula) {
        return Double.compare(this.calificacionMedia, otraPelicula.calificacionMedia);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    @Override
    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
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

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }
}
