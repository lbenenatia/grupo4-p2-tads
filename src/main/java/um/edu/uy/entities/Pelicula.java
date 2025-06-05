package um.edu.uy.entities;

import com.opencsv.bean.CsvBindByPosition;

import java.util.List;

public class Pelicula {
    @CsvBindByPosition(position = 5)
    private int id;
    @CsvBindByPosition(position = 18)
    private String titulo;
    @CsvBindByPosition(position = 7)
    private String idiomaOriginal;
    @CsvBindByPosition(position = 13)
    private double ingresos;
    @CsvBindByPosition(position = 3)
    private List<Genero> generos;
    @CsvBindByPosition(position = 1)
    private Coleccion coleccion;
    private double calificacionMedia;
    private List<Evaluacion> evaluaciones;

    public Pelicula() {
        this.id = id;
        this.titulo = titulo;
        this.idiomaOriginal = idiomaOriginal;
        this.ingresos = ingresos;
        this.generos = generos;
        this.calificacionMedia = 0;
        this.coleccion = coleccion;
        this.evaluaciones = evaluaciones;
    }

    public void calcularMedia() {
        double puntajeTotal = 0;
        for (Evaluacion e : this.evaluaciones) {
            puntajeTotal += e.getPuntaje();
        }

        this.calificacionMedia = puntajeTotal / this.evaluaciones.size();
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

    public Coleccion getColeccion() {
        return coleccion;
    }

    public void setColeccion(Coleccion coleccion) {
        this.coleccion = coleccion;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}
