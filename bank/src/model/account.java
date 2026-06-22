package model;
import java.util.Date;
import libreria_generica.generic;

public class account extends generic<Integer, Double> {
    private Date fecha;

    public account() {
    }

    public account(int nCuenta, double saldo, int movimiento, double monto, Date fecha) {
        super(nCuenta, movimiento, saldo, monto);
        this.fecha = fecha;
    }

    public int getNCuenta() {
        return getAttributeT1();
    }
    public void setNCuenta(int nCuenta) {
        setAttributeT1(nCuenta);
    }
    public int getMovimiento() {
        return getAttributeT2();
    }
    public void setMovimiento(int movimiento) {
        setAttributeT2(movimiento);
    }
    public double getSaldo() {
        return getAttributeS3();
    }
    public void setSaldo(double saldo) {
        setAttributeS3(saldo);
    }
    public double getMonto() {
        return getAttribteS4();
    }
    public void setMonto(double monto) {
        setAttribteS4(monto);
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return String.format("%d;%.2f;%d;%.2f;%s%n",
                getNCuenta(),
                getSaldo(),
                getMovimiento(),
                getMonto(),
                getFecha()
        );
    }
}
