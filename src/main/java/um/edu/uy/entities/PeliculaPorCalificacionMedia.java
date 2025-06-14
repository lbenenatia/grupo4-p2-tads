package um.edu.uy.entities;

public class PeliculaPorCalificacionMedia extends Pelicula implements Comparable<PeliculaPorCalificacionMedia>{
    @Override
    public int compareTo(PeliculaPorCalificacionMedia otraPelicula) {
        return Double.compare(this.getCalificacionMedia(), otraPelicula.getCalificacionMedia());
    }
}
