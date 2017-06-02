package com.bankescomaester.entities;

/**
 *
 * @author Master Chief
 */
public class ReciboPago {
    private String movimiento;
    private String referencia;
    private String nombre;
    private String cuenta;
    private String importe;

    public ReciboPago() {
    }

    public ReciboPago(String movimiento, String referencia, String nombre, String cuenta, String importe) {
        this.movimiento = movimiento;
        this.referencia = referencia;
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.importe = importe;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }
}
