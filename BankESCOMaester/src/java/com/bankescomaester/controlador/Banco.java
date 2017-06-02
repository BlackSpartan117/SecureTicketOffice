package com.bankescomaester.controlador;

import com.bankescomaester.dao.BankDAO;
import com.bankescomaester.entities.Cuenta;
import com.bankescomaester.entities.ReciboPago;
import com.bankescomaester.xml.ReadXML;
import com.bankescomaester.xml.WriteXML;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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

    private String ruta, rutaImgIPN, imgESCOM, pdfs, nombreArchivo = "Ticket";

    @Override
    public void init( ServletConfig config ) throws ServletException {
        ruta = config.getServletContext().getRealPath("/conf/ticket.jasper");
        pdfs = config.getServletContext().getRealPath("/conf/pdf");
        rutaImgIPN = config.getServletContext().getRealPath("/images/ipn.png");
        imgESCOM = config.getServletContext().getRealPath("/images/escom.png");
        System.out.println("la ruta es: " + ruta);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        String xmlr;
        LinkedList<Cuenta> cuentas = null;
        xmlr = request.getParameter("xml");
        System.out.println( "EN BANCUS   " + xmlr );
        
        if( xmlr != null ) {
            cuentas = new ReadXML().cargarXml( xmlr );
        }
        
        System.out.println("SIZE CUENTAS " + cuentas.size() );
        if( realizarTransaccionBancaria( cuentas ) ) {
            LinkedList<ReciboPago> rp = new LinkedList<>();
            Cuenta cu = new BankDAO().getCuentaById( cuentas.get(1).getNoTarjetaCredito() ); 
            nombreArchivo = cuentas.get(0).getNombre();
            rp.add( new ReciboPago( "001927", cu.getNombre(), cuentas.get(0).getNombre(), cu.getId().toString(), Double.toString(cuentas.get(0).getSaldo() ) ) );        
            crearReciboDePago( rp );
        } else {
            
        }
        
        String xml = new WriteXML().crearXML();
        response.setContentType("text/xml");
        response.getWriter().println( xml );
        
    }
    
    public boolean realizarTransaccionBancaria( LinkedList<Cuenta> cuentasXML ) {
        boolean transaccionCorrecta = false;
        Cuenta cuentaUsuario;
        BankDAO dao = new BankDAO();
        
        for( Cuenta datosPeticion : cuentasXML ) {
            if( datosPeticion.getSaldo() > 1 ) { /* No es la cuenta de TicketsESCOM */
                /* Busca al usuario por su tarjeta de credito*/
                cuentaUsuario = dao.getCuentaById( datosPeticion.getNoTarjetaCredito() ); 
                
                /* Verificamos que el usuario tenga saldo suficiente para comprar el o
                ** los eventos que solicito */
                if( cuentaUsuario.getSaldo() > datosPeticion.getSaldo() ) {
                    /* Tiene saldo suficiente. Actualizamos su saldo descontando 
                    ** el monto equivalente al pago del o los eventos solicitados. */
                    double nuevoSaldo = cuentaUsuario.getSaldo() - datosPeticion.getSaldo();
                    double ganancia = datosPeticion.getSaldo();
                    cuentaUsuario.setSaldo( nuevoSaldo );
                    dao.updateCuenta( cuentaUsuario );
                    datosPeticion = dao.getCuentaById( cuentasXML.get(1).getNoTarjetaCredito() ); 
                    datosPeticion.setSaldo( ganancia + datosPeticion.getSaldo()  );
                    dao.updateCuenta( datosPeticion );
                    
                    System.out.println( "USER" + cuentaUsuario.getApellido() + " " + cuentaUsuario.getSaldo() );
                    transaccionCorrecta = true;
                } else {
                    transaccionCorrecta = false;
                }
            }
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
