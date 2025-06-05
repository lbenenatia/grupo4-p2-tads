package um.edu.uy.entities;

import java.util.List;

public class ListaPeliculas {
    private List<Pelicula> ingles;
    private List<Pelicula> frances;
    private List<Pelicula> espaniol;
    private List<Pelicula> italiano;
    private List<Pelicula> portugues;

    public ListaPeliculas(List<Pelicula> ingles, List<Pelicula> frances, List<Pelicula> espaniol, List<Pelicula> italiano, List<Pelicula> portugues) {
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
            display.append(pelicula.getId()).append(pelicula.getTitulo()).append(pelicula.getEvaluaciones().size()).append(pelicula.getIdiomaOriginal()).append("\n");
        }
        for (Pelicula pelicula : frances){
            display.append(pelicula.getId()).append(pelicula.getTitulo()).append(pelicula.getEvaluaciones().size()).append(pelicula.getIdiomaOriginal()).append("\n");
        }
        for (Pelicula pelicula : espaniol){
            display.append(pelicula.getId()).append(pelicula.getTitulo()).append(pelicula.getEvaluaciones().size()).append(pelicula.getIdiomaOriginal()).append("\n");
        }
        for (Pelicula pelicula : italiano){
            display.append(pelicula.getId()).append(pelicula.getTitulo()).append(pelicula.getEvaluaciones().size()).append(pelicula.getIdiomaOriginal()).append("\n");
        }
        for (Pelicula pelicula : portugues){
            display.append(pelicula.getId()).append(pelicula.getTitulo()).append(pelicula.getEvaluaciones().size()).append(pelicula.getIdiomaOriginal()).append("\n");
        }

        return display.toString();
    }

    public List<Pelicula> getIngles() {
        return ingles;
    }

    public void setIngles(List<Pelicula> ingles) {
        this.ingles = ingles;
    }

    public List<Pelicula> getFrances() {
        return frances;
    }

    public void setFrances(List<Pelicula> frances) {
        this.frances = frances;
    }

    public List<Pelicula> getEspaniol() {
        return espaniol;
    }

    public void setEspaniol(List<Pelicula> espaniol) {
        this.espaniol = espaniol;
    }

    public List<Pelicula> getItaliano() {
        return italiano;
    }

    public void setItaliano(List<Pelicula> italiano) {
        this.italiano = italiano;
    }

    public List<Pelicula> getPortugues() {
        return portugues;
    }

    public void setPortugues(List<Pelicula> portugues) {
        this.portugues = portugues;
    }
}
