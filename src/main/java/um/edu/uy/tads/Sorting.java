package um.edu.uy.tads;

import java.util.Arrays;

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

    public static <K extends Comparable<K>> K[] mergeSort(K [] array) {
        if (array.length < 2) {
            return array;
        }
        int middle = array.length/2;
        K [] left = Arrays.copyOfRange(array, 0, middle);
        K [] right = Arrays.copyOfRange(array, middle, array.length);
        left = mergeSort(left);
        right = mergeSort(right);
        return merge(left, right);
    }

    public static <K extends Comparable<K>> K[] merge(K [] left, K [] right) {
        K[] result = Arrays.copyOf(left, left.length + right.length);
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) < 0) {
                result[k++] = left[i++];
            }
            else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        return result;
    }

}
