package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.logic_view_bank;

public class view_bank extends JFrame {
    
    public JButton btn_cuentas;
    public JButton btn_depositos;
    public JButton btn_pagos;
    public JButton btn_consultar;
    public JButton btn_ver_todas;
    public JButton btn_detener;
    
    public JTable tablaOpciones;
    public DefaultTableModel modeloTabla;
    
    public view_bank() {
        setTitle("Banco - Sistema de Cuentas");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        inicializarComponentes();
        new logic_view_bank(this);
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        // Panel superior con los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panelBotones.setBackground(new Color(240, 240, 240));
        
        btn_cuentas = new JButton(" CREAR CUENTA");
        btn_depositos = new JButton(" DEPÓSITO");
        btn_pagos = new JButton("RETIRO");
        btn_consultar = new JButton(" CONSULTAR");
        btn_ver_todas = new JButton(" VER TODAS");
        btn_detener = new JButton(" DETENER");
        
        // Estilo de botones
        Font btnFont = new Font("Arial", Font.BOLD, 11);
        btn_cuentas.setFont(btnFont);
        btn_depositos.setFont(btnFont);
        btn_pagos.setFont(btnFont);
        btn_consultar.setFont(btnFont);
        btn_ver_todas.setFont(btnFont);
        btn_detener.setFont(btnFont);
        
        btn_cuentas.setBackground(new Color(52, 152, 219));
        btn_cuentas.setForeground(Color.WHITE);
        btn_depositos.setBackground(new Color(46, 204, 113));
        btn_depositos.setForeground(Color.WHITE);
        btn_pagos.setBackground(new Color(231, 76, 60));
        btn_pagos.setForeground(Color.WHITE);
        btn_consultar.setBackground(new Color(241, 196, 15));
        btn_consultar.setForeground(Color.WHITE);
        btn_ver_todas.setBackground(new Color(155, 89, 182));
        btn_ver_todas.setForeground(Color.WHITE);
        btn_detener.setBackground(new Color(149, 165, 166));
        btn_detener.setForeground(Color.WHITE);
        
        btn_cuentas.setPreferredSize(new Dimension(130, 35));
        btn_depositos.setPreferredSize(new Dimension(110, 35));
        btn_pagos.setPreferredSize(new Dimension(110, 35));
        btn_consultar.setPreferredSize(new Dimension(110, 35));
        btn_ver_todas.setPreferredSize(new Dimension(120, 35));
        btn_detener.setPreferredSize(new Dimension(100, 35));
        
        panelBotones.add(btn_cuentas);
        panelBotones.add(btn_depositos);
        panelBotones.add(btn_pagos);
        panelBotones.add(btn_consultar);
        panelBotones.add(btn_ver_todas);
        panelBotones.add(btn_detener);
        
        add(panelBotones, BorderLayout.NORTH);
        
        // Panel central con la tabla
        String[] columnas = {"N° CUENTA", "MOVIMIENTO", "MONTO", "SALDO", "FECHA"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        
        tablaOpciones = new JTable(modeloTabla);
        tablaOpciones.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaOpciones.setRowHeight(30);
        tablaOpciones.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(tablaOpciones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        add(scrollPane, BorderLayout.CENTER);
    }
}