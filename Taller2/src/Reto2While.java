import java.util.Scanner;

public class Reto2While {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== SUMA DE NÚMEROS PARES (WHILE) ===");
        System.out.print("Ingrese un número límite: ");
        int limite = sc.nextInt();
        
        int contador = 0;
        int sumaPares = 0;
        
        while (contador <= limite) {
            if (contador % 2 == 0) {
                sumaPares += contador;
            }
            contador++;
        }
        
        System.out.println("La suma de todos los números pares desde 0 hasta " 
                          + limite + " es: " + sumaPares);
        
        sc.close();
    }
}