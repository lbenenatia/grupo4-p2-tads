package um.edu.uy.entities;

import um.edu.uy.tads.hash.HashTableL;
import um.edu.uy.tads.hash.HashTableLinkedL;
import um.edu.uy.tads.linkedlist.LinkedListL;
import um.edu.uy.tads.linkedlist.ListaL;

public class Actor implements Comparable<Actor> {
    private String nombre;
    private ListaL<Pelicula> peliculas;
    private HashTableL<Integer, Integer> cantidadEvaluacionesPorMes;
    private Integer idActor;

    public Actor(int idActor, String nombre) {
        this.peliculas = new LinkedListL<Pelicula>();
        this.nombre = nombre;
        this.cantidadEvaluacionesPorMes = new HashTableLinkedL<>();
        for (int i = 0; i < 12; i++) {
            cantidadEvaluacionesPorMes.put(i, 0);
        }
        this.idActor = idActor;
    }

    public int cantidadPeliculasPorMes(int mes) {
        int cantidad = 0;
        for (Pelicula pelicula : peliculas) {
            for (Evaluacion evaluacion : pelicula.getEvaluaciones()){
                if (evaluacion.getFecha() == mes){
                    cantidad += 1;
                    break;
                }
            }
        }
        return cantidad;
    }


    public void agregarPelicula(Pelicula pelicula) {
        this.peliculas.add(pelicula);
    }

    /// Ver tema tiempo/mes de las peliculas

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaL<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ListaL<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public HashTableL<Integer, Integer> getCantidadEvaluacionesPorMes() {
        return cantidadEvaluacionesPorMes;
    }

    public void setCantidadEvaluacionesPorMes(HashTableL<Integer, Integer> cantidadEvaluacionesPorMes) {
        this.cantidadEvaluacionesPorMes = cantidadEvaluacionesPorMes;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    @Override
    public int compareTo(Actor o) {
        return this.idActor.compareTo(o.getIdActor());
    }
}
