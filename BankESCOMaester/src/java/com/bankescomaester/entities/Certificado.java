package com.bankescomaester.entities;
// Generated 1/06/2017 03:13:49 PM by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Certificado generated by hbm2java
 */
public class Certificado  implements java.io.Serializable {


     private int idCliente;
     private Cuenta cuenta;
     private Date cumple;
     private String apellido;
     private Date expiracion;
     private byte[] publicKey;
     private byte[] firmaBank;

    public Certificado() {
    }

    public Certificado(Cuenta cuenta, Date cumple, String apellido, Date expiracion, byte[] publicKey, byte[] firmaBank) {
       this.cuenta = cuenta;
       this.cumple = cumple;
       this.apellido = apellido;
       this.expiracion = expiracion;
       this.publicKey = publicKey;
       this.firmaBank = firmaBank;
    }
   
    public int getIdCliente() {
        return this.idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public Cuenta getCuenta() {
        return this.cuenta;
    }
    
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    public Date getCumple() {
        return this.cumple;
    }
    
    public void setCumple(Date cumple) {
        this.cumple = cumple;
    }
    public String getApellido() {
        return this.apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Date getExpiracion() {
        return this.expiracion;
    }
    
    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }
    public byte[] getPublicKey() {
        return this.publicKey;
    }
    
    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
    public byte[] getFirmaBank() {
        return this.firmaBank;
    }
    
    public void setFirmaBank(byte[] firmaBank) {
        this.firmaBank = firmaBank;
    }




}


