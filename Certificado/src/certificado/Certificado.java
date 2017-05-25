/*
 * To change this license header, choose License Headers in Project Properties.
http://www.java2s.com/Tutorial/Java/0490__Security/CreatingaCertificateinJava.htm
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package certificado;
     
import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.Arrays;
import javax.crypto.Cipher;

public class Certificado {

    public static void main(String args[]) {
            Certificado certificado = new Certificado();
            try {
                    String path = System.getProperty("user.dir") + "/src/llaves";

                    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

                    keyGen.initialize(2048);
                    
                    /* Generacion de la llave del banco */
                    KeyPair generatedKeyPair = keyGen.genKeyPair();

                    /*Imprimir y guardar las llaves */
                    System.out.println("Generated Key Pair");
                    certificado.dumpKeyPair(generatedKeyPair);
                    certificado.SaveKeyPair(path, generatedKeyPair);

                    /* Leer las llaves desde los arvhivos */
                    KeyPair loadedKeyPair = certificado.LoadKeyPair(path, "RSA");
                    System.out.println("Loaded Key Pair");
                    certificado.dumpKeyPair(loadedKeyPair);
                    
                    /* Ejemplo de cifrar y descifrar con rsa */
                    String textoClaro = "Este es el mensaje original";
                    byte [] cifrado = certificado.encrypt(textoClaro, loadedKeyPair.getPublic());
                    byte []recuperado = certificado.decrypt(cifrado, loadedKeyPair.getPrivate());
                    System.out.println("Texto claro: " + textoClaro);
                    System.out.println("Texto recuperado: " + new String(recuperado));
                    
                /* Generacion del certificado */
                    
                    // Datos del certificado
                    String idCliente = "1001";
                    String nombreCliente = "Raúl Martinez Galicia";
                    String fechaNacimiento = "10/04/1984";
                    //String llavePublica = "llave publica";
                    String gerente = "Ignacio Suarez Hernandez";
                    String firma = "la firma";
                    
                    /* Generacion de las llaves del cliente */
                    KeyPair llavesDelCliente = keyGen.genKeyPair();
                    certificado.guardarLlave(System.getProperty("user.dir") +   // Guardar lave privada
                                            "/src/certificados/" + idCliente + 
                                            ".key", llavesDelCliente.getPrivate());  
                    
                    /* Generacion de firma digital del gerente */
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] mdbytes = md.digest(gerente.getBytes());
                    byte []bytesFirma = certificado.encrypt(mdbytes, loadedKeyPair.getPrivate());
                    
                    /* Guardar certificado */
                    File cer = new File(System.getProperty("user.dir") + "/src/certificados/" + idCliente + ".cer");
                    FileOutputStream fos = new FileOutputStream(cer);
                    fos.write(("Id:" + idCliente + "\n").getBytes());
                    fos.write(("Cliente:" + nombreCliente + "\n").getBytes());
                    fos.write(("Fecha de nacimiento:" + fechaNacimiento + "\n").getBytes());
                    fos.write("Llave publica:".getBytes());
                    fos.write(llavesDelCliente.getPublic().getEncoded());
                    fos.write(("Gerente:" + gerente + "\n").getBytes());
                    fos.write("Firma:".getBytes());
                    fos.write(bytesFirma);
                    fos.close();
                    
                /* Verificacion de la autenticidad del certificado */
                    byte []firmaDec = certificado.decrypt(bytesFirma, loadedKeyPair.getPublic());
                    MessageDigest hash = MessageDigest.getInstance("SHA-256");
                    byte[] bytesGerente = hash.digest(gerente.getBytes());
                    System.out.println("Firma " + (Arrays.equals(firmaDec, bytesGerente) ? "valida" : "invalida"));
            } catch (Exception e) {
                    e.printStackTrace();
                    return;
            }
    }

    private void dumpKeyPair(KeyPair keyPair) {
            PublicKey pub = keyPair.getPublic();
            System.out.println("Public Key: " + getHexString(pub.getEncoded()));

            PrivateKey priv = keyPair.getPrivate();
            System.out.println("Private Key: " + getHexString(priv.getEncoded()));
    }

    private String getHexString(byte[] b) {
            String result = "";
            for (int i = 0; i < b.length; i++) {
                    result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
            }
            return result;
    }

    public void SaveKeyPair(String path, KeyPair keyPair) throws IOException {
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Store Public Key.
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
                            publicKey.getEncoded());
            FileOutputStream fos = new FileOutputStream(path + "/public.key");
            fos.write(x509EncodedKeySpec.getEncoded());
            fos.close();

            // Store Private Key.
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                            privateKey.getEncoded());
            fos = new FileOutputStream(path + "/private.key");
            fos.write(pkcs8EncodedKeySpec.getEncoded());
            fos.close();
    }

    public KeyPair LoadKeyPair(String path, String algorithm)
                    throws IOException, NoSuchAlgorithmException,
                    InvalidKeySpecException {
            // Read Public Key.
            File filePublicKey = new File(path + "/public.key");
            FileInputStream fis = new FileInputStream(path + "/public.key");
            byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
            fis.read(encodedPublicKey);
            fis.close();

            // Read Private Key.
            File filePrivateKey = new File(path + "/private.key");
            fis = new FileInputStream(path + "/private.key");
            byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
            fis.read(encodedPrivateKey);
            fis.close();

            // Generate KeyPair.
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                            encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                            encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            return new KeyPair(publicKey, privateKey);
    }
    
    public byte[] encrypt(String text, Key key) {
        byte[] cipherText = null;
        try {
          // get an RSA cipher object and print the provider
          final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
          // encrypt the plain text using the public key
          cipher.init(Cipher.ENCRYPT_MODE, key);
          cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
          e.printStackTrace();
          System.exit(0);
        }
        return cipherText;
    }
    
    public byte[] encrypt(byte []datos, Key key) {
        byte[] cipherText = null;
        try {
          // get an RSA cipher object and print the provider
          final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
          // encrypt the plain text using the public key
          cipher.init(Cipher.ENCRYPT_MODE, key);
          cipherText = cipher.doFinal(datos);
        } catch (Exception e) {
          e.printStackTrace();
          System.exit(0);
        }
        return cipherText;
    }

    public byte []decrypt(byte[] datos, Key key) {
        byte[] dectyptedText = null;
        try {
          // get an RSA cipher object and print the provider
          final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

          // decrypt the text using the private key
          cipher.init(Cipher.DECRYPT_MODE, key);
          dectyptedText = cipher.doFinal(datos);

        } catch (Exception ex) {
          ex.printStackTrace();
          System.exit(0);
        }

        return dectyptedText;
    }
  
  public void guardarLlave(String path, Key llave) throws IOException {

            FileOutputStream fos = new FileOutputStream(path);
            fos.write(llave.getEncoded());
            fos.close();
    }
}