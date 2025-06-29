package um.edu.uy.tads.linkedlist;

public class Nodo<E> {
    private E valor;
    private Nodo<E> next;

    public Nodo(E valor) {
        this.valor = valor;
        this.next = null;
    }

    public E getValor() {
        return valor;
    }

    public Nodo<E> getNext() {
        return next;
    }

    public void setNext(Nodo<E> next) {
        this.next = next;
    }
}
