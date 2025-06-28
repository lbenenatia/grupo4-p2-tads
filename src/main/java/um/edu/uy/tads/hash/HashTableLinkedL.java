package um.edu.uy.tads.hash;

import um.edu.uy.tads.ElementoYaExistenteException;
import um.edu.uy.tads.linkedlist.LinkedListL;
import um.edu.uy.tads.linkedlist.ListaL;

import java.util.Iterator;

public class HashTableLinkedL<K, T extends Comparable<T>> implements HashTableL<K,T>{
    private ListaL<Entrada<K,T>>[] hashTable;
    private int size = 11;
    private int amountofData;
    private double factordeCarga = 0.75;

    public HashTableLinkedL() {
        this.size = size;
        this.hashTable = new LinkedListL[size];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new LinkedListL<>();
        }
        this.amountofData = 0;
        this.factordeCarga = factordeCarga;
    }

    @Override
    public void insertar(K clave, T valor) throws ElementoYaExistenteException {
        if ((double)(amountofData + 1) / size > factordeCarga) {
            redimensionar();
        }
        Entrada<K,T> entrada = new Entrada<>(clave, valor);
        if (pertenece(clave)) {
            throw new ElementoYaExistenteException();
        }
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);
        hashTable[posicion].add(entrada);
        amountofData++;
    }

    @Override
    public boolean pertenece(K clave) {
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);

        for (Entrada<K, T> entrada : hashTable[posicion]) {
            if (entrada.getClave().equals(clave)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void borrar(K clave) {
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);
        if (hashTable[posicion] instanceof LinkedListL) {
            LinkedListL<Entrada<K,T>> lista = (LinkedListL<Entrada<K,T>>) hashTable[posicion];
            for (int i = 0; i< lista.size(); i++){
                Entrada<K,T> entrada = lista.get(i);
                if(entrada.getClave().equals(clave)){
                    lista.remove(i);
                    amountofData--;
                    return;
                }
            }
        }
    }

    @Override
    public void put(K clave, T valor) {
        if ((double)(amountofData + 1) / size > factordeCarga) {
            redimensionar();
        }

        int index = clave.hashCode();
        int posicion = Math.abs(index % size);
        ListaL<Entrada<K, T>> lista = hashTable[posicion];

        for (Entrada<K, T> entrada : lista) {
            if (entrada.getClave().equals(clave)) {
                entrada.setValor(valor);
                return;
            }
        }

        Entrada<K, T> nuevaEntrada = new Entrada<>(clave, valor);
        lista.add(nuevaEntrada);
        amountofData++;
    }

    @Override
    public boolean containsKey(K clave) {
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);

        ListaL<Entrada<K, T>> bucket = hashTable[posicion];
        for (Entrada<K, T> entrada : bucket) {
            if (entrada.getClave().equals(clave)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        ListaL<T> valores = new LinkedListL<>();

        for (ListaL<Entrada<K, T>> bucket : hashTable) {
            for (Entrada<K, T> entrada : bucket) {
                valores.add(entrada.getValor());
            }
        }

        return valores.iterator();
    }

    @Override
    public int size() {
        return hashTable.length;
    }

    @Override
    public ListaL<T> values() {
        ListaL<T> valores = new LinkedListL<>();

        for (ListaL<Entrada<K, T>> bucket : hashTable) {
            for (Entrada<K, T> entrada : bucket) {
                valores.add(entrada.getValor());
            }
        }

        return valores;
    }

    @Override
    public boolean replace(K clave, T nuevoValor) {
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);

        ListaL<Entrada<K, T>> lista = hashTable[posicion];

        for (int i = 0; i < lista.size(); i++) {
            Entrada<K, T> entrada = lista.get(i);
            if (entrada.getClave().equals(clave)) {
                entrada.setValor(nuevoValor);
                return true; // reemplazo exitoso
            }
        }

        return false; // la clave no existe
    }

    private void insertarSinRedimensionar(K clave, T valor) {
        Entrada<K,T> entrada = new Entrada<>(clave, valor);
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);
        hashTable[posicion].add(entrada);
        amountofData++;
    }

    public void redimensionar()  {
        ListaL<Entrada<K,T>>[] oldHashTable = hashTable;
        LinkedListL<Entrada<K,T>> lista = null;
        hashTable = (ListaL<Entrada<K,T>>[]) new LinkedListL[closestPrime(size * 2)];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new LinkedListL<>();
        }
        size = hashTable.length;
        amountofData = 0;
        for (int i = 0; i < oldHashTable.length; i++) {
            if (oldHashTable[i] instanceof LinkedListL) {
                lista = (LinkedListL<Entrada<K,T>>) oldHashTable[i];
                for (int j = 0; j < lista.size(); j++) {
                    Entrada<K,T> entrada = lista.get(j);
                    if (entrada != null) {
                        insertarSinRedimensionar(entrada.getClave(), entrada.getValor());
                    }
                }
            }
        }
    }

    public boolean isPrime(int num) {
        if (num%2 == 0) {
            return false;
        }
        for (int i=3; i<= Math.sqrt(num); i+=2) {
            if (num%i == 0) {
                return false;
            }
        }
        return true;
    }

    public int closestPrime(int num) {
        int num2 = num;
        while (true) {
            if (isPrime(num2)) {
                return num2;
            }
            if (isPrime(num)) {
                return num;
            }
            num++;
            num2--;
        }
    }

    public T get(K clave) {
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);

        if (hashTable[posicion] instanceof LinkedListL) {
            ListaL<Entrada<K,T>> lista = hashTable[posicion];
            for (int i = 0; i < lista.size(); i++) {
                Entrada<K,T> entrada = lista.get(i);
                if (entrada.getClave().equals(clave)) {
                    return entrada.getValor();
                }
            }
        }
        return null; // No se encontró la clave
    }

}
