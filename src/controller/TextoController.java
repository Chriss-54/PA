package controller;

import model.TextoModel;
import view.TextoView;

import java.io.IOException;
import java.util.List;

/**
 * Controlador: conecta el Modelo con la Vista.
 * Recibe eventos de la Vista, delega la lógica al Modelo
 * y actualiza la Vista con los resultados.
 */
public class TextoController {

    private final TextoModel modelo;
    private final TextoView  vista;

    public TextoController(TextoModel modelo, TextoView vista) {
        this.modelo = modelo;
        this.vista  = vista;
        inicializar();
    }

    // ---------------------------------------------------------------
    // Inicialización: cargar archivo y registrar listeners
    // ---------------------------------------------------------------
    private void inicializar() {
        cargarArchivoEnVista();
        vista.agregarListenerGuardar(e -> guardarArchivo());
        vista.agregarListenerAnalizar(e -> analizarTexto());
    }

    // ---------------------------------------------------------------
    // Cargar contenido del archivo al JTextArea
    // ---------------------------------------------------------------
    private void cargarArchivoEnVista() {
        try {
            String contenido = modelo.leerArchivo();
            vista.setTextoArea(contenido);
        } catch (IOException ex) {
            vista.mostrarError("No se pudo leer el archivo:\n" + ex.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Guardar contenido del JTextArea en el archivo
    // ---------------------------------------------------------------
    private void guardarArchivo() {
        try {
            String contenido = vista.getTextoArea();
            modelo.guardarArchivo(contenido);
            vista.mostrarMensaje("Archivo guardado correctamente en:\n" + modelo.getRutaArchivo());
        } catch (IOException ex) {
            vista.mostrarError("Error al guardar el archivo:\n" + ex.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // Analizar texto con la expresión regular ingresada
    // ---------------------------------------------------------------
    private void analizarTexto() {
        String texto = vista.getTextoArea();
        String er    = vista.getExpresionRegular();

        if (er.trim().isEmpty()) {
            vista.mostrarError("Por favor ingrese una Expresión Regular.");
            return;
        }

        List<String> coincidencias = modelo.buscarCoincidencias(texto, er);
        vista.setNumeroPalabras(coincidencias.size());
        vista.setListaPalabras(coincidencias);
    }
}
