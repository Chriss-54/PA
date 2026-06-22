package controller;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import model.accountDAO;
import model.account;

public class mgAccount {
    private accountDAO adao;
    private List<account> cuentas; // Lista de todas las cuentas
    private account cuentaActual; // Cuenta con la que se trabaja actualmente
    private int nCuentaActual;
    
    public mgAccount() {
        this.adao = new accountDAO();
        this.cuentas = new ArrayList<>();
        this.nCuentaActual = -1;
    }
    
    private int getRandom() {
        return (int)(Math.random() * 10);
    }
    
    // CREAR NUEVA CUENTA (siempre crea una nueva)
    public synchronized boolean createAccount() {
        // Generar número de cuenta único
        String numberAcount = "12";
        for (int i = 0; i <= 7; i++) {
            numberAcount += getRandom();
        }
        
        int nCuenta = Integer.parseInt(numberAcount);
        double saldoInicial = 100.0;
        
        account nuevaCuenta = new account(
            nCuenta,
            saldoInicial,
            0,  // Movimiento: CREACIÓN
            0,  // Monto
            new Date()
        );
        
        // Guardar en la lista
        cuentas.add(nuevaCuenta);
        
        // Establecer como cuenta actual
        cuentaActual = nuevaCuenta;
        nCuentaActual = nCuenta;
        
        // Guardar en archivo
        adao = new accountDAO(nuevaCuenta);
        try {
            adao.writerAccount();
            System.out.println("✅ CUENTA CREADA: " + nCuenta + " | Saldo: $" + saldoInicial);
            System.out.println("📊 Total de cuentas: " + cuentas.size());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // SELECCIONAR CUENTA PARA OPERAR
    public synchronized boolean seleccionarCuenta(int nCuenta) {
        for (account acc : cuentas) {
            if (acc.getNCuenta() == nCuenta) {
                cuentaActual = acc;
                nCuentaActual = nCuenta;
                System.out.println("🔍 Cuenta seleccionada: " + nCuenta + " | Saldo: $" + acc.getSaldo());
                return true;
            }
        }
        System.out.println("❌ Cuenta no encontrada: " + nCuenta);
        return false;
    }
    
    // DEPÓSITO EN CUENTA SELECCIONADA
    public synchronized double realizarDeposito(double monto) {
        if (cuentaActual == null) {
            System.out.println("❌ No hay cuenta seleccionada");
            return -1;
        }
        
        if (monto <= 0) {
            System.out.println("❌ El monto debe ser mayor a 0");
            return -1;
        }
        
        try {
            double saldoActual = cuentaActual.getSaldo();
            double nuevoSaldo = saldoActual + monto;
            
            // Crear nuevo registro de cuenta con el depósito
            account nuevaCuenta = new account(
                nCuentaActual,
                nuevoSaldo,
                1,  // Movimiento: DEPÓSITO
                monto,
                new Date()
            );
            
            // Actualizar en la lista
            actualizarCuentaEnLista(nuevaCuenta);
            
            // Guardar en archivo
            adao = new accountDAO(nuevaCuenta);
            adao.writerAccount();
            
            System.out.println("💰 DEPÓSITO: +$" + monto + " | Saldo: $" + nuevoSaldo);
            return nuevoSaldo;
            
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // RETIRO EN CUENTA SELECCIONADA
    public synchronized double realizarRetiro(double monto) {
        if (cuentaActual == null) {
            System.out.println("❌ No hay cuenta seleccionada");
            return -1;
        }
        
        if (monto <= 0) {
            System.out.println("❌ El monto debe ser mayor a 0");
            return -1;
        }
        
        try {
            double saldoActual = cuentaActual.getSaldo();
            
            if (saldoActual < monto) {
                System.out.println("❌ Saldo insuficiente: $" + saldoActual + " < $" + monto);
                return -2;
            }
            
            double nuevoSaldo = saldoActual - monto;
            
            // Crear nuevo registro de cuenta con el retiro
            account nuevaCuenta = new account(
                nCuentaActual,
                nuevoSaldo,
                2,  // Movimiento: RETIRO
                monto,
                new Date()
            );
            
            // Actualizar en la lista
            actualizarCuentaEnLista(nuevaCuenta);
            
            // Guardar en archivo
            adao = new accountDAO(nuevaCuenta);
            adao.writerAccount();
            
            System.out.println("💸 RETIRO: -$" + monto + " | Saldo: $" + nuevoSaldo);
            return nuevoSaldo;
            
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // CONSULTAR UNA CUENTA ESPECÍFICA
    public synchronized account consultarCuenta(int nCuenta) {
        // Buscar en la lista
        for (account acc : cuentas) {
            if (acc.getNCuenta() == nCuenta) {
                System.out.println("📊 Consulta: Cuenta " + nCuenta + " | Saldo: $" + acc.getSaldo());
                return acc;
            }
        }
        System.out.println("❌ Cuenta no encontrada: " + nCuenta);
        return null;
    }
    
    // OBTENER TODAS LAS CUENTAS
    public synchronized List<account> getTodasLasCuentas() {
        return new ArrayList<>(cuentas);
    }
    
    // OBTENER CUENTA ACTUAL
    public synchronized account getCuentaActual() {
        return cuentaActual;
    }
    
    public synchronized int getNumeroCuentaActual() {
        return nCuentaActual;
    }
    
    public synchronized double getSaldoActual() {
        return cuentaActual != null ? cuentaActual.getSaldo() : 0;
    }
    
    public synchronized boolean tieneCuentaSeleccionada() {
        return cuentaActual != null;
    }
    
    // ACTUALIZAR CUENTA EN LA LISTA
    private void actualizarCuentaEnLista(account nuevaCuenta) {
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getNCuenta() == nuevaCuenta.getNCuenta()) {
                cuentas.set(i, nuevaCuenta);
                cuentaActual = nuevaCuenta;
                break;
            }
        }
    }
    
    // OBTENER HISTORIAL DE UNA CUENTA (todos los movimientos)
    public synchronized List<account> getHistorialCuenta(int nCuenta) {
        List<account> historial = new ArrayList<>();
        for (account acc : cuentas) {
            if (acc.getNCuenta() == nCuenta) {
                historial.add(acc);
            }
        }
        return historial;
    }
}