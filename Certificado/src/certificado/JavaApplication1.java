/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package certificado;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;

public class JavaApplication1 {
    
    public static void main(String[] args) {
        CifradorRSA cifrador = new CifradorRSA();
        KeyPair llavesCliente = cifrador.generarLlaves(2048);
        Certificado cer;
        /********* Generar certificado **********/
        /*
        cer = new Certificado("1010", "Alan Hernandez Robles", "10/02/1990", llavesCliente.getPublic(), "Banxico", "01/01/2020");
        PrivateKey llavePrivadaBanco = (PrivateKey) cifrador.leerLlave("llaves/private.key", CifradorRSA.TipoLlave.PRIVADA);
        cer.firmar(llavePrivadaBanco);
        cifrador.guardarLlave("certificados/" + cer.getId() + ".key", llavesCliente.getPrivate(), CifradorRSA.TipoLlave.PRIVADA);
        System.out.println("Certificado generado");
        System.out.println(cer);
        */
        /********* Leer y validar certificado ********/
        PublicKey llavePublicaBanco = (PublicKey) cifrador.leerLlave("llaves/public.key", CifradorRSA.TipoLlave.PUBLICA);
        cer = new Certificado();
        boolean valido = cer.leerCertificado("certificados/1010.cer", llavePublicaBanco);
        System.out.println("Certificado " + (valido ? "valido" : "invalido"));
        System.out.println("Certificado leido: ");
        System.out.println(cer);
    }
}