package um.edu.uy.entities;

import java.util.ArrayList;
import java.util.List;

public class Genero implements Comparable<Genero> {
    private int id;
    private String nombre;
    private List<Evaluacion> evaluaciones;

    public Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.evaluaciones = new ArrayList<>();
    }

    public void agregarEvaluacion(Evaluacion e) {
        this.evaluaciones.add(e);
    }

    public int cantidadEvaluaciones() {
        return this.evaluaciones.size();
    }

    @Override
    public int compareTo(Genero otroGenero) {
        return Integer.compare(this.cantidadEvaluaciones(), otroGenero.cantidadEvaluaciones());
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
