package um.edu.uy;

import um.edu.uy.entities.UMovie;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UMovie uMovie = new UMovie();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Menu principal" + "\n" +
                    "Seleccione la opcion que desee:\n" +
                    "1. Carga de datos\n" +
                    "2. Ejecutar consultas\n" +
                    "3. Salir");

            System.out.println("Ingrese una opcion: ");
              while (!input.hasNextInt()) {
                System.out.println("Opcion invalida\n" +
                        "Ingrese otra opcion: ");
                input.next();
            }

            int seleccion1 = input.nextInt();

            if (seleccion1 == 1) {
                long startTime = System.currentTimeMillis();
                uMovie.cargarPeliculas("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\movies_metadata.csv");
                uMovie.cargarEvaluaciones("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\ratings_1mm.csv");
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("Duración de cargarPeliculas: " + duration + " milisegundos");
            } else if (seleccion1 == 2) {
                loop:
                while (true) {
                    System.out.println(
                            "1. Top 5 de las peliculas que mas calificaciones por idioma tienen.\n" +
                            "2. Top 10 de las peliculas que mejor calificacion media tienen por parte de los usuarios.\n" +
                            "3. Top 5 de las colecciones que mas ingresos generaron.\n" +
                            "4. Top 10 de los directores que mejor calificacion tienen.\n" +
                            "5. Actor con mas calificaciones recibidas en cada mes del año.\n" +
                            "6. Usuarios con mas calificaciones por genero\n" +
                            "7. Salir");

                    System.out.println("Ingrese una opcion: ");
                    while (!input.hasNextInt()) {
                        System.out.println("Opcion invalida\n" +
                                "Ingrese otra opcion: ");
                        input.next();
                    }

                    int seleccion2 = input.nextInt();

                    switch (seleccion2) {
                        case 1:
                            long startTime = System.currentTimeMillis();
                            System.out.println(uMovie.top5PorIdioma());
                            long endTime = System.currentTimeMillis();
                            long duration = endTime - startTime;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration + " milisegundos");
                            break;

                        case 2:
                            long startTime2 = System.currentTimeMillis();
                            uMovie.top10CalificacionMedia();
                            long endTime2 = System.currentTimeMillis();
                            long duration2 = endTime2 - startTime2;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration2 + " milisegundos");
                            break;

                        case 3: // funcion 3
                            break;

                        case 4: // funcion 4
                            break;

                        case 5: // funcion 5
                            break;

                        case 6: // funcion 6
                            break;

                        case 7:
                            break loop;
                    }

                }
            } else if (seleccion1 == 3) { // Break
                return;
            }
        }
    }
}
