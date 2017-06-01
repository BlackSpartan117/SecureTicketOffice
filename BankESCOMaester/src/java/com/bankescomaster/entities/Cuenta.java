package com.bankescomaster.entities;


import java.util.Date;

public class Cuenta  implements java.io.Serializable {

     private Integer id;
     private String nombre;
     private String apellido;
     private String noTarjetaCredito;
     private double saldo;
     private Date vigencia;

    public Cuenta() {
    }

	
    public Cuenta(String nombre, String noTarjetaCredito, double saldo) {
        this.nombre = nombre;
        this.noTarjetaCredito = noTarjetaCredito;
        this.saldo = saldo;
    }
    public Cuenta(String nombre, String apellido, String noTarjetaCredito, double saldo, Date vigencia) {
       this.nombre = nombre;
       this.apellido = apellido;
       this.noTarjetaCredito = noTarjetaCredito;
       this.saldo = saldo;
       this.vigencia = vigencia;
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
}


