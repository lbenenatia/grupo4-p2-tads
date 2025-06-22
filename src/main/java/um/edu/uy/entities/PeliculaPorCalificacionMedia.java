
package um.edu.uy.entities;

public class PeliculaPorCalificacionMedia extends Pelicula implements Comparable<PeliculaPorCalificacionMedia>{

    public PeliculaPorCalificacionMedia(Pelicula pelicula) {
        super(pelicula.getId(), pelicula.getTitulo(), pelicula.getIdiomaOriginal(), pelicula.getIngresos());
        this.setEvaluaciones(pelicula.getEvaluaciones());
        this.setCalificacionMedia(pelicula.getCalificacionMedia());
    }

    @Override
    public int compareTo(PeliculaPorCalificacionMedia otraPelicula) {
        return Double.compare(this.getCalificacionMedia(), otraPelicula.getCalificacionMedia());
    }
}

