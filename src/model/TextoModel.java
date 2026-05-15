package model;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * Modelo: gestiona la lógica de negocio.
 * Lee/escribe el archivo de texto y aplica expresiones regulares.
 */
public class TextoModel {

    private static final String RUTA_ARCHIVO = "practica06/archivo.txt";

    // ---------------------------------------------------------------
    // Lectura del archivo
    // ---------------------------------------------------------------
    public String leerArchivo() throws IOException {
        Path path = Paths.get(RUTA_ARCHIVO);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
            return "";
        }
        return new String(Files.readAllBytes(path));
    }

    // ---------------------------------------------------------------
    // Escritura del archivo
    // ---------------------------------------------------------------
    public void guardarArchivo(String contenido) throws IOException {
        Path path = Paths.get(RUTA_ARCHIVO);
        Files.createDirectories(path.getParent());
        Files.write(path, contenido.getBytes());
    }

    // ---------------------------------------------------------------
    // Análisis con expresión regular
    // ---------------------------------------------------------------
    public List<String> buscarCoincidencias(String texto, String expresionRegular) {
        List<String> coincidencias = new ArrayList<>();
        if (texto == null || texto.isEmpty() || expresionRegular == null || expresionRegular.isEmpty()) {
            return coincidencias;
        }
        try {
            Pattern patron = Pattern.compile(expresionRegular);
            Matcher matcher = patron.matcher(texto);
            while (matcher.find()) {
                coincidencias.add(matcher.group());
            }
        } catch (PatternSyntaxException e) {
            // Expresión inválida — se retorna lista vacía
        }
        return coincidencias;
    }

    public String getRutaArchivo() {
        return RUTA_ARCHIVO;
    }
}
