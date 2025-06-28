package um.edu.uy.entities;

import um.edu.uy.tads.linkedlist.LinkedListL;
import um.edu.uy.tads.linkedlist.ListaL;

public class Genero implements Comparable<Genero> {
    private Integer id;
    private String nombre;
    private ListaL<Evaluacion> evaluaciones;

    public Genero(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.evaluaciones = new LinkedListL<>();
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

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ListaL<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }
}
