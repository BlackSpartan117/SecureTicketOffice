/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.sql.Date;

/**
 *
 * @author kars
 */
public class cuenta {
    private int id;
    private String nombre;
    private String apellido;
    private String noTarjetaCredito;
    private String cvv;
    private double saldo;
    private Date vigencia;


public cuenta(){

}

public cuenta(int id, String nombre, String apellido, String noTarjetaCredito, String cvv, double saldo, Date vigencia){
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.noTarjetaCredito = noTarjetaCredito;
    this.cvv = cvv;
    this.saldo = saldo;
    this.vigencia = vigencia;
}

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the noTarjetaCredito
     */
    public String getNoTarjetaCredito() {
        return noTarjetaCredito;
    }

    /**
     * @param noTarjetaCredito the noTarjetaCredito to set
     */
    public void setNoTarjetaCredito(String noTarjetaCredito) {
        this.noTarjetaCredito = noTarjetaCredito;
    }

    /**
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * @return the vigencia
     */
    public Date getVigencia() {
        return vigencia;
    }

    /**
     * @param vigencia the vigencia to set
     */
    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * @return the cvv
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * @param cvv the cvv to set
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

}