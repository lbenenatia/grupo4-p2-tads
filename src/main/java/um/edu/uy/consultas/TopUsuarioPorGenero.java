package um.edu.uy.consultas;

import um.edu.uy.entities.Evaluacion;
import um.edu.uy.entities.Genero;
import um.edu.uy.entities.UMovie;
import um.edu.uy.tads.bst.BinarySearchTree;
import um.edu.uy.tads.bst.MyBinarySearchTree;
import um.edu.uy.tads.bst.NodeBST;

import java.util.Map;

import static um.edu.uy.tads.Sorting.agregarOrdenado;
import static um.edu.uy.tads.Sorting.ordenarUltimo;

public class TopUsuarioPorGenero {
    public Genero[] top10Generos(UMovie umovie){
        Map<Integer, Genero> generos = umovie.getGeneros();
        Genero[] top = new Genero[10];

        int posVacia = 0;

        for (Genero genero : generos.values()) {
            if (posVacia < 10) {
                agregarOrdenado(genero, top, posVacia);
                posVacia++;
            } else {
                if (genero.compareTo(top[9]) > 0) {
                    top[9] = genero;
                    ordenarUltimo(top, 9);
                }
            }
        }
        return top;
    }

    public NodeBST<Integer, Integer>[] topUsuario(UMovie umovie) {
        NodeBST<Integer, Integer>[] topUsuarioPorGenero = new NodeBST[10];
        int posVacia = 0;
        for (Genero genero : top10Generos(umovie)) {
            MyBinarySearchTree<Integer, Integer> evaluacionesPorUsuario;
            evaluacionesPorUsuario = new BinarySearchTree<>();
            for (Evaluacion evaluacion : genero.getEvaluaciones()) {
                int idUsuario = evaluacion.getIdUsuario();
                if(evaluacionesPorUsuario.find(idUsuario) != null) {
                    int valorAntiguo = evaluacionesPorUsuario.find(idUsuario);
                    if (valorAntiguo == 0) {
                        evaluacionesPorUsuario.insert(idUsuario, 1);
                    } else {
                        evaluacionesPorUsuario.delete(idUsuario);
                        evaluacionesPorUsuario.insert(idUsuario, valorAntiguo + 1);
                    }
                } else {
                    evaluacionesPorUsuario.insert(idUsuario, 1);
                }
            }
            topUsuarioPorGenero[posVacia] = evaluacionesPorUsuario.maximo(evaluacionesPorUsuario.root());
            posVacia++;
        }
        return topUsuarioPorGenero;
    }

    public void ejecutar(UMovie umovie) {
        Genero[] top10Generos = top10Generos(umovie);
        NodeBST<Integer, Integer>[] topUsuarioPorGenero = topUsuario(umovie);

        for (int i = 0; i < 10; i++) {
            int idUsuario = topUsuarioPorGenero[i].getKey();
            int cantEvaluaciones = topUsuarioPorGenero[i].getData();
            String genero = top10Generos[i].getNombre();
            System.out.println(idUsuario + ", " + genero + ", " + cantEvaluaciones);
        }
    }
}
