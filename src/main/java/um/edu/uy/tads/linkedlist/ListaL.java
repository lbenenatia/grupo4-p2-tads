package um.edu.uy.tads.linkedlist;

public interface ListaL <E> extends Iterable<E>{
    void add(E item);
    void remove(int index) throws IndexOutOfBoundsException;
    E get(int index) throws IndexOutOfBoundsException;
    int size();
    boolean contains(E item);
    boolean isEmpty();
}
