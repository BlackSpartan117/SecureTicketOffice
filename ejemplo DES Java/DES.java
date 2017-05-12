import java.security.*;

import javax.crypto.*;
import javax.crypto.interfaces.*;
import javax.crypto.spec.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;

/* para  compilar usar: javac -cp ".:bcprov-jdk16-1.45.jar" DES.java */
/* ver pagina ára doc http://docs.oracle.com/javase/1.5.0/docs/guide/security/CryptoSpec.html#SimpleEncrEx */

public class DES {
    /*  Ejemplo de uso de funciones de resumen Hash
     *  carga el fichero que recibe como parametro y genera el resumen
     */
    public static void main(String[] args) throws Exception {
		
		if( args.length != 1 ) {
			mensajeAyuda();
			System.exit( -1 );
		}
        
        String texto_plano = args[0];
        /* Cargar "provider" (sólo si no se usa el que viene por defecto) */
        Security.addProvider(new BouncyCastleProvider());  // Usa provider BC

		KeyGenerator keygen = KeyGenerator.getInstance("DESede");
		SecretKey desKey = keygen.generateKey();

		Cipher des;
		// Create the cipher 
		des = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		
		// Initialize the cipher for encryption
	    des.init( Cipher.ENCRYPT_MODE, desKey );
	    
		// Our cleartext
		byte[] cleartext = texto_plano.getBytes();
		
		// Encrypt the cleartext
		byte[] ciphertext = des.doFinal( cleartext );
		
		// Initialize the same cipher for decryption
		des.init( Cipher.DECRYPT_MODE, desKey );
		
		// Decrypt the ciphertext
		byte[] cleartext1 = des.doFinal( ciphertext );
		
		System.out.print("Texto:\t");
		mostrarBytes( cleartext );
		System.out.print("cifrado:\t");
		mostrarBytes( ciphertext );
		System.out.print("descifrado:\t");
		mostrarBytes( cleartext1 );
    }

    public static void mostrarBytes(byte [] buffer) {
        System.out.write( buffer, 0, buffer.length );
        System.out.println();
    } 
    
    public static void mensajeAyuda() {
        System.out.println("Ejemplo DES");
        System.out.println("\tSintaxis:   java DES texto");
        System.out.println();
    }

}

