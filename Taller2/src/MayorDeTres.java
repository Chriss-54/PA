import java.util.Scanner;

public class MayorDeTres {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== MAYOR DE TRES NÚMEROS (IF/ELSE IF) ===");
        
        System.out.print("Ingrese el primer número: ");
        int a = sc.nextInt();
        
        System.out.print("Ingrese el segundo número: ");
        int b = sc.nextInt();
        
        System.out.print("Ingrese el tercer número: ");
        int c = sc.nextInt();
        
        int mayor;
        
        if (a >= b && a >= c) {
            mayor = a;
        } else if (b >= a && b >= c) {
            mayor = b;
        } else {
            mayor = c;
        }
        
        System.out.println("\nNúmeros ingresados: " + a + ", " + b + ", " + c);
        System.out.println("El número mayor es: " + mayor);
        
        sc.close();
    }
}