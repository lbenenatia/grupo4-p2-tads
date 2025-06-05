package um.edu.uy.entities;

import java.util.List;

public class Genero {
    private int id;
    private String nombre;
    private List<Evaluacion> evaluaciones;

    public Genero() {
        this.id = id;
        this.nombre = nombre;
        this.evaluaciones = evaluaciones;
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
