package main;

import controller.TextoController;
import model.TextoModel;
import view.TextoView;

import javax.swing.*;

/**
 * Punto de entrada de la aplicación.
 * Instancia Modelo, Vista y Controlador (patrón MVC).
 */
public class Main {

    public static void main(String[] args) {
        // Ejecutar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            TextoModel      modelo      = new TextoModel();
            TextoView       vista       = new TextoView();
            new TextoController(modelo, vista);
            vista.setVisible(true);
        });
    }
}
