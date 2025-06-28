package um.edu.uy.entities;

import com.opencsv.bean.CsvCustomBindByPosition;
import um.edu.uy.converter.IdColeccionJson;
import um.edu.uy.converter.TituloColeccionJson;
import um.edu.uy.tads.linkedlist.LinkedListL;
import um.edu.uy.tads.linkedlist.ListaL;

public class Coleccion implements Comparable<Coleccion> {
    @CsvCustomBindByPosition(position = 1, converter = IdColeccionJson.class)
    private Integer id;
    private ListaL<Pelicula> peliculas;
    private double ingresos;
    @CsvCustomBindByPosition(position = 1, converter = TituloColeccionJson.class)
    private String titulo;

    public Coleccion(Integer id, String titulo) {
        this.id = id;
        this.peliculas = new LinkedListL<>();
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

    public ListaL<Integer> idPeliculas() {
        ListaL<Integer> ids = new LinkedListL<>();

        for (Pelicula pelicula : peliculas) {
            ids.add(pelicula.getId());
        }
        return ids;
    }

    @Override
    public int compareTo(Coleccion otraColeccion) {
        return Double.compare(this.ingresos, otraColeccion.getIngresos());
    }

    public Integer getId() {
        return id;
    }

    public ListaL<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ListaL<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public double getIngresos() {
        return ingresos;
    }

    public String getTitulo() {
        return titulo;
    }
}
