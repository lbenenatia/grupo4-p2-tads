package um.edu.uy.entities;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import um.edu.uy.converter.FechaConversor;

public class Evaluacion implements Comparable<Evaluacion> {
    @CsvBindByPosition(position = 0)
    private int idUsuario;
    @CsvBindByPosition(position = 1)
    private int idPelicula;
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
//    }
//
//    public int convertFecha(String fecha) {
//        return new Date(Long.parseLong(this.fecha));
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

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }
}
