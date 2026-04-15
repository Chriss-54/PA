import java.util.Scanner;

public class AprobacionTernario {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== APROBACIÓN ESTUDIANTE (OPERADOR TERNARIO) ===");
        
        System.out.print("Ingrese la nota del estudiante: ");
        double nota = sc.nextDouble();
        
        System.out.print("Ingrese la nota mínima para aprobar: ");
        double notaMinima = sc.nextDouble();
        
        // Uso del operador ternario
        String resultado = (nota >= notaMinima) ? "APROBADO" : "REPROBADO";
        
        System.out.println("\nNota obtenida: " + nota);
        System.out.println("Nota mínima requerida: " + notaMinima);
        System.out.println("Resultado: El estudiante está " + resultado);
        
        sc.close();
    }
}