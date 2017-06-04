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
public class Certificado {
    private int idCliente;
    private Date cumple;
    private String apellido;
    private Date expiacion;
    private byte [] publicKey;
    private byte [] firmaBank;
    
    public Certificado(){
        
    } 
    
    public Certificado(int id, Date cumple, String apellido, Date expiacion, byte[] publicKey, byte[] firmaBank){
        this.idCliente = id;
        this.cumple = cumple;
        this.apellido = apellido;
        this.expiacion = expiacion;
        this.publicKey = publicKey;
        this.firmaBank = firmaBank;
    }
    
       public Certificado(Date cumple, String apellido, Date expiacion, byte[] publicKey, byte[] firmaBank){
        this.cumple = cumple;
        this.apellido = apellido;
        this.expiacion = expiacion;
        this.publicKey = publicKey;
        this.firmaBank = firmaBank;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the cumple
     */
    public Date getCumple() {
        return cumple;
    }

    /**
     * @param cumple the cumple to set
     */
    public void setCumple(Date cumple) {
        this.cumple = cumple;
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
     * @return the expiacion
     */
    public Date getExpiacion() {
        return expiacion;
    }

    /**
     * @param expiacion the expiacion to set
     */
    public void setExpiacion(Date expiacion) {
        this.expiacion = expiacion;
    }

    /**
     * @return the publicKey
     */
    public byte[] getPublicKey() {
        return publicKey;
    }

    /**
     * @param publicKey the publicKey to set
     */
    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * @return the firmaBank
     */
    public byte[] getFirmaBank() {
        return firmaBank;
    }

    /**
     * @param firmaBank the firmaBank to set
     */
    public void setFirmaBank(byte[] firmaBank) {
        this.firmaBank = firmaBank;
    }
    
    
}
