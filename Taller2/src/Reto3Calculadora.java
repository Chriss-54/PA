import java.util.Scanner;

public class Reto3Calculadora {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String continuar;
        System.out.println("=== CALCULADORA INTERACTIVA ===");
        do {
            double num1 = 0, num2 = 0;
            boolean entradaValida = false;
            while (!entradaValida) {
                System.out.print("\nIngrese el primer número: ");
                if (sc.hasNextDouble()) {
                    num1 = sc.nextDouble();
                    entradaValida = true;
                } else {
                    System.out.println("Error: Debe ingresar un número válido.");
                    sc.next(); 
                }
            }
            entradaValida = false;
            while (!entradaValida) {
                System.out.print("Ingrese el segundo número: ");
                if (sc.hasNextDouble()) {
                    num2 = sc.nextDouble();
                    entradaValida = true;
                } else {
                    System.out.println("Error: Debe ingresar un número válido.");
                    sc.next();
                }
            }
            sc.nextLine(); 
            System.out.print("Ingrese la operación (+, -, *, /): ");
            String operacion = sc.nextLine();
            double resultado = 0;
            boolean operacionValida = true;
            switch (operacion) {
                case "+":
                    resultado = num1 + num2;
                    System.out.println(num1 + " + " + num2 + " = " + resultado);
                    break;
                case "-":
                    resultado = num1 - num2;
                    System.out.println(num1 + " - " + num2 + " = " + resultado);
                    break;
                case "*":
                    resultado = num1 * num2;
                    System.out.println(num1 + " * " + num2 + " = " + resultado);
                    break;
                case "/":
                    if (num2 == 0) {
                        System.out.println("Error: No se puede dividir por cero.");
                        operacionValida = false;
                    } else {
                        resultado = num1 / num2;
                        System.out.println(num1 + " / " + num2 + " = " + resultado);
                    }
                    break;
                default:
                    System.out.println("Error: Operación no válida.");
                    operacionValida = false;
            }
            
            System.out.print("\n¿Desea realizar otra operación? (s/n): ");
            continuar = sc.nextLine();
            
        } while (continuar.equalsIgnoreCase("s"));
        
        System.out.println("¡Gracias por usar la calculadora!");
        sc.close();
    }
}