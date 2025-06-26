package um.edu.uy.tads.linkedlist;

import java.util.Iterator;

public class LinkedListL<E> implements ListaL<E> {
    private Nodo<E> head;
    private Nodo<E> tail;
    private int largo;

    @Override
    public void add(E item) {
        Nodo<E> nodo = new Nodo<>(item);
        if (head == null) {
            head = nodo;
            tail = nodo;
        } else {
            tail.setNext(nodo);
            tail = nodo;
        }
        largo++;
    }

    @Override
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
        largo--;
    }

    @Override
    public E get(int index) {
        Nodo<E> actual = head;
        for (int i = 0; i < index; i++) {
            actual = actual.getNext();
        }
        return actual.getValor();
    }

    @Override
    public int size() {
        return largo;
    }

    @Override
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

    @Override
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Nodo<E> actual = head;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new RuntimeException(); //cambiar exception
                }
                E valor = actual.getValor();
                actual = actual.getNext();
                return valor;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder("[");
        Nodo<E> temp = head;
        for (int i = 0; i < this.largo; i++) {
            resultado.append(temp.getValor().toString());
            if (temp.getNext() != null) {
                resultado.append(", ");
            }
            temp = temp.getNext();
        }
        resultado.append("]");
        return resultado.toString();
    }
}
