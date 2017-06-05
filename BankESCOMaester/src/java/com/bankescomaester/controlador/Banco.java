package com.bankescomaester.controlador;

import banco.util.CifradorRSA;
import com.bankescomaester.dao.BankDAO;
import com.bankescomaester.entities.Cuenta;
import com.bankescomaester.entities.ReciboPago;
import com.bankescomaester.xml.ReadXML;
import com.bankescomaester.xml.WriteXML;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 *
 * @author isaac_stark
 */

public class Banco extends HttpServlet {

    private String ruta, rutaImgIPN, imgESCOM, pdfs, nombreArchivo = "Ticket", rutaKey;

    @Override
    public void init( ServletConfig config ) throws ServletException {
        ruta = config.getServletContext().getRealPath("/conf/ticket.jasper");
        pdfs = config.getServletContext().getRealPath("/conf/pdf");
        rutaKey = config.getServletContext().getRealPath("/WEB-INF/");
        rutaImgIPN = config.getServletContext().getRealPath("/images/ipn.png");
        imgESCOM = config.getServletContext().getRealPath("/images/escom.png");
        System.out.println("la rutaKey es: " + rutaKey);
        System.out.println("la ruta es: " + pdfs);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String xmlr, jsonCliente = null;
        ReadXML xmlCifrado = new ReadXML();
        
        xmlr = request.getParameter("xml");
        System.out.println( "EN BANCUS   " + xmlr );
        
        if( xmlr != null ) {
            CifradorRSA rsa = new CifradorRSA();
            
            rsa.setDirectorio( rutaKey );
            xmlCifrado.cargarXml( xmlr );
            Key llavePrivada =  rsa.leerLlave( "private.key", CifradorRSA.TipoLlave.PRIVADA );
            
            String claveTemporal = rsa.decrypt(xmlCifrado.getClave(), llavePrivada);
            byte[] claveDescifrada = Base64.getDecoder().decode( claveTemporal.getBytes("UTF-8") );
            jsonCliente = decifrar( claveDescifrada, xmlCifrado.getTarjetaCliente() );
            System.out.println("CLIENTE " + jsonCliente );
        }
        
        Cuenta cliente = new Cuenta();
        if( jsonCliente != null ) {
            JsonReader jsonReader = Json.createReader( new StringReader( jsonCliente ) );
            JsonObject object = jsonReader.readObject();
            
            cliente.setNoTarjetaCredito( object.getString("tarjeta") );
            cliente.setNombre( object.getString("titular").split(" ")[0] );
            cliente.setApellido( object.getString("titular").substring( object.getString("titular").indexOf(" "), object.getString("titular").length() )  );
            Calendar fec = new GregorianCalendar();
            fec.set( Integer.parseInt( object.getString("anio") ), Integer.parseInt( object.getString("mes") ) - 1, 4 );
            cliente.setVigencia( fec.getTime() );
            cliente.setCvv( object.getString("cvv") );
            System.out.println("APE " + cliente.getNoTarjetaCredito() + " " + cliente.getApellido() + " " + cliente.getCvv() );
            //{"tarjeta":"1234567890123459","titular":"Omar Rodriguez Lopez","mes":"07","anio":"2022","cvv":"670"}
        }
        
        BankDAO dao = new BankDAO();
        if( dao.existeCuenta( cliente ) ) {
            cliente = dao.getCuentaById( cliente.getNoTarjetaCredito() );
            Cuenta ticket = dao.getCuentaById( xmlCifrado.getTarjetaTicket() );
            
            if( realizarTransaccionBancaria( cliente, ticket, Double.parseDouble( xmlCifrado.getMonto() ) ) ) {
                LinkedList<ReciboPago> rp = new LinkedList<>();
                nombreArchivo = cliente.getNombre();
                
                rp.add( new ReciboPago( "001927", ticket.getNombre(), cliente.getNombre(), cliente.getId().toString(), xmlCifrado.getMonto() ) );
                crearReciboDePago( rp );
            } else {
                response.getWriter().print("Saldo insuficiente");
                return;
            }
            
        } else {
            response.getWriter().print("Cuenta no existente");
            return;
        }
        
        String xml = new WriteXML().crearXML();
        response.setContentType("text/xml");
        response.getWriter().println( xml );
        
    }
    
    private String decifrar(byte []clave, String datosBase64){
        try{
            byte[] decodedString = Base64.getDecoder().decode(datosBase64.getBytes("UTF-8"));
            
            byte []ivBytes = Arrays.copyOfRange(decodedString, 0, 16);
            byte []cipherText = Arrays.copyOfRange(decodedString, 16, decodedString.length);
            
            SecretKeySpec secret = new SecretKeySpec(clave, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
            
            byte[] decryptedTextBytes = null;
            decryptedTextBytes = cipher.doFinal(cipherText);

            return new String(decryptedTextBytes);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean realizarTransaccionBancaria( Cuenta cliente, Cuenta ticket, double monto ) {
        boolean transaccionCorrecta = false;
        BankDAO dao = new BankDAO();
        
        if( cliente == null || ticket == null )
            return transaccionCorrecta;
        
        if( cliente.getSaldo() > monto ) {
            /* Tiene saldo suficiente. Actualizamos su saldo descontando 
            ** el monto equivalente al pago del o los eventos solicitados. */
            cliente.setSaldo( cliente.getSaldo() - monto );
            ticket.setSaldo( ticket.getSaldo() + monto );
            
            dao.updateCuenta( cliente );
            dao.updateCuenta( ticket );

            System.out.println( "USER" + cliente.getApellido() + " " + cliente.getSaldo() );
            transaccionCorrecta = true;
            
        } else {
            transaccionCorrecta = false;
        }
        
        return transaccionCorrecta;
    }
    
    private void crearReciboDePago( LinkedList<ReciboPago> ticket ) {
        try {
            /*Se indica la plantilla jasper */
            JasperReport reporte = ( JasperReport ) JRLoader.loadObject( new File( ruta ) );
            Map parametros = new HashMap();
            
            parametros.put("ipn", rutaImgIPN );
            parametros.put("escom", imgESCOM );
            
            JasperPrint jPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource( ticket ) );

            Exporter exporter = new JRPdfExporter();
            exporter.setExporterInput( new SimpleExporterInput( jPrint ) );
            exporter.setExporterOutput( new SimpleOutputStreamExporterOutput( pdfs + File.separator + nombreArchivo + ".pdf") );
            exporter.exportReport();

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
