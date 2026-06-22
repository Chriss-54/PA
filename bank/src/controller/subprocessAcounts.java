package controller;

import javax.swing.table.DefaultTableModel;
import model.account;

public class subprocessAcounts extends Thread {
    private mgAccount mga;
    private DefaultTableModel modeloTabla;
    private int nCuentaConsultar;
    private boolean ejecutando = true;
    
    public subprocessAcounts(mgAccount mga, DefaultTableModel modeloTabla, int nCuentaConsultar) {
        super();
        this.mga = mga;
        this.modeloTabla = modeloTabla;
        this.nCuentaConsultar = nCuentaConsultar;
    }
    
    @Override
    public void run() {
        while (ejecutando) {
            try {
                sleep(3000); // Consultar cada 3 segundos
                
                account cuenta = mga.consultarCuenta(nCuentaConsultar);
                
                if (cuenta != null) {
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            // Obtener historial completo de la cuenta
                            java.util.List<account> historial = mga.getHistorialCuenta(nCuentaConsultar);
                            modeloTabla.setRowCount(0);
                            
                            if (historial.isEmpty()) {
                                modeloTabla.addRow(new Object[]{
                                    String.valueOf(nCuentaConsultar),
                                    "SIN MOVIMIENTOS",
                                    "0.00",
                                    "0.00",
                                    new java.util.Date().toString()
                                });
                            } else {
                                for (account acc : historial) {
                                    modeloTabla.addRow(new Object[]{
                                        String.valueOf(acc.getNCuenta()),
                                        getTipoMovimiento(acc.getMovimiento()),
                                        String.format("%.2f", acc.getMonto()),
                                        String.format("%.2f", acc.getSaldo()),
                                        acc.getFecha().toString()
                                    });
                                }
                            }
                        }
                    });
                }
                
            } catch (InterruptedException e) {
                ejecutando = false;
                System.out.println("🛑 Hilo de consultas detenido");
            }
        }
    }
    
    private String getTipoMovimiento(int movimiento) {
        switch (movimiento) {
            case 1: return "DEPÓSITO";
            case 2: return "RETIRO";
            case 0: return "CREACIÓN";
            default: return "DESCONOCIDO";
        }
    }
    
    public void detener() {
        ejecutando = false;
        this.interrupt();
    }
}