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

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (!(obj instanceof PeliculaPorEvaluaciones)) {
            return false;
        }
        PeliculaPorEvaluaciones otraPelicula = (PeliculaPorEvaluaciones) obj;
        return this.getId().equals(otraPelicula.getId());
    }

}

