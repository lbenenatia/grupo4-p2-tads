package um.edu.uy.entities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Actor {
    private String nombre;
    private List<Pelicula> peliculas;
    private Map<Integer, Integer> cantidadEvaluacionesPorMes;
    private int idActor;

    public Actor(int idActor, String nombre) {
        this.peliculas = new ArrayList<Pelicula>();
        this.nombre = nombre;
        this.cantidadEvaluacionesPorMes = new Hashtable<>();
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

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Map<Integer, Integer> getCantidadEvaluacionesPorMes() {
        return cantidadEvaluacionesPorMes;
    }

    public void setCantidadEvaluacionesPorMes(Map<Integer, Integer> cantidadEvaluacionesPorMes) {
        this.cantidadEvaluacionesPorMes = cantidadEvaluacionesPorMes;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }
}
