package controlador;

import entidades.Evento;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import negocio.ConexionMySQL;
import negocio.DAO;
import negocio.PropiedadConexion;
import xml.xmlcuenta;


/**
 *
 * @author jdagu e isaac_stark
 */
public class Ticket extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PropiedadConexion connProp;
    private ConexionMySQL conexionBD;
    private DAO consulta;
    private BigInteger primoDH;
    private BigInteger generadorDH;
    
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
        
        String peticion = this.descifrar(request);
        JsonReader jsonReader = Json.createReader(new StringReader(peticion));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        
        String accion = object.getString("accion");
        System.out.println( "ACCION  " + accion );
        
        if( "eventos".equals(accion) ) {
            response.setCharacterEncoding("UTF-8");
                /* funcion de prueba */ 
            //conectarConBanco(request, response);
            iniciarPagina( object, request, response );
            
        } else if( "comprar".equals(accion) ) {
            response.setCharacterEncoding("UTF-8");
            comprarBoleto( object, request, response );
            
        }
    }
    
    private String descifrar(HttpServletRequest request, byte []clave, String datosBase64){
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
    
    private String descifrar(HttpServletRequest request, byte []clave){
        String datos = request.getParameter("datos");
        return descifrar(request, clave, datos);
    }
    
    private String descifrar(HttpServletRequest request){
        HttpSession sesion= (HttpSession)request.getSession();
        byte []claveSesion = (byte [])sesion.getAttribute("clave");
        return descifrar(request, claveSesion);
    }
    
    private String cifrar(HttpServletRequest request, HttpServletResponse response, String datos, byte []clave){
        try{
            SecretKeySpec secret = new SecretKeySpec(clave, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte []ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte []cipherText = cipher.doFinal(datos.getBytes());
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 128);
            buffer.put(ivBytes);
            buffer.put(cipherText);
            int tam = buffer.position();
            byte []respuesta = new byte[tam];
            buffer.flip();
            buffer.get(respuesta);
            byte []respuestaBase64 = Base64.getEncoder().encode(respuesta);
            String resp = new String(respuestaBase64);
            System.out.println(resp);
            return resp;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    private String cifrar(HttpServletRequest request, HttpServletResponse response, String datos){
        HttpSession sesion= (HttpSession)request.getSession();
        byte []claveSesion = (byte [])sesion.getAttribute("clave");
        return cifrar(request, response, datos, claveSesion);
    }
    
/* Ejemplo extraido de http://www.theserverside.com/news/thread.tss?thread_id=21884
    ** http://programacionextrema.com/2015/11/26/realizar-una-peticion-post-en-java/*/
    private void conectarConBanco( HttpServletRequest request, HttpServletResponse response ) {
        try {
            URL url = new URL("http://localhost:8080/BankESCOMaester/CuentaBancaria");
            Map<String, Object> params = new LinkedHashMap<>();
            
            params.put("a", "10");
            params.put("b", "10");
            params.put("xml", new xmlcuenta().crearXML() );
            
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
            
            InputStream stream = conn.getInputStream();
            BufferedInputStream bin = new BufferedInputStream( stream );
            int i;
            
            while( ( i = bin.read() ) != -1 ){
                System.out.write( i );
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void iniciarPagina( JsonObject peticion, HttpServletRequest request, HttpServletResponse response ) {
        LinkedList<Evento> eventos;
        String tipo = peticion.getString("tipo");
        String tipoEvento = "";
        switch(tipo){
            case "C": tipoEvento = "Cine"; break;
            case "T": tipoEvento = "Teatro"; break;
            case "M": tipoEvento = "Concierto"; break;
        }
        try {
            eventos = consulta.obtenerEventos(tipoEvento);
            
            if( eventos != null ) {
                System.out.println("SIZE event " + eventos.size() );
                String json = crearJSON( eventos ).toString();
                System.out.println(json);
                String respuesta = this.cifrar(request, response, json);
                response.getWriter().print(respuesta);
                
            } else {
                response.getWriter().print( Json.createObjectBuilder().add( "Error", "error").build() );
            }
        } catch ( IOException ex ) {
            Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void comprarBoleto( JsonObject peticion, HttpServletRequest request, HttpServletResponse response ) {
        JsonObject obj      = peticion.getJsonObject("datos");
        String claveBase64  = obj.getString("clave");
        JsonObject evento   = obj.getJsonObject("evento");
        String tarjeta      = obj.getString("tarjeta");
        
        System.out.println("Clave: " + claveBase64);
        System.out.println("Evento: " + evento);
        System.out.println("Tarjeta: " + tarjeta);
       
        try{
            /* Este bloque para verificar que el cifrado AES sobre la tarejta
            funciones correctamente. Este descifrado lo debe hacer el banco.
            Aqui puede realizarse porque aun falta el cifrado RSA de la clave temporal
            */
            byte[] claveTemporal = Base64.getDecoder().decode(claveBase64.getBytes("UTF-8"));
            String jsonTarjeta  = descifrar(request, claveTemporal, tarjeta);
            System.out.println("Tarjeta descifrada: " + jsonTarjeta);
            PrintWriter out = response.getWriter();
            out.print("OK");
            String xml= new xmlcuenta().crearXML() ;
            String cuenta = this.cifrar(request, response, xml);
            conectarConBanco(request, response);
            //System.out.println(cuenta);
        }catch(Exception ioe){
            ioe.printStackTrace();
        }
    }
    
    private JsonArray crearJSON( LinkedList<Evento> eventos ) {
        JsonArray value;
        JsonArrayBuilder values = Json.createArrayBuilder();

        eventos.forEach((e) -> {
            values.add(Json.createObjectBuilder()
                    .add("id", Integer.toString( e.getId() ) )
                    .add("titulo", e.getNombre() )
                    .add("Tipo", e.getTipo() )
                    .add("precio", Double.toString( e.getPrecio() ) )
                    .add("lugares", Integer.toString( e.getAsientos() ) )
                    //.add("Fecha", e.getFecha().toString() )
                    .add("descripcion", e.getFecha().toString() )
                    .add("imagen", new String( e.getFoto() ) )
                    .add("lugar", e.getLugar() ) );
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
