package um.edu.uy.entities;

import com.opencsv.bean.CsvCustomBindByPosition;
import um.edu.uy.converter.IdColeccionJson;
import um.edu.uy.converter.TituloColeccionJson;

import java.util.ArrayList;
import java.util.List;

public class Coleccion implements Comparable<Coleccion> {
    @CsvCustomBindByPosition(position = 1, converter = IdColeccionJson.class)
    private int id;
    private List<Pelicula> peliculas = new ArrayList<>();
    private double ingresos;
    @CsvCustomBindByPosition(position = 1, converter = TituloColeccionJson.class)
    private String titulo;

    public Coleccion(int id, String titulo) {
        this.id = id;
        this.ingresos = 0;
        this.titulo = titulo;
    }

    public void agregarPelicula(Pelicula p) {
        this.peliculas.add(p);
        this.ingresos += p.getIngresos();
    }

    public int cantidadPeliculas() {
        return this.getPeliculas().size();
    }

    public List<Integer> idPeliculas() {
        List<Integer> ids = new ArrayList<>();

        for (Pelicula pelicula : peliculas) {
            ids.add(pelicula.getId());
        }
        return ids;
    }

    @Override
    public int compareTo(Coleccion otraColeccion) {
        return Double.compare(this.ingresos, otraColeccion.getIngresos());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public double getIngresos() {
        return ingresos;
    }

    public void setIngresos(double ingresos) {
        this.ingresos = ingresos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
