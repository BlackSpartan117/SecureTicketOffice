package controlador;

import entidades.Cuenta;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author isaac_stark
 */
public class Login extends HttpServlet {
    private String ruta;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        ruta = config.getServletContext().getRealPath("/WEB-INF/user.json");
        System.out.println("la ruta es: " + ruta);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("nameUser");
        String pass = request.getParameter("pass");
        String logout = request.getParameter("logout");
        
        System.out.println("USERNAME_REGISTRO " + username + "  " + pass + "  " + logout );
        
        if( username != null && pass != null ) {
            System.out.println("opcion 1");
            InputStream fis = new FileInputStream( ruta ); /*Abrimos el Json*/
            JsonReader jsonReader = Json.createReader( fis ); /*Creamos unJsonReader*/
            JsonObject jsonObject = jsonReader.readObject(); /*Obtenemos el objeto Json*/
            
            if( username.equals( jsonObject.getString("email") ) && pass.equals( jsonObject.getString("pass") ) ) {
                HttpSession misession= request.getSession( true );
                Cuenta cuenta = new Cuenta( username, pass );
                
                misession.setAttribute("cuenta", cuenta );
                response.sendRedirect("registro.html");
            } else {
                response.sendRedirect("login.html");
            }
            
        } else if( logout != null ) {
            System.out.println("opcion 2");
            HttpSession misession= request.getSession( false );
            
            if( misession != null )
                misession.invalidate();
            
            response.sendRedirect("index.html");
            
        } else {
            System.out.println("opcion 3");
            response.sendRedirect("login.html");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
