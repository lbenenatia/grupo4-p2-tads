
package um.edu.uy.entities;

public class PeliculaPorCalificacionMedia extends Pelicula{

    public PeliculaPorCalificacionMedia(Pelicula pelicula) {
        super(pelicula.getId(), pelicula.getTitulo(), pelicula.getIdiomaOriginal(), pelicula.getIngresos());
        this.setEvaluaciones(pelicula.getEvaluaciones());
        this.setCalificacionMedia(pelicula.getCalificacionMedia());
    }

    @Override
    public int compareTo(Pelicula otraPelicula) {
        return Double.compare(this.getCalificacionMedia(), otraPelicula.getCalificacionMedia());
    }
}

