package um.edu.uy.entities;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import um.edu.uy.converter.FechaConversor;

public class Evaluacion implements Comparable<Evaluacion> {
    @CsvBindByPosition(position = 0)
    private Integer idUsuario;
    @CsvBindByPosition(position = 1)
    private Integer idPelicula;
    @CsvBindByPosition(position = 2)
    private double puntaje;
    @CsvCustomBindByPosition(position = 3, converter = FechaConversor.class)
    private int fecha;

    public Evaluacion() {
        this.idUsuario = idUsuario;
        this.idPelicula = idPelicula;
        this.puntaje = puntaje;
        this.fecha = fecha;
    }

    @Override
    public int compareTo(Evaluacion otraEvaluacion) {
        return Double.compare(this.puntaje, otraEvaluacion.puntaje);
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }
}
