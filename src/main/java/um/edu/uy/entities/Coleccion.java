package um.edu.uy.entities;

import com.opencsv.bean.CsvCustomBindByPosition;
import um.edu.uy.converter.IdColeccionJson;
import um.edu.uy.converter.TituloColeccionJson;

import java.util.List;

public class Coleccion {
    @CsvCustomBindByPosition(position = 1, converter = IdColeccionJson.class)
    private int id;
    private List<Pelicula> peliculas;
    private double ingresos;
    @CsvCustomBindByPosition(position = 1, converter = TituloColeccionJson.class)
    private String titulo;

    public Coleccion() {
        this.id = id;
        this.peliculas = peliculas;
        this.ingresos = 0;
        this.titulo = titulo;
    }

    /// Podría hacerse esto y al agregar pelicula ya se va calculando el ingreso
    public void agregarPelicula(Pelicula p) {
            this.peliculas.add(p);
            this.ingresos += p.getIngresos();
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
