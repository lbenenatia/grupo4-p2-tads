package um.edu.uy.tads.bst;

public class BinarySearchTree<K extends Comparable<K>, T> implements MyBinarySearchTree<K, T> {

    private NodeBST<K, T> root;

    @Override
    public T find(K key) {
        return findRecursive(root, key);
    }

    private T findRecursive(NodeBST<K, T> current, K key) {
        if (current == null) return null;

        int compare = key.compareTo(current.getKey());
        if (compare == 0) return current.getData();
        if (compare < 0) return findRecursive(current.getLeftChild(), key);
        else return findRecursive(current.getRightChild(), key);
    }

    @Override
    public void insert(K key, T data) {
        root = insertRecursive(root, key, data);
    }

    private NodeBST<K, T> insertRecursive(NodeBST<K, T> current, K key, T data) {
        if (current == null) return new NodeBST<>(key, data);

        int compare = key.compareTo(current.getKey());
        if (compare == 0) {
            throw new IllegalArgumentException("Clave duplicada: " + key);
        } else if (compare < 0) {
            current.setLeftChild(insertRecursive(current.getLeftChild(), key, data));
        } else {
            current.setRightChild(insertRecursive(current.getRightChild(), key, data));
        }

        return current;
    }

    @Override
    public void delete(K key) {
        root = deleteRecursive(root, key);
    }

    private NodeBST<K, T> deleteRecursive(NodeBST<K, T> current, K key) {
        if (current == null) return null;

        int compare = key.compareTo(current.key);
        if (compare < 0) {
            current.setLeftChild(deleteRecursive(current.getLeftChild(), key));
        } else if (compare > 0) {
            current.setRightChild(deleteRecursive(current.getRightChild(), key));
        } else {
            // nodo encontrado
            if (current.getLeftChild() == null) return current.getRightChild();
            if (current.getRightChild() == null) return current.getLeftChild();

            // nodo con dos hijos: reemplazar con el menor del subarbol derecho
            NodeBST<K, T> min = findMinNode(current.getRightChild());
            current.setKey(min.getKey());
            current.setData(min.getData());
            current.setRightChild(deleteRecursive(current.getRightChild(), min.getKey()));
        }

        return current;
    }

    private NodeBST<K, T> findMinNode(NodeBST<K, T> node) {
        while (node.getLeftChild() != null) node = node.getLeftChild();
        return node;
    }

    @Override
    public NodeBST<Integer, Integer> maximo(NodeBST<Integer, Integer> nodoActual) {
        while (nodoActual.getRightChild() != null) nodoActual = nodoActual.getRightChild();
        return nodoActual;
    }

    @Override
    public int size() {
        return sizeBinario(root);
    }

    private int sizeBinario(NodeBST<K, T> nodo) {

        // si esta vacio el arbol
        if(nodo == null) {
            return 0;
        }

        // cuenta todos los nodos de los subarboles de la raiz
        return 1 + sizeBinario(nodo.getLeftChild()) + sizeBinario(nodo.getRightChild());
    }

    @Override
    public void inorder() {
        inorder(root);
        System.out.println();
    }

    private void inorder(NodeBST<K, T> node) {
        if (node != null) {
            inorder(node.getLeftChild());
            System.out.print(node.getKey() + " ");
            inorder(node.getRightChild());
        }
    }

    @Override
    public void preorder() {
        preorder(root);
        System.out.println();
    }

    private void preorder(NodeBST<K, T> node) {
        if (node != null) {
            System.out.print(node.getKey() + " ");
            preorder(node.getLeftChild());
            preorder(node.getRightChild());
        }
    }

    @Override
    public void postorder() {
        postorder(root);
        System.out.println();
    }

    private void postorder(NodeBST<K, T> node) {
        if (node != null) {
            postorder(node.getLeftChild());
            postorder(node.getRightChild());
            System.out.print(node.getKey() + " ");
        }
    }

    @Override
    public NodeBST<K, T> root() {
        return this.root;
    }
}
