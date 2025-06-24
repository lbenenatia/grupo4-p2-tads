package um.edu.uy.tads.bst;

public interface MyBinarySearchTree<K extends Comparable<K>, T> {

    T find(K key); // Busca y devuelve los datos asociados a una clave

    void insert(K key, T data); // Inserta un nodo con clave y datos

    void delete(K key); // Elimina un nodo con la clave dada

    int size(); // Devuelve la cantidad de nodos en el árbol

    void inorder(); // Recorrido en orden (izq, raíz, der)

    void preorder(); // Recorrido en preorden (raíz, izq, der)

    void postorder(); // Recorrido en postorden (izq, der, raíz)

    NodeBST<Integer, Integer> maximo(NodeBST<Integer, Integer> nodoActual);

    NodeBST<K, T> root();
}
