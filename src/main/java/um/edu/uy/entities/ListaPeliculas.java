package um.edu.uy.entities;

public class ListaPeliculas {
    private PeliculaPorEvaluaciones[] ingles;
    private PeliculaPorEvaluaciones[] frances;
    private PeliculaPorEvaluaciones[] espaniol;
    private PeliculaPorEvaluaciones[] italiano;
    private PeliculaPorEvaluaciones[] portugues;

    public ListaPeliculas(PeliculaPorEvaluaciones[] ingles, PeliculaPorEvaluaciones[] frances, PeliculaPorEvaluaciones[] espaniol, PeliculaPorEvaluaciones[] italiano, PeliculaPorEvaluaciones[] portugues) {
        this.ingles = ingles;
        this.frances = frances;
        this.espaniol = espaniol;
        this.italiano = italiano;
        this.portugues = portugues;
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        for (PeliculaPorEvaluaciones pelicula : ingles){
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (PeliculaPorEvaluaciones pelicula : frances) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (PeliculaPorEvaluaciones pelicula : espaniol) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (PeliculaPorEvaluaciones pelicula : italiano) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }
        for (PeliculaPorEvaluaciones pelicula : portugues) {
            if (pelicula != null) {
                display.append(pelicula.getId()).append(", ").append(pelicula.getTitulo()).append(", ").append(pelicula.cantidadEvaluaciones()).append(", ").append(pelicula.getIdiomaOriginal()).append("\n");
            }
        }

        return display.toString();
    }

    public PeliculaPorEvaluaciones[] getIngles() {
        return ingles;
    }

    public PeliculaPorEvaluaciones[] getFrances() {
        return frances;
    }

    public PeliculaPorEvaluaciones[] getEspaniol() {
        return espaniol;
    }

    public PeliculaPorEvaluaciones[] getItaliano() {
        return italiano;
    }

    public PeliculaPorEvaluaciones[] getPortugues() {
        return portugues;
    }

}