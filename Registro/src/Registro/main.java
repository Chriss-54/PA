package Registro;

import java.util.Scanner;

public class main {

    private static Scanner scanner = new Scanner(System.in);
    private static person[] persons;
    private static int contador = 0;

    public static void main(String[] args) {
        // Validación de usuario y contraseña
        boolean loggedIn = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (!loggedIn && attempts < MAX_ATTEMPTS) {
            System.out.print("Ingrese usuario: ");
            String userInput = scanner.nextLine();
            System.out.print("Ingrese contraseña: ");
            String passInput = scanner.nextLine();

            Usuario user = new Usuario(userInput, passInput);
            if (user.Validate()) {
                loggedIn = true;
                System.out.println("Acceso concedido.\n");
            } else {
                attempts++;
                System.out.println("Usuario o contraseña incorrectos. Intento " + attempts + " de " + MAX_ATTEMPTS);
                if (attempts >= MAX_ATTEMPTS) {
                    System.out.println("Demasiados intentos fallidos. El programa se cerrará.");
                    System.exit(0);
                }
            }
        }

        // Solicitar número de personas a registrar
        System.out.print("Ingrese el número de personas que desea registrar: ");
        int numPersonas = Integer.parseInt(scanner.nextLine());
        persons = new person[numPersonas];

        for (int i = 0; i < numPersonas; i++) {
            System.out.println("\n--- Registro de persona " + (i + 1) + " ---");
            System.out.print("Nombre: ");
            String name = scanner.nextLine();
            System.out.print("Edad: ");
            int age = Integer.parseInt(scanner.nextLine());
            System.out.print("Teléfono (ej: 992563285): ");
            long phone = Long.parseLong(scanner.nextLine());

            persons[i] = new person(name, age, phone);
            contador++;
        }

        // Menú iterativo
        char option;
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("(r) Registrar");
            System.out.println("(n) Listar por nombre");
            System.out.println("(s) Buscar");
            System.out.println("(u) Actualizar");
            System.out.println("(x) Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextLine().toLowerCase().charAt(0);

            switch (option) {
                case 'r':
                    registrarPersona();
                    break;
                case 'n':
                    listarPorNombre();
                    break;
                case 's':
                    buscarPersona();
                    break;
                case 'u':
                    actualizarPersona();
                    break;
                case 'x':
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (option != 'x');

        scanner.close();
    }

    private static void registrarPersona() {
        if (contador >= persons.length) {
            System.out.println("\nNo hay espacio para más personas. Capacidad máxima: " + persons.length);
            return;
        }

        System.out.println("\n--- Registrar persona ---");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Edad: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Teléfono (ej: 992563285): ");
        long phone = Long.parseLong(scanner.nextLine());

        persons[contador] = new person(name, age, phone);
        contador++;
        System.out.println("Persona registrada exitosamente.");
    }

    private static void listarPorNombre() {
        if (contador == 0) {
            System.out.println("\nNo hay personas registradas.");
            return;
        }

        // Crear copia del array con solo los elementos válidos
        person[] temp = new person[contador];
        for (int i = 0; i < contador; i++) {
            temp[i] = persons[i];
        }

        // Ordenamiento por burbuja (alfabético por nombre)
        for (int i = 0; i < temp.length - 1; i++) {
            for (int j = 0; j < temp.length - i - 1; j++) {
                if (temp[j].name.compareToIgnoreCase(temp[j + 1].name) > 0) {
                    person aux = temp[j];
                    temp[j] = temp[j + 1];
                    temp[j + 1] = aux;
                }
            }
        }

        System.out.println("\n=== LISTA DE PERSONAS (ordenadas por nombre) ===");
        for (int i = 0; i < temp.length; i++) {
            System.out.println((i + 1) + ". " + temp[i].info());
        }
    }

    private static void buscarPersona() {
        System.out.print("\nIngrese el nombre a buscar: ");
        String nameFind = scanner.nextLine();
        boolean encontrado = false;

        for (int i = 0; i < contador; i++) {
            if (persons[i].name.equalsIgnoreCase(nameFind)) {
                System.out.println("Persona encontrada:");
                System.out.println(persons[i].info());
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Registro no encontrado.");
        }
    }

    private static void actualizarPersona() {
        System.out.print("\nIngrese el nombre de la persona a actualizar: ");
        String nameFind = scanner.nextLine();
        boolean encontrado = false;

        for (int i = 0; i < contador; i++) {
            if (persons[i].name.equalsIgnoreCase(nameFind)) {
                System.out.println("Persona encontrada. Datos actuales:");
                System.out.println(persons[i].info());
                System.out.println("\nIngrese los nuevos datos:");
                System.out.print("Nuevo nombre: ");
                persons[i].name = scanner.nextLine();
                System.out.print("Nueva edad: ");
                persons[i].age = Integer.parseInt(scanner.nextLine());
                System.out.print("Nuevo teléfono: ");
                persons[i].phone = Long.parseLong(scanner.nextLine());

                System.out.println("Datos actualizados correctamente.");
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Registro no encontrado.");
        }
    }
}