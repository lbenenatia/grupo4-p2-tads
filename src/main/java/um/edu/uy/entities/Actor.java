package um.edu.uy.entities;

import java.util.List;

public class Actor {
    private String nombre;
    private List<Pelicula> peliculas;
    private int cantidadPeliculasPorMes;
    /// Puede llegar a ser necesario tener el id para saber cuando un actor es el mismo (2 Jorges Gonzalez distintos)

    public Actor() {
        this.peliculas = peliculas;
        this.nombre = nombre;
        this.cantidadPeliculasPorMes = 0;
    }

    public void actualizarCantidad(String mes){}

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
}
