package um.edu.uy.tads.hash;

import um.edu.uy.tads.ElementoYaExistenteException;
import um.edu.uy.tads.linkedlist.ListaL;

public interface HashTableL <K,T> extends Iterable<T>{
    void insertar (K clave, T valor) throws ElementoYaExistenteException;

    boolean pertenece (K clave);

    void borrar (K clave);

    T get (K clave);

    void put (K clave, T valor);

    boolean containsKey(K clave);

    int size();

    ListaL<T> values();

    boolean replace(K clave, T nuevoValor);
}
