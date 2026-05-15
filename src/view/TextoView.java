package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Vista: interfaz gráfica Swing.
 * Contiene todos los componentes visuales sin lógica de negocio.
 */
public class TextoView extends JFrame {

    // --- Sección: Archivo de texto ---
    private final JTextArea  txtAreaArchivo;
    private final JButton    btnGuardar;

    // --- Sección: Analizar datos ---
    private final JTextField txtExpresionRegular;
    private final JButton    btnAnalizar;
    private final JTextField txtNumeroPalabras;
    private final JList<String> lstPalabras;
    private final DefaultListModel<String> modeloLista;

    // ---------------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------------
    public TextoView() {
        super("Analizador de Textos con Expresiones Regulares");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(680, 680);
        setLocationRelativeTo(null);
        setResizable(false);

        // Componentes
        txtAreaArchivo      = new JTextArea();
        btnGuardar          = new JButton("GUARDAR");
        txtExpresionRegular = new JTextField();
        btnAnalizar         = new JButton("...");
        txtNumeroPalabras   = new JTextField();
        modeloLista         = new DefaultListModel<>();
        lstPalabras         = new JList<>(modeloLista);

        txtNumeroPalabras.setEditable(false);
        txtAreaArchivo.setLineWrap(true);
        txtAreaArchivo.setWrapStyleWord(true);
        lstPalabras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        construirUI();
    }

    // ---------------------------------------------------------------
    // Construcción del layout
    // ---------------------------------------------------------------
    private void construirUI() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        // ---- Panel superior: Archivo de texto ----
        JPanel panelArchivo = new JPanel(new BorderLayout(0, 6));
        panelArchivo.setBorder(new TitledBorder("Archivo de texto"));

        JScrollPane scrollTexto = new JScrollPane(txtAreaArchivo);
        scrollTexto.setPreferredSize(new Dimension(0, 220));

        JPanel panelBtnGuardar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBtnGuardar.add(btnGuardar);

        panelArchivo.add(scrollTexto,       BorderLayout.CENTER);
        panelArchivo.add(panelBtnGuardar,   BorderLayout.SOUTH);

        // ---- Panel inferior: Analizar datos ----
        JPanel panelAnalizar = new JPanel();
        panelAnalizar.setLayout(new GridBagLayout());
        panelAnalizar.setBorder(new TitledBorder("Analizar datos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Fila 0: Expresión regular
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panelAnalizar.add(new JLabel("EXPRESIÓN REGULAR"), gbc);

        gbc.gridx = 1; gbc.weightx = 1.0;
        panelAnalizar.add(txtExpresionRegular, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        panelAnalizar.add(btnAnalizar, gbc);

        // Fila 1: Número de palabras encontradas
        gbc.gridx = 0; gbc.gridy = 1;
        panelAnalizar.add(new JLabel("Número de palabras encontradas"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 2;
        panelAnalizar.add(txtNumeroPalabras, gbc);
        gbc.gridwidth = 1;

        // Fila 2: Listado de palabras (label)
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.NORTHWEST;
        panelAnalizar.add(new JLabel("Listado de palabras:"), gbc);

        // Fila 2-4: JList con scroll
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0; gbc.gridheight = 3;
        JScrollPane scrollLista = new JScrollPane(lstPalabras);
        scrollLista.setPreferredSize(new Dimension(0, 160));
        panelAnalizar.add(scrollLista, gbc);

        // Ensamblar panel principal
        panelPrincipal.add(panelArchivo);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(panelAnalizar);

        add(panelPrincipal);
    }

    // ---------------------------------------------------------------
    // Métodos públicos que usa el Controlador
    // ---------------------------------------------------------------
    public void agregarListenerGuardar(ActionListener listener) {
        btnGuardar.addActionListener(listener);
    }

    public void agregarListenerAnalizar(ActionListener listener) {
        btnAnalizar.addActionListener(listener);
    }

    public String getTextoArea() {
        return txtAreaArchivo.getText();
    }

    public void setTextoArea(String texto) {
        txtAreaArchivo.setText(texto);
        txtAreaArchivo.setCaretPosition(0);
    }

    public String getExpresionRegular() {
        return txtExpresionRegular.getText();
    }

    public void setNumeroPalabras(int cantidad) {
        txtNumeroPalabras.setText(String.valueOf(cantidad));
    }

    public void setListaPalabras(List<String> palabras) {
        modeloLista.clear();
        for (String p : palabras) {
            modeloLista.addElement(p);
        }
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
