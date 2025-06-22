package um.edu.uy.entities;

public class PeliculaPorEvaluaciones extends Pelicula implements Comparable<PeliculaPorEvaluaciones> {

    public PeliculaPorEvaluaciones(Pelicula pelicula) {
        super(pelicula.getId(), pelicula.getTitulo(), pelicula.getIdiomaOriginal(), pelicula.getIngresos());
        this.setEvaluaciones(pelicula.getEvaluaciones());
    }

    @Override
    public int compareTo(PeliculaPorEvaluaciones otraPelicula) {
        return Double.compare(this.cantidadEvaluaciones(), otraPelicula.cantidadEvaluaciones());
    }
}

