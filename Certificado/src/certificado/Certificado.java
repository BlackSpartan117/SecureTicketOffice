/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package certificado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edgar
 */
public class Certificado {
    private String id;
    private String nombre;
    private String fechaDeNacimiento;
    private PublicKey llave;
    private String certificador;
    private byte []firma;
    
    public Certificado(String id, String nombre, String fecha, PublicKey llave, String certificador){
        this.id                = id;
        this.nombre            = nombre;
        this.fechaDeNacimiento = fecha;
        this.llave             = llave;
        this.certificador      = certificador;
    }
    
    public void firmar(PrivateKey llave){
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.llave.getEncoded());
        byte []llavePublica = x509EncodedKeySpec.getEncoded();
        byte []len = new byte[2];
        len[0] = (byte)(llavePublica.length/256);
        len[1] = (byte)(llavePublica.length%256);
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);
        buffer.put("Id:".getBytes());
        buffer.put(id.getBytes());
        buffer.put("Nombre:".getBytes());
        buffer.put(nombre.getBytes());
        buffer.put("Fecha de nacimiento:".getBytes());
        buffer.put(fechaDeNacimiento.getBytes());
        buffer.put("Llave:".getBytes());
        buffer.put(len);
        buffer.put(this.llave.getEncoded());
        buffer.put("Certificador:".getBytes());
        buffer.put(certificador.getBytes());
        int tam = buffer.position();
        byte []bytesCertificado = new byte[tam];
        buffer.flip();
        buffer.get(bytesCertificado);
        
        try {
            MessageDigest md;
            CifradorRSA cifrador = new CifradorRSA();
            md = MessageDigest.getInstance("SHA-256");
            byte[] mdbytes = md.digest(bytesCertificado);
            byte []bytesFirma = cifrador.encrypt(mdbytes, llave);
            File cer = new File(System.getProperty("user.dir") + "/src/certificados/" + this.id + ".cer");
            FileOutputStream fos = new FileOutputStream(cer);
            fos.write(bytesCertificado);
            fos.write(bytesFirma);
            fos.close();
        } catch (NoSuchAlgorithmException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getId(){
        return this.id;
    }
}
