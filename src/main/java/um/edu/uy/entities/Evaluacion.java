package um.edu.uy.entities;

public class Evaluacion {
    private int idUsuario;
    private int idPelicula;
    private double puntaje;

    public Evaluacion() {
        this.idUsuario = idUsuario;
        this.idPelicula = idPelicula;
        this.puntaje = puntaje;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }
}
