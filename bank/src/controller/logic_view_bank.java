package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import view.view_bank;
import model.account;

public class logic_view_bank implements ActionListener {
    private view_bank vb;
    private mgAccount mga;
    private subprocessAcounts hiloConsultas;
    private int cuentaActualConsultando = -1;
    
    public logic_view_bank(view_bank vb) {
        super();
        this.vb = vb;
        this.mga = new mgAccount();
        
        this.vb.btn_cuentas.addActionListener(this);
        this.vb.btn_depositos.addActionListener(this);
        this.vb.btn_pagos.addActionListener(this);
        this.vb.btn_consultar.addActionListener(this);
        this.vb.btn_detener.addActionListener(this);
        this.vb.btn_ver_todas.addActionListener(this);
        
        JOptionPane.showMessageDialog(vb, 
            "💡 INSTRUCCIONES:\n" +
            "1. 'CREAR CUENTA' - Genera una nueva cuenta\n" +
            "2. 'CONSULTAR' - Ingresa el número de cuenta\n" +
            "3. 'DEPÓSITO' y 'RETIRO' - Operan sobre la cuenta seleccionada",
            "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vb.btn_cuentas) {
            System.out.println("=== CREAR CUENTA ===");
            
            // Crear nueva cuenta
            boolean creada = mga.createAccount();
            
            if (creada) {
                account cuenta = mga.getCuentaActual();
                
                // Preguntar si quiere seleccionar esta cuenta
                int respuesta = JOptionPane.showConfirmDialog(vb, 
                    "✅ Cuenta creada: " + cuenta.getNCuenta() + "\n" +
                    "Saldo inicial: $" + String.format("%.2f", cuenta.getSaldo()) + "\n\n" +
                    "¿Desea seleccionar esta cuenta para operar?",
                    "Cuenta Creada", JOptionPane.YES_NO_OPTION);
                
                if (respuesta == JOptionPane.YES_OPTION) {
                    cuentaActualConsultando = cuenta.getNCuenta();
                    
                    // Iniciar hilo de consultas para esta cuenta
                    iniciarConsultasAutomaticas(cuentaActualConsultando);
                    
                    mostrarCuentaEnTabla(cuenta);
                    JOptionPane.showMessageDialog(vb, 
                        "✅ Cuenta seleccionada: " + cuenta.getNCuenta() + "\n" +
                        "Saldo: $" + String.format("%.2f", cuenta.getSaldo()),
                        "Cuenta Seleccionada", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vb, 
                    "❌ Error al crear la cuenta", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } else if (e.getSource() == vb.btn_consultar) {
            System.out.println("=== CONSULTAR CUENTA ===");
            
            String input = JOptionPane.showInputDialog(vb, 
                "Ingrese el número de cuenta a consultar:",
                "CONSULTAR CUENTA", JOptionPane.QUESTION_MESSAGE);
            
            if (input != null && !input.isEmpty()) {
                try {
                    int nCuenta = Integer.parseInt(input);
                    account cuenta = mga.consultarCuenta(nCuenta);
                    
                    if (cuenta != null) {
                        cuentaActualConsultando = nCuenta;
                        
                        // Preguntar si quiere seleccionar esta cuenta
                        int respuesta = JOptionPane.showConfirmDialog(vb, 
                            "📊 CUENTA ENCONTRADA\n" +
                            "N° Cuenta: " + cuenta.getNCuenta() + "\n" +
                            "Saldo actual: $" + String.format("%.2f", cuenta.getSaldo()) + "\n" +
                            "Último movimiento: " + getTipoMovimiento(cuenta.getMovimiento()) + "\n" +
                            "Fecha: " + cuenta.getFecha() + "\n\n" +
                            "¿Desea seleccionar esta cuenta para operar?",
                            "Cuenta Encontrada", JOptionPane.YES_NO_OPTION);
                        
                        if (respuesta == JOptionPane.YES_OPTION) {
                            mga.seleccionarCuenta(nCuenta);
                            
                            // Iniciar hilo de consultas para esta cuenta
                            iniciarConsultasAutomaticas(nCuenta);
                            
                            JOptionPane.showMessageDialog(vb, 
                                "✅ Cuenta seleccionada: " + nCuenta,
                                "Selección Exitosa", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        // Mostrar todos los movimientos de la cuenta
                        mostrarHistorialCuenta(nCuenta);
                        
                    } else {
                        JOptionPane.showMessageDialog(vb, 
                            "❌ Cuenta no encontrada: " + nCuenta + "\n" +
                            "Total de cuentas creadas: " + mga.getTodasLasCuentas().size(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vb, 
                        "Ingrese un número válido", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } else if (e.getSource() == vb.btn_ver_todas) {
            System.out.println("=== VER TODAS LAS CUENTAS ===");
            mostrarTodasLasCuentas();
            
        } else if (e.getSource() == vb.btn_depositos) {
            System.out.println("=== DEPÓSITO ===");
            
            if (!mga.tieneCuentaSeleccionada()) {
                JOptionPane.showMessageDialog(vb, 
                    "Primero debe seleccionar una cuenta (usar 'CONSULTAR')", 
                    "Sin cuenta seleccionada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String montoStr = JOptionPane.showInputDialog(vb, 
                "💳 CUENTA: " + mga.getNumeroCuentaActual() + "\n" +
                "Saldo actual: $" + String.format("%.2f", mga.getSaldoActual()) + "\n\n" +
                "Ingrese el monto a depositar:",
                "DEPÓSITO", JOptionPane.QUESTION_MESSAGE);
            
            if (montoStr != null && !montoStr.isEmpty()) {
                try {
                    double monto = Double.parseDouble(montoStr);
                    if (monto > 0) {
                        double nuevoSaldo = mga.realizarDeposito(monto);
                        if (nuevoSaldo != -1) {
                            JOptionPane.showMessageDialog(vb, 
                                "✅ Depósito exitoso!\n" +
                                "Cuenta: " + mga.getNumeroCuentaActual() + "\n" +
                                "Monto: $" + String.format("%.2f", monto) + "\n" +
                                "Nuevo saldo: $" + String.format("%.2f", nuevoSaldo),
                                "DEPÓSITO", JOptionPane.INFORMATION_MESSAGE);
                            
                            // Actualizar la tabla con el historial
                            mostrarHistorialCuenta(mga.getNumeroCuentaActual());
                        }
                    } else {
                        JOptionPane.showMessageDialog(vb, 
                            "El monto debe ser mayor a 0", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vb, 
                        "Ingrese un número válido", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } else if (e.getSource() == vb.btn_pagos) {
            System.out.println("=== RETIRO ===");
            
            if (!mga.tieneCuentaSeleccionada()) {
                JOptionPane.showMessageDialog(vb, 
                    "Primero debe seleccionar una cuenta (usar 'CONSULTAR')", 
                    "Sin cuenta seleccionada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double saldoActual = mga.getSaldoActual();
            String montoStr = JOptionPane.showInputDialog(vb, 
                "💳 CUENTA: " + mga.getNumeroCuentaActual() + "\n" +
                "Saldo actual: $" + String.format("%.2f", saldoActual) + "\n\n" +
                "Ingrese el monto a retirar:",
                "RETIRO", JOptionPane.QUESTION_MESSAGE);
            
            if (montoStr != null && !montoStr.isEmpty()) {
                try {
                    double monto = Double.parseDouble(montoStr);
                    if (monto > 0) {
                        double resultado = mga.realizarRetiro(monto);
                        
                        if (resultado == -2) {
                            JOptionPane.showMessageDialog(vb, 
                                "❌ Saldo insuficiente!\n" +
                                "Saldo actual: $" + String.format("%.2f", saldoActual) + "\n" +
                                "Monto solicitado: $" + String.format("%.2f", monto) + "\n" +
                                "Faltan: $" + String.format("%.2f", monto - saldoActual),
                                "SALDO INSUFICIENTE", JOptionPane.WARNING_MESSAGE);
                        } else if (resultado == -1) {
                            JOptionPane.showMessageDialog(vb, 
                                "❌ Error al realizar el retiro", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(vb, 
                                "✅ Retiro exitoso!\n" +
                                "Cuenta: " + mga.getNumeroCuentaActual() + "\n" +
                                "Monto: $" + String.format("%.2f", monto) + "\n" +
                                "Nuevo saldo: $" + String.format("%.2f", resultado),
                                "RETIRO", JOptionPane.INFORMATION_MESSAGE);
                            
                            // Actualizar la tabla con el historial
                            mostrarHistorialCuenta(mga.getNumeroCuentaActual());
                        }
                    } else {
                        JOptionPane.showMessageDialog(vb, 
                            "El monto debe ser mayor a 0", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vb, 
                        "Ingrese un número válido", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        } else if (e.getSource() == vb.btn_detener) {
            System.out.println("=== DETENER CONSULTAS ===");
            if (hiloConsultas != null) {
                hiloConsultas.detener();
                JOptionPane.showMessageDialog(vb, 
                    "⏹️ Consultas automáticas detenidas", 
                    "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vb, 
                    "No hay consultas activas", 
                    "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void iniciarConsultasAutomaticas(int nCuenta) {
        if (hiloConsultas != null) {
            hiloConsultas.detener();
        }
        
        hiloConsultas = new subprocessAcounts(mga, vb.modeloTabla, nCuenta);
        hiloConsultas.start();
        System.out.println("🔄 Hilo de consultas iniciado para cuenta: " + nCuenta + " (cada 3 segundos)");
    }
    
    private void mostrarCuentaEnTabla(account cuenta) {
        vb.modeloTabla.setRowCount(0);
        
        vb.modeloTabla.addRow(new Object[]{
            String.valueOf(cuenta.getNCuenta()),
            getTipoMovimiento(cuenta.getMovimiento()),
            String.format("%.2f", cuenta.getMonto()),
            String.format("%.2f", cuenta.getSaldo()),
            cuenta.getFecha().toString()
        });
    }
    
    private void mostrarHistorialCuenta(int nCuenta) {
        List<account> historial = mga.getHistorialCuenta(nCuenta);
        
        vb.modeloTabla.setRowCount(0);
        
        if (historial.isEmpty()) {
            vb.modeloTabla.addRow(new Object[]{
                String.valueOf(nCuenta),
                "SIN MOVIMIENTOS",
                "0.00",
                "0.00",
                new Date().toString()
            });
            return;
        }
        
        // Mostrar todos los movimientos de la cuenta
        for (account acc : historial) {
            vb.modeloTabla.addRow(new Object[]{
                String.valueOf(acc.getNCuenta()),
                getTipoMovimiento(acc.getMovimiento()),
                String.format("%.2f", acc.getMonto()),
                String.format("%.2f", acc.getSaldo()),
                acc.getFecha().toString()
            });
        }
        
        System.out.println("📊 Historial de cuenta " + nCuenta + ": " + historial.size() + " movimientos");
    }
    
    private void mostrarTodasLasCuentas() {
        List<account> todas = mga.getTodasLasCuentas();
        
        vb.modeloTabla.setRowCount(0);
        
        if (todas.isEmpty()) {
            JOptionPane.showMessageDialog(vb, 
                "No hay cuentas creadas", 
                "Sin Cuentas", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        for (account acc : todas) {
            vb.modeloTabla.addRow(new Object[]{
                String.valueOf(acc.getNCuenta()),
                getTipoMovimiento(acc.getMovimiento()),
                String.format("%.2f", acc.getMonto()),
                String.format("%.2f", acc.getSaldo()),
                acc.getFecha().toString()
            });
        }
        
        JOptionPane.showMessageDialog(vb, 
            "📊 Total de cuentas: " + todas.size(),
            "Todas las Cuentas", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String getTipoMovimiento(int movimiento) {
        switch (movimiento) {
            case 1: return "DEPÓSITO";
            case 2: return "RETIRO";
            case 0: return "CREACIÓN";
            default: return "DESCONOCIDO";
        }
    }
}