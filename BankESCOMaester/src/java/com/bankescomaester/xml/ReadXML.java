/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankescomaester.xml;

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
    
    public ReadXML() {
        
    }
    
    public LinkedList<Cuenta> cargarXml( String xml ) {
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        InputStream streamXML = new ByteArrayInputStream( xml.getBytes( StandardCharsets.UTF_8 ) );
        LinkedList<Cuenta> cuentas = new LinkedList<>();
        
        try {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build( streamXML );

            //Se obtiene la raiz
            Element rootNode = document.getRootElement();

            //Se obtiene la lista de hijos de la raiz
            List<Element> list = rootNode.getChildren();
            Cuenta cuenta;
            
            //Se recorre la lista de hijos
            for( int i = 0; i < list.size(); i++ ) {
                //System.out.println( "ELEMENTO " + list.get(i).toString() );
                Element elemento = (Element) list.get(i);
                cuenta = new Cuenta();
                
                for( Element e : elemento.getChildren() ) {
                    //System.out.println("ELEMENTO e.value " + " " + e.getName() + " " + e.getValue() );
                    cuenta = setPropiedad( cuenta, e.getName(), e.getValue() );
                }
                
                cuentas.add( cuenta );
            }
            
        } catch( JDOMException jdomex ) {
            System.out.println( jdomex.getMessage() );
        } catch( IOException ex ) {
            Logger.getLogger(ReadXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cuentas;
    }
    
    private Cuenta setPropiedad( Cuenta c, String etiqueta, String propiedad ) {
        switch( etiqueta ) {
            case "nombre":
                c.setNombre(propiedad );
                break;
            case "noTarjeta":
                c.setNoTarjetaCredito(propiedad );
                break;
            case "cvv":
                c.setCvv(propiedad );
                break;
            case "vigencia":
                Calendar fecha = new GregorianCalendar();
                fecha.set(Integer.parseInt(propiedad.split("-")[0] ), Integer.parseInt(propiedad.split("-")[1] ), 1 );
                
                c.setVigencia( fecha.getTime() );
                break;
            case "precio":
                c.setSaldo(Double.parseDouble(propiedad ) );
                break;
        }
        
        return c;
    }
}
