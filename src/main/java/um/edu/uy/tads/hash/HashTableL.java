package um.edu.uy.tads.hash;

import um.edu.uy.tads.ElementoYaExistenteException;

public interface HashTableL <K,T> {
    public void insertar (K clave, T valor) throws ElementoYaExistenteException;
    public boolean pertenece (K clave);
    public void borrar (K clave);
    public T get (K clave);
}
