package um.edu.uy.tads;

public interface ListaL <E>{
    public void add(E item);
    public void remove(int index) throws IndexOutOfBoundsException;
    public E get(int index) throws IndexOutOfBoundsException;
    public int size();
    public boolean contains(E item);
    public boolean isEmpty();
}
