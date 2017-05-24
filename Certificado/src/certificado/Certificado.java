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
import javax.crypto.Cipher;

public class Certificado {

    public static void main(String args[]) {
            Certificado certificado = new Certificado();
            try {
                    String path = System.getProperty("user.dir") + "/src/llaves";

                    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

                    keyGen.initialize(2048);
                    KeyPair generatedKeyPair = keyGen.genKeyPair();

                    System.out.println("Generated Key Pair");
                    certificado.dumpKeyPair(generatedKeyPair);
                    certificado.SaveKeyPair(path, generatedKeyPair);

                    KeyPair loadedKeyPair = certificado.LoadKeyPair(path, "RSA");
                    System.out.println("Loaded Key Pair");
                    certificado.dumpKeyPair(loadedKeyPair);
                    
                    String textoClaro = "Este es el mensaje original";
                    byte [] cifrado = certificado.encrypt(textoClaro, loadedKeyPair.getPublic());
                    String recuperado = certificado.decrypt(cifrado, loadedKeyPair.getPrivate());
                    System.out.println("Texto claro: " + textoClaro);
                    System.out.println("Texto recuperado: " + recuperado);
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
    
    public byte[] encrypt(String text, PublicKey key) {
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

  public String decrypt(byte[] text, PrivateKey key) {
    byte[] dectyptedText = null;
    try {
      // get an RSA cipher object and print the provider
      final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

      // decrypt the text using the private key
      cipher.init(Cipher.DECRYPT_MODE, key);
      dectyptedText = cipher.doFinal(text);

    } catch (Exception ex) {
      ex.printStackTrace();
      System.exit(0);
    }

    return new String(dectyptedText);
  }
}