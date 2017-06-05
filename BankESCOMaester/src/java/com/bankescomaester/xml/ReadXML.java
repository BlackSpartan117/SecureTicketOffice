/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankescomaester.xml;

import banco.util.CifradorRSA;
import com.bankescomaester.entities.Cuenta;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;         // |
import org.jdom2.Element;          // |\ Librerías
import org.jdom2.JDOMException;    // |/ JDOM
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Master Chief
 */
public class ReadXML {
    
    private String tarjetaCliente;
    private String tarjetaTicket;
    private String monto;
    private String clave;
    
    public ReadXML() {
        
    }
    
    public void cargarXml( String xml ) {
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        InputStream streamXML = new ByteArrayInputStream( xml.getBytes( StandardCharsets.UTF_8 ) );
        
        try {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build( streamXML );

            //Se obtiene la raiz
            Element rootNode = document.getRootElement();

            //Se obtiene la lista de hijos de la raiz
            List<Element> list = rootNode.getChildren();
            
            Element elemento = (Element) list.get(0);
            setTarjetaCliente( elemento.getChild("cliente").getValue() );
            setTarjetaTicket( elemento.getChild("noTarjeta").getValue() );
            setMonto( elemento.getChild("monto").getValue() );
            setClave( elemento.getChild("clave").getValue() );
            
        } catch( JDOMException jdomex ) {
            System.out.println( jdomex.getMessage() );
        } catch( IOException ex ) {
            Logger.getLogger(ReadXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String getTarjetaCliente() {
        return tarjetaCliente;
    }

    public void setTarjetaCliente(String tarjetaCliente) {
        this.tarjetaCliente = tarjetaCliente;
    }

    public String getTarjetaTicket() {
        return tarjetaTicket;
    }

    public void setTarjetaTicket(String tarjetaTicket) {
        this.tarjetaTicket = tarjetaTicket;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
