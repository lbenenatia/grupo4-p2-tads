package um.edu.uy.tads.linkedlist;

import um.edu.uy.tads.hash.Entrada;

public class LinkedListL<E> implements ListaL<E> {
    private Nodo<E> head;
    private Nodo<E> tail;

    public void add(E item) {
        Nodo<E> nodo = new Nodo<>(item);
        if (head == null) {
            head = nodo;
            tail = nodo;
        } else {
            tail.setNext(nodo);
            tail = nodo;
        }
    }

    public void remove(int index) {
        if (head == null) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            head = head.getNext();
        } else {
            Nodo<E> auxiliar = head;
            for (int i = 0; i < index - 1; i++) {
                auxiliar = auxiliar.getNext();
            }
            auxiliar.setNext(auxiliar.getNext().getNext());
        }
    }

    public E get(int index) {
        Nodo<E> actual = head;
        for (int i = 0; i < index; i++) {
            actual = actual.getNext();
        }
        return actual.getValor();
    }

    public int size() {
        int length = 0;
        Nodo<E> auxiliar = head;
        while (auxiliar != null) {
            auxiliar = auxiliar.getNext();
            length++;
        }
        return length;
    }

    public boolean contains(E item) {
        Nodo<E> auxiliar = head;
        while (auxiliar != null) {
            if (auxiliar.getValor().equals(item)) {
                return true;
            }
            auxiliar = auxiliar.getNext();
        }
        return false;
    }

    //Metodo para HashTable
    public boolean containsKey(String clave) {
        Nodo<E> auxiliar = head;
        while (auxiliar != null) {
            if (auxiliar.getValor() instanceof Entrada) {
                Entrada entrada = (Entrada) auxiliar.getValor();
                if (entrada.getClave().equals(clave)) {
                    return true;
                }
            }
            auxiliar = auxiliar.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }
}
