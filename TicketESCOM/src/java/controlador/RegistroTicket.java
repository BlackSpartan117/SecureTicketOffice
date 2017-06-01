package controlador;

import entidades.Evento;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import negocio.ConexionMySQL;
import negocio.DAO;
import negocio.PropiedadConexion;

/**
 *
 * @author isaac_stark
 */
@WebServlet("/Registro")
@MultipartConfig
public class RegistroTicket extends HttpServlet {
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

        String name = request.getParameter("nombre");
        String tipo = request.getParameter("tipo");
        String precio = request.getParameter("precio");
        String asientos = request.getParameter("asientos");
        String fecha = request.getParameter("fecha");
        Part foto = request.getPart("foto");
        String lugar = request.getParameter("lugar");
        String desc = request.getParameter("desc");

        System.out.println( name );
        System.out.println( tipo );
        System.out.println( precio );
        System.out.println( asientos );
        System.out.println( fecha );

        byte contenidoFoto[] = null;
        String img = "data:image/jpeg;base64,";

        if( foto != null ) {
            String nameFoto = Paths.get( foto.getSubmittedFileName() ).getFileName().toString();

            InputStream fileContent = foto.getInputStream();

            contenidoFoto = new byte[ fileContent.available() ];
            fileContent.read( contenidoFoto, 0, fileContent.available() );
            img += Base64.getEncoder().encodeToString( contenidoFoto );

            fileContent.close();
            System.out.println(nameFoto + "  " + contenidoFoto.length);
            contenidoFoto = img.getBytes();
        }

        System.out.println( lugar );
        System.out.println( desc );

        Calendar f = new GregorianCalendar();
        String fec[] = fecha.split("-");
        f.set( Integer.parseInt( fec[0] ), Integer.parseInt( fec[1] ) - 1, Integer.parseInt( fec[2] ) );

        Evento ev = new Evento(name, tipo, Double.parseDouble( precio ), Integer.parseInt( asientos ), f.getTime(), contenidoFoto, lugar, desc);
        consulta.insertar( ev, fecha );
        
        Cookie galletita = new Cookie("registro", "exitoso");
        galletita.setMaxAge( 4 );
        response.addCookie( galletita );
        response.sendRedirect("registro.html");
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