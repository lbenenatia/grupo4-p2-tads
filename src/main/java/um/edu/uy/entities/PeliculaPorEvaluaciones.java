package um.edu.uy.entities;

public class PeliculaPorEvaluaciones extends Pelicula {

    public PeliculaPorEvaluaciones(Pelicula pelicula) {
        super(pelicula.getId(), pelicula.getTitulo(), pelicula.getIdiomaOriginal(), pelicula.getIngresos());
        this.setEvaluaciones(pelicula.getEvaluaciones());
    }

    @Override
    public int compareTo(Pelicula otraPelicula) {
        return Integer.compare(this.cantidadEvaluaciones(),otraPelicula.cantidadEvaluaciones());
    }
}

