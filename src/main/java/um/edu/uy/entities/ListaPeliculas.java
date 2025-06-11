package um.edu.uy.entities;

public class ListaPeliculas {
    private Pelicula[] ingles;
    private Pelicula[] frances;
    private Pelicula[] espaniol;
    private Pelicula[] italiano;
    private Pelicula[] portugues;

    public ListaPeliculas(Pelicula[] ingles, Pelicula[] frances, Pelicula[] espaniol, Pelicula[] italiano, Pelicula[] portugues) {
            this.ingles = new Pelicula[5];
            this.frances = new Pelicula[5];
            this.espaniol = new Pelicula[5];
            this.italiano = new Pelicula[5];
            this.portugues = new Pelicula[5];
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
