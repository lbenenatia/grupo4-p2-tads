package um.edu.uy.tads;

public interface HashTableL <K,T> {
    public void insertar (K clave, T valor) throws ElementoYaExistenteException;
    public boolean pertenece (K clave);
    public void borrar (K clave);
    public T get (K clave);
}
