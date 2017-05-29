package controlador;

import entidades.Evento;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.ConexionMySQL;
import negocio.DAO;
import negocio.PropiedadConexion;
import xml.ConectarServidor;
import xml.WriteXML;

/**
 *
 * @author jdagu e isaac_stark
 */
public class Ticket extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PropiedadConexion connProp;
    private ConexionMySQL conexionBD;
    private DAO consulta;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        String ruta = config.getServletContext().getRealPath("/conf/config.properties");
        System.out.println("la ruta es: " + ruta);
        connProp = new PropiedadConexion(ruta); // poner ruta
        conexionBD = new ConexionMySQL( connProp );
        conexionBD.getConexion();
        consulta = new DAO( conexionBD.getConn() );
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String opcion = request.getParameter("iniciar");
        System.out.println( "OPCION  " + opcion );
        
        request.setCharacterEncoding("UTF-8");
        if( opcion != null && opcion.equals("iniciaPagina") ) {
            WriteXML xml = new WriteXML();
            ConectarServidor cs = new ConectarServidor();
            cs.conectar();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            iniciarPagina( request, response );
            
        } else {
            response.getWriter().print("Error");
        }
    }
    
    private void iniciarPagina( HttpServletRequest request, HttpServletResponse response ) {
        LinkedList<Evento> eventos;
        
        try {
            eventos = consulta.obtenerEventos();
            
            if( eventos != null ) {
                System.out.println("SIZE event " + eventos.size() );
                response.getWriter().print( crearJSON( eventos ) );
                
            } else {
                response.getWriter().print( Json.createObjectBuilder().add( "Error", "error").build() );
            }
        } catch ( IOException ex ) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private JsonArray crearJSON( LinkedList<Evento> eventos ) {
        JsonArray value;
        JsonArrayBuilder values = Json.createArrayBuilder();

        eventos.forEach((e) -> {
            values.add(Json.createObjectBuilder()
                    .add("Id", Integer.toString( e.getId() ) )
                    .add("Nombre", e.getNombre() )
                    .add("Tipo", e.getTipo() )
                    .add("Precio", Double.toString( e.getPrecio() ) )
                    .add("Asientos", Integer.toString( e.getAsientos() ) )
                    .add("Fecha", e.getFecha().toString() )
                    .add("Foto", new String( e.getFoto() ) )
                    .add("Lugar", e.getLugar() ) );
        });

        value = values.build();

        return value;
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
    }// </editor-fold>

}
