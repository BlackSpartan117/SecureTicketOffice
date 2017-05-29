package xml;

/**
 *
 * @author isaac_stark
 */
import java.io.*;
import java.net.*;

public class ConectarServidor {

    public void conectar() {
        Socket mi_IP;
        PrintWriter enviarAServidor;
        DataInputStream recibirDelServidor;
        String tecleado;

        try {
            /* direccion IP del servidor de Ticket y puerto en el que escucha 
            ** el servidor bancario */
            mi_IP = new Socket("127.0.0.1", 5000 );
            
            System.out.println("Conectado con el servidor Bancario");

            enviarAServidor = new PrintWriter( mi_IP.getOutputStream(), true );
            recibirDelServidor = new DataInputStream( mi_IP.getInputStream() );
            /* Se crea el archivo xml */
            WriteXML xml = new WriteXML();
            
            /* Se envia el archivo xml bien formado al servidor bancario*/
            enviarAServidor.println( xml.crearXML() );
            /* Recibe, por ahora, un entero del servidor */
            System.out.println("Longitud = " + recibirDelServidor.readInt() );

            System.out.println("Transaccion terminada!");

            recibirDelServidor.close();
            enviarAServidor.close();
            mi_IP.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}