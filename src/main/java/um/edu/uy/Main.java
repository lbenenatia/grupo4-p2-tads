package um.edu.uy;

import um.edu.uy.consultas.*;
import um.edu.uy.entities.UMovie;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UMovie uMovie = new UMovie();
        TopPeliculasPorIdioma top5PorIdioma = new TopPeliculasPorIdioma();
        TopPeliculasPorCalificacionMedia top10PorCalificacionMedia = new TopPeliculasPorCalificacionMedia();
        TopColeccionPorIngreso top5PorIngreso = new TopColeccionPorIngreso();
        TopDirectores top10Directores = new TopDirectores();
        TopActorPorMes topActorPorMes = new TopActorPorMes();
        TopUsuarioPorGenero topUsuarioPorGenero = new TopUsuarioPorGenero();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Menu principal");
            System.out.println("Seleccione la opcion que desee:");
            System.out.println("1. Carga de datos");
            System.out.println("2. Ejecutar consultas");
            System.out.println("3. Salir");

            System.out.println("Ingrese una opcion:");
              while (!input.hasNextInt()) {
                System.out.println("Opcion invalida");
                System.out.println("Ingrese otra opcion:");
                input.next();
            }

            int seleccion1 = input.nextInt();

            if (seleccion1 == 1) {
                long startTime = System.currentTimeMillis();
                uMovie.cargarDatos();
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("Carga de datos exitosa, tiempo de ejecución de la carga: " + duration + " milisegundos");

            } else if (seleccion1 == 2) {
                loop:
                while (true) {
                    System.out.println("1. Top 5 de las peliculas que mas calificaciones por idioma tienen.");
                    System.out.println("2. Top 10 de las peliculas que mejor calificacion media tienen por parte de los usuarios.");
                    System.out.println("3. Top 5 de las colecciones que mas ingresos generaron.");
                    System.out.println("4. Top 10 de los directores que mejor calificacion tienen.");
                    System.out.println("5. Actor con mas calificaciones recibidas en cada mes del año.");
                    System.out.println("6. Usuarios con mas calificaciones por genero");
                    System.out.println("7. Salir");

                    System.out.println("Ingrese una opcion: ");
                    while (!input.hasNextInt()) {
                        System.out.println("Opcion invalida");
                        System.out.println("Ingrese otra opcion:");
                        input.next();
                    }

                    int seleccion2 = input.nextInt();

                    switch (seleccion2) {
                        case 1:
                            long startTime = System.currentTimeMillis();
                            System.out.println(top5PorIdioma.ejecutar(uMovie));
                            long endTime = System.currentTimeMillis();
                            long duration = endTime - startTime;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration + " milisegundos");
                            break;

                        case 2:
                            long startTime2 = System.currentTimeMillis();
                            top10PorCalificacionMedia.ejecutar(uMovie);
                            long endTime2 = System.currentTimeMillis();
                            long duration2 = endTime2 - startTime2;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration2 + " milisegundos");
                            break;

                        case 3:
                            long startTime3 = System.currentTimeMillis();
                            top5PorIngreso.ejecutar(uMovie);
                            long endTime3 = System.currentTimeMillis();
                            long duration3 = endTime3 - startTime3;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration3 + " milisegundos");
                            break;

                        case 4:
                            long startTime4 = System.currentTimeMillis();
                            top10Directores.ejecutar(uMovie);
                            long endTime4 = System.currentTimeMillis();
                            long duration4 = endTime4 - startTime4;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration4 + " milisegundos");
                            break;

                        case 5:
                            long startTime5 = System.currentTimeMillis();
                            topActorPorMes.ejecutar(uMovie);
                            long endTime5 = System.currentTimeMillis();
                            long duration5 = endTime5 - startTime5;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration5 + " milisegundos");
                            break;

                        case 6:
                            long startTime6 = System.currentTimeMillis();
                            topUsuarioPorGenero.ejecutar(uMovie);
                            long endTime6 = System.currentTimeMillis();
                            long duration6 = endTime6 - startTime6;
                            System.out.println("Tiempo de ejecucion de la consulta: " + duration6 + " milisegundos");
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
