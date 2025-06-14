package um.edu.uy.tads;

public class Sorting{
    public Sorting() {}

    public static <K extends Comparable<K>> void swap(K[] array, int i, int j) {
        K temporal = array[i];
        array[i] = array[j];
        array[j] = temporal;
    }

    public static <K extends Comparable<K>> K[] ordenarUltimo(K[] array, int posicion) {
        boolean termine = false;
        while (!termine && posicion > 0) {
            if (array[posicion].compareTo(array[posicion-1]) > 0) {
                swap(array, posicion, posicion-1);
                posicion--;
            }
            else{
                termine = true;
            }
        }
        return array;
    }

    public static <K extends Comparable<K>> void agregarOrdenado(K elemento, K[] array, int posicion) {
        array[posicion] = elemento;
        while (posicion > 0) {
            if (array[posicion-1] == null){
                swap(array, posicion, posicion-1);
                posicion--;
            }
            else{
                if (elemento.compareTo(array[posicion-1]) > 0) {
                    swap(array, posicion, posicion-1);
                    posicion--;
                }
                else{
                    break;
                }
            }
        }
    }
}
