package um.edu.uy.entities;

import java.util.List;

public class ListaPeliculas {
    private Pelicula[] ingles;
    private Pelicula[] frances;
    private Pelicula[] espaniol;
    private Pelicula[] italiano;
    private Pelicula[] portugues;

    public ListaPeliculas(Pelicula[] ingles, Pelicula[] frances, Pelicula[] espaniol, Pelicula[] italiano, Pelicula[] portugues) {
            this.ingles = ingles;
            this.frances = frances;
            this.espaniol = espaniol;
            this.italiano = italiano;
            this.portugues = portugues;
        }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        for (Pelicula pelicula : ingles){
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (Pelicula pelicula : frances) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (Pelicula pelicula : espaniol) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (Pelicula pelicula : italiano) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (Pelicula pelicula : portugues) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }

        return display.toString();
    }

    public Pelicula[] getIngles() {
        return ingles;
    }

    public void setIngles(Pelicula[] ingles) {
        this.ingles = ingles;
    }

    public Pelicula[] getFrances() {
        return frances;
    }

    public void setFrances(Pelicula[] frances) {
        this.frances = frances;
    }

    public Pelicula[] getEspaniol() {
        return espaniol;
    }

    public void setEspaniol(Pelicula[] espaniol) {
        this.espaniol = espaniol;
    }

    public Pelicula[] getItaliano() {
        return italiano;
    }

    public void setItaliano(Pelicula[] italiano) {
        this.italiano = italiano;
    }

    public Pelicula[] getPortugues() {
        return portugues;
    }

    public void setPortugues(Pelicula[] portugues) {
        this.portugues = portugues;
    }
}
