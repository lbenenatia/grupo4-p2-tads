package um.edu.uy;

import um.edu.uy.consultas.TopPeliculasPorIdioma;
import um.edu.uy.entities.UMovie;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UMovie uMovie = new UMovie();
        TopPeliculasPorIdioma top5PorIdioma = new TopPeliculasPorIdioma();
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
                /*
                long startTime = System.currentTimeMillis();
                uMovie.cargarPeliculas("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\movies_metadata.csv");
                uMovie.cargarEvaluaciones("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\ratings_1mm.csv");
                uMovie.cargarActoresDirectores("C:\\Users\\lmart\\obg_prog2\\obligatorio2\\credits.csv");
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + duration + " milisegundos");

                 */
                long startTime = System.currentTimeMillis();
                uMovie.cargarDatos();
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + duration + " milisegundos");

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
                            //System.out.println(uMovie.top5PorIdioma());
                            System.out.println(top5PorIdioma.ejecutar(uMovie));
                            System.out.println();
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

                        case 3:
                            long startTime3 = System.currentTimeMillis();
                            uMovie.top5Ingresos();
                            long endTime3 = System.currentTimeMillis();
                            long duration3 = endTime3 - startTime3;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration3 + " milisegundos");
                            break;

                        case 4:
                            long startTime4 = System.currentTimeMillis();
                            uMovie.top10Directores();
                            long endTime4 = System.currentTimeMillis();
                            long duration4 = endTime4 - startTime4;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration4 + " milisegundos");
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
