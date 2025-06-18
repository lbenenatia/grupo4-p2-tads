package um.edu.uy.entities;

public class PeliculaPorEvaluaciones extends Pelicula implements Comparable<PeliculaPorEvaluaciones> {

    @Override
    public int compareTo(PeliculaPorEvaluaciones otraPelicula) {
        return Double.compare(this.cantidadEvaluaciones(), otraPelicula.cantidadEvaluaciones());
    }
}

