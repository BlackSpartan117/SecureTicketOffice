package com.bankescomaester.entities;
// Generated 1/06/2017 09:37:07 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Cuenta generated by hbm2java
 */
public class Cuenta  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String apellido;
     private String noTarjetaCredito;
     private String cvv;
     private double saldo;
     private Date vigencia;
     private Certificado certificado;

    public Cuenta() {
    }

	
    public Cuenta(String nombre, String apellido, String noTarjetaCredito, String cvv, double saldo, Date vigencia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.noTarjetaCredito = noTarjetaCredito;
        this.cvv = cvv;
        this.saldo = saldo;
        this.vigencia = vigencia;
    }
    public Cuenta(String nombre, String apellido, String noTarjetaCredito, String cvv, double saldo, Date vigencia, Certificado certificado) {
       this.nombre = nombre;
       this.apellido = apellido;
       this.noTarjetaCredito = noTarjetaCredito;
       this.cvv = cvv;
       this.saldo = saldo;
       this.vigencia = vigencia;
       this.certificado = certificado;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return this.apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getNoTarjetaCredito() {
        return this.noTarjetaCredito;
    }
    
    public void setNoTarjetaCredito(String noTarjetaCredito) {
        this.noTarjetaCredito = noTarjetaCredito;
    }
    public String getCvv() {
        return this.cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public double getSaldo() {
        return this.saldo;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    public Date getVigencia() {
        return this.vigencia;
    }
    
    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }
    public Certificado getCertificado() {
        return this.certificado;
    }
    
    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }




}


