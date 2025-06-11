package um.edu.uy.entities;

import java.util.List;

public class Genero implements Comparable<Genero> {
    private int id;
    private String nombre;
    private List<Evaluacion> evaluaciones;

    public Genero() {
        this.id = id;
        this.nombre = nombre;
        this.evaluaciones = evaluaciones;
    }

    public void agregarEvaluacion(Evaluacion e) {
        this.evaluaciones.add(e);
    }

    @Override
    public int compareTo(Genero otroGenero) {
        return Integer.compare(this.getEvaluaciones().size(), otroGenero.getEvaluaciones().size());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}
