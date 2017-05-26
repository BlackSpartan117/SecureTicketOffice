/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package certificado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author edgar
 */
public class CifradorRSA {
    public enum TipoLlave {
        PRIVADA, PUBLICA
    }
    public KeyPair generarLlaves(int longitud){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(longitud);
            return keyGen.genKeyPair();
        }catch(NoSuchAlgorithmException nsae){
            nsae.printStackTrace();
            return null;
        }
    }
    
    public void guardarLlave(String path, Key llave, TipoLlave tipo){
        try{
            FileOutputStream fos;
            if(tipo == TipoLlave.PRIVADA){
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                                llave.getEncoded());
                fos = new FileOutputStream(System.getProperty("user.dir") + "/src/" + path);
                fos.write(pkcs8EncodedKeySpec.getEncoded());
                fos.close();
            }else{
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(llave.getEncoded());
                fos = new FileOutputStream(System.getProperty("user.dir") + "/src/" + path);
                fos.write(x509EncodedKeySpec.getEncoded());
                fos.close();
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public Key leerLlave(String path, TipoLlave tipo){
        try{
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            File fileKey = new File(System.getProperty("user.dir") + "/src/" + path);
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/" + path);
            byte[] encodedKey = new byte[(int) fileKey.length()];
            fis.read(encodedKey);
            fis.close();
            if(tipo == TipoLlave.PRIVADA){
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                            encodedKey);
                return keyFactory.generatePrivate(privateKeySpec);
            }else{
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedKey);
                return keyFactory.generatePublic(publicKeySpec);
            }
        }catch(IOException | NoSuchAlgorithmException | InvalidKeySpecException ioe){
            System.out.println("No existe el archivo " + path);
            ioe.printStackTrace();
            return null;
        }
    }
    
    public byte[] encrypt(byte []datos, Key key) {
        byte[] cipherText;
        try {
          final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
          cipher.init(Cipher.ENCRYPT_MODE, key);
          return cipher.doFinal(datos);
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
    }
    
    public byte []decrypt(byte[] datos, Key key) {
        try {
          final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
          cipher.init(Cipher.DECRYPT_MODE, key);
          return cipher.doFinal(datos);
        } catch (Exception ex) {
          ex.printStackTrace();
          return null;
        }
    }
}
