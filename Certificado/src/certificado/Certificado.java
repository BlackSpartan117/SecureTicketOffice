/*
 Para la generacion del certificado ver la clase JavaApplication1
 */

package certificado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Collections;

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
    private final String ID     = "Id:";
    private final String NOMBRE = "Nombre:";
    private final String FECHA_DE_NACIMIENTO = "Fecha de nacimiento:";
    private final String LLAVE = "Llave:";
    private final String CERTIFICADOR = "Certificador:";
    private final String FIRMA = "Firma:";
    
    public Certificado(String id, String nombre, String fecha, PublicKey llave, String certificador){
        this.id                = id;
        this.nombre            = nombre;
        this.fechaDeNacimiento = fecha;
        this.llave             = llave;
        this.certificador      = certificador;
    }
    
    public Certificado(){
    }
    
    public void firmar(PrivateKey llave){
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(this.llave.getEncoded());
        byte []llavePublica = x509EncodedKeySpec.getEncoded();
        /*byte []len = new byte[2];
        len[0] = (byte)(llavePublica.length/256);
        len[1] = (byte)(llavePublica.length%256);*/
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);
        buffer.put(ID.getBytes());
        buffer.put(id.getBytes());
        buffer.put(NOMBRE.getBytes());
        buffer.put(nombre.getBytes());
        buffer.put(FECHA_DE_NACIMIENTO.getBytes());
        buffer.put(fechaDeNacimiento.getBytes());
        buffer.put(LLAVE.getBytes());
        //buffer.put(len);
        buffer.put(llavePublica);
        buffer.put(CERTIFICADOR.getBytes());
        buffer.put(certificador.getBytes());
        buffer.put(FIRMA.getBytes());
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
            System.out.println(bytesFirma.length);
            for (int i = 0; i < bytesFirma.length; i++) {
                System.out.print(bytesFirma[i] + ":");
            }
            fos.close();
        } catch (NoSuchAlgorithmException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public String getId(){
        return this.id;
    }
    
    public void leerCertificado(String path, PublicKey llave){
        try{
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/" + path);
            File f = new File(System.getProperty("user.dir") + "/src/" + path);
            Byte []bytesCertificado;
            byte []bytesCert = new byte[(int)f.length()];
            byte []bytesLlave, bytesFirma;
            int len, index[];
            CifradorRSA crsa = new CifradorRSA();
            
            index = new int[6];
            len = fis.read(bytesCert);
            bytesCertificado = byteArrayToObject(bytesCert);
            index[0] = findArray(bytesCertificado, byteArrayToObject(ID.getBytes()));
            index[1] = findArray(bytesCertificado, byteArrayToObject(NOMBRE.getBytes()));
            index[2] = findArray(bytesCertificado, byteArrayToObject(FECHA_DE_NACIMIENTO.getBytes()));
            index[3] = findArray(bytesCertificado, byteArrayToObject(LLAVE.getBytes()));
            index[4] = findArray(bytesCertificado, byteArrayToObject(CERTIFICADOR.getBytes()));
            index[5] = findArray(bytesCertificado, byteArrayToObject(FIRMA.getBytes()));
            this.id     = new String(Arrays.copyOfRange(bytesCert, index[0] + ID.length(), index[1]));
            this.nombre = new String(Arrays.copyOfRange(bytesCert, index[1] + NOMBRE.length(), index[2]));
            this.fechaDeNacimiento = new String(Arrays.copyOfRange(bytesCert, index[2] + FECHA_DE_NACIMIENTO.length(), index[3]));
            bytesLlave = Arrays.copyOfRange(bytesCert, index[3] + LLAVE.length(), index[4]);
            this.certificador = new String(Arrays.copyOfRange(bytesCert, index[4] + CERTIFICADOR.length(), index[5]));
            this.firma = Arrays.copyOfRange(bytesCert, index[5] + FIRMA.length(), bytesCert.length);
            System.out.println(this.firma.length);
            for (int i = 0; i < this.firma.length; i++) {
                System.out.print(this.firma[i] + ":");
            }
            byte []firmaDec = crsa.decrypt(this.firma, llave);
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            byte[] bytesGerente = hash.digest(Arrays.copyOfRange(bytesCertificado, index[0], index[5] + FIRMA.length()));
            System.out.println("Firma " + (Arrays.equals(firmaDec, bytesGerente) ? "valida" : "invalida"));
        }catch(IOException | NoSuchAlgorithmException ioe){
            ioe.printStackTrace();
        }
    }
    public String toString(){
        return "id: " + this.id + "\nnombre: " + this.nombre + 
                "\nFecha de nacimiento: " + this.fechaDeNacimiento +
                "\nCertificador: " + this.certificador;
    }
    
    private static int findArray(Byte[] array, Byte[] subArray){
        return Collections.indexOfSubList(Arrays.asList(array), Arrays.asList(subArray));
    }
    
    private Byte []byteArrayToObject(byte []arreglo){
        int i = 0;
        Byte []arregloByte = new Byte[arreglo.length];
        for(byte b : arreglo){
            arregloByte[i++] = b;
        }
        return arregloByte;
    }
}
