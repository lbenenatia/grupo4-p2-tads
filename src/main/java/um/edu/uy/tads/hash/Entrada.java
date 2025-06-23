package um.edu.uy.tads.hash;

public class Entrada<K, T extends Comparable<T>> implements Comparable<Entrada<K, T>> {
    private K clave;
    private T valor;
    private boolean borrado;

    public Entrada(K clave, T valor) {
        this.clave = clave;
        this.valor = valor;
        this.borrado = false;
    }

    public K getClave() {
        return clave;
    }

    public void setClave(K clave) {
        this.clave = clave;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    @Override
    public int compareTo(Entrada<K, T> otraEntrada) {
        return this.valor.compareTo(otraEntrada.getValor());
    }
}
