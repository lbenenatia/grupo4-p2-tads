package um.edu.uy.entities;

import com.opencsv.bean.CsvBindByPosition;

import java.util.List;

public class Pelicula implements Comparable<Pelicula> {
    private int id;
    private String titulo;
    private String idiomaOriginal;
    private double ingresos;
    private List<Genero> generos; /// Ver si es realmente necesario tener los generos en la pelicula
    private boolean perteneceAColeccion = false; ///Actualizarla cuando tiene
    private double calificacionMedia;
    private List<Evaluacion> evaluaciones;
    /// Agregar fecha

    public Pelicula() {
        this.id = id;
        this.titulo = titulo;
        this.idiomaOriginal = idiomaOriginal;
        this.ingresos = ingresos;
        this.generos = generos;
        this.calificacionMedia = 0;
        this.perteneceAColeccion = perteneceAColeccion;
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
}
