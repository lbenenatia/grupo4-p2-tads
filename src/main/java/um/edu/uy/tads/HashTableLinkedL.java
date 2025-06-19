package um.edu.uy.tads;

public class HashTableLinkedL<K, T extends Comparable<T>> implements HashTableL<K,T>{
    private ListaL<Entrada<K,T>>[] hashTable;
    private int size = 11;
    private int amountofData;
    private double factordeCarga = 0.75;

    public HashTableLinkedL(int size, double factordeCarga) {
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
        if (hashTable[posicion] instanceof LinkedListL) {
            LinkedListL<Entrada<K,T>> lista = (LinkedListL<Entrada<K,T>>) hashTable[posicion];
            return lista.containsKey(clave.toString());
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

    private void insertarSinRedimensionar(K clave, T valor) {
        Entrada<K,T> entrada = new Entrada<>(clave, valor);
        int index = clave.hashCode();
        int posicion = Math.abs(index % size);
        hashTable[posicion].add(entrada);
        amountofData++;
    }

    public void redimensionar() throws ElementoYaExistenteException {
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
            LinkedListL<Entrada<K,T>> lista = (LinkedListL<Entrada<K,T>>) hashTable[posicion];
            for (int i = 0; i < lista.size(); i++) {
                Entrada<K,T> entrada = lista.get(i);
                if (entrada.getClave().equals(clave)) {
                    return entrada.getValor();
                }
            }
        }
        return null; // No se encontró la clave
    }

    public ListaL<Entrada<K,T>>[] getHashTable() {
        return hashTable;
    }

    public void setHashTable(ListaL<Entrada<K,T>>[] hashTable) {
        this.hashTable = hashTable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAmountofData() {
        return amountofData;
    }

    public void setAmountofData(int amountofData) {
        this.amountofData = amountofData;
    }

    public double getFactordeCarga() {
        return factordeCarga;
    }

    public void setFactordeCarga(double factordeCarga) {
        this.factordeCarga = factordeCarga;
    }
}
