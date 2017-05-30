package controlador;

import entidades.Evento;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.DHParameterSpec;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.ConexionMySQL;
import negocio.DAO;
import negocio.PropiedadConexion;
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
        String accion = request.getParameter("accion");
        System.out.println( "OPCION  " + opcion );
        
        request.setCharacterEncoding("UTF-8");
        if( opcion != null && opcion.equals("iniciaPagina") ) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            System.out.println("CONECTAR BANCO\n");
            conectarConBanco( request, response );
            //iniciarPagina( request, response );
            
        } 
        else if (accion != null && accion.equals("parametros")){
             DiffieHellman(request,response);
        }else {
            response.getWriter().print("Error");
        }
    }
    
    
    private void DiffieHellman(HttpServletRequest reques,HttpServletResponse response) throws NoSuchAlgorithmException{
     
          
        try {
            AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
    paramGen.init(1024);

    AlgorithmParameters params = paramGen.generateParameters();
    DHParameterSpec dhSpec;
    dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);
            
    System.out.println("" + dhSpec.getP() + "\n" + dhSpec.getG() + "\n" + dhSpec.getL());
    
    JsonObjectBuilder respuesta = Json.createObjectBuilder();
    
    respuesta.add("primo", dhSpec.getP());
    respuesta.add("generador",dhSpec.getG());
    respuesta.add("longitud",dhSpec.getL());
    
    JsonObject o = respuesta.build();
    
    PrintWriter out = response.getWriter();
    System.out.println(o);
    out.print(o);
    
        } catch (InvalidParameterSpecException | IOException | NoSuchAlgorithmException ex) {
            
        }

   
    
    }
    /* Ejemplo extraido de http://www.theserverside.com/news/thread.tss?thread_id=21884
    ** http://programacionextrema.com/2015/11/26/realizar-una-peticion-post-en-java/*/
    private void conectarConBanco( HttpServletRequest request, HttpServletResponse response ) {
        try {
            URL url = new URL("http://localhost:8080/BankESCOMaester/CuentaBancaria");
            Map<String, Object> params = new LinkedHashMap<>();
            
            params.put("a", "10");
            params.put("b", "10");
            
            StringBuilder postData = new StringBuilder();
            
            for( Map.Entry<String, Object> param : params.entrySet() ) {
                if( postData.length() != 0 )
                    postData.append("&");
                postData.append( URLEncoder.encode( param.getKey(), "UTF-8" ) );
                postData.append("=");
                postData.append( URLEncoder.encode( String.valueOf( param.getValue() ), "UTF-8" ) );
            }
            
            byte []postDataBytes = postData.toString().getBytes("UTF-8");
            
            HttpURLConnection conn = ( HttpURLConnection ) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf( postDataBytes.length ) );
            conn.setDoOutput(true);
            conn.getOutputStream().write( postDataBytes );
            
            /*Reader in = new BufferedReader( new InputStreamReader( conn.getInputStream(), "UTF-8" ) );
            
            for( int c = in.read(); c != -1; c = in.read() ) {
                System.out.println( (char)c );
            }*/
            
            InputStream stream = conn.getInputStream();
            BufferedInputStream bin = new BufferedInputStream( stream );
            long time = System.currentTimeMillis();
            int i = 0;
            
            while( ( i = bin.read() ) != -1 ){
                System.out.write( i );
            }
            
            /*
            String direccionUrl = "http://localhost:8080/ServletGetPost/ServletBasico";
            URL url = new URL( direccionUrl );
            HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput( true );
            OutputStreamWriter ostream = new OutputStreamWriter( connection.getOutputStream() );
            String buffer = "?a=3.0&b=23.3";
            if( buffer != "" ) {
                BufferedWriter out = new BufferedWriter( ostream );
                //out.write( bufer );
                out.flush();
                out.close();
            }
            
            InputStream stream = connection.getInputStream();
            BufferedInputStream bin = new BufferedInputStream( stream );
            long time = System.currentTimeMillis();
            int i = 0;
            
            while( ( i = bin.read() ) != -1 ){
                System.out.write( i );
            }
            bin.close();
            connection.disconnect();*/
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
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
