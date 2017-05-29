/**
 *
 * @author isaac_stark
 */
import java.io.*;
import java.net.*;

public class ServidorBancario {

    public static void main( String[] args ) {
        ServerSocket IP_Server;
        Socket IP_Cliente;
        int puerto = 5000;
        boolean escuchando = true;

        try {
            IP_Server = new ServerSocket( puerto );
            System.out.println("Servidor Bancario escuchando en puerto " + puerto );

            while (escuchando) {
                IP_Cliente = IP_Server.accept();
                System.out.println("Peticion de cliente desde: "
                        + IP_Cliente.getInetAddress().getHostName()
                        + "(" + IP_Cliente.getPort() + ")");
                new Atiende(IP_Cliente).start();
            }
            
            IP_Server.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}

class Atiende extends Thread {

    private BufferedReader deCliente;
    private DataOutputStream paraCliente;
    private String mensajeRecibido;
    private Socket cliente = null;
    private String nombreyDirIP;

    public Atiende( Socket cliente ) {
        this.cliente = cliente;
        nombreyDirIP = this.cliente.getInetAddress().toString();
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().setPriority( Thread.NORM_PRIORITY );
            deCliente = new BufferedReader( new InputStreamReader( cliente.getInputStream() ) );
            paraCliente = new DataOutputStream( cliente.getOutputStream() );
            ReadXML readXML = new ReadXML();
            String xml = "";
            
            do {
                mensajeRecibido = deCliente.readLine();
                System.out.println("(" + nombreyDirIP + ") Llego: " + mensajeRecibido);
                xml += mensajeRecibido;
            } while( mensajeRecibido.length() != 0 );

            paraCliente.writeInt( xml.length() );
            readXML.cargarXml( xml );
            deCliente.close();
            cliente.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        
        System.out.println("Peticion finalizada." + "(" + nombreyDirIP + ")");
    }
}
