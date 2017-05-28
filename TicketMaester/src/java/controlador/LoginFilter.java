package controlador;

/**
 *
 * @author isaac_stark
 */
/**
 * Ejemplo de filtro de servlet: Este ejemplo filtra j_security_check y realiza
 * acciones anteriores al inicio de sesión para determinar si el usuario que
 * está intentando iniciar la sesión está en la lista de revocados. Si el
 * usuario está en la lista de revocados, se envía un error al navegador.
 *
 * Este filtro lee el nombre de archivo de la lista de revocados en la
 * FilterConfig que se pasa en el método init(). Lee el archivo de la lista de
 * usuarios revocados y crea una lista revokedUsers.
 *
 * Cuando se llama al método doFilter, se comprueba el usuario que está
 * iniciando la sesión para confirmar que éste usuario no está en la lista de
 * usuarios revocados.
 *
 */
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LoginFilter implements Filter {

    protected FilterConfig filterConfig;
    java.util.List revokeList;
    private String ruta = "";

    /**
     * init() : se llama al método init() cuando se crea una instancia del
     * filtro. Se crea una instancia de este filtro la primera vez que se invoca
     * j_security_check para la aplicación (es decir, cuando se accede a un
     * servlet protegido de la aplicación).
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        ruta = filterConfig.getServletContext().getRealPath("/conf/user.txt");
        System.out.println("ruta " + ruta );
        // leer la lista de usuario revocados
        revokeList = new java.util.ArrayList();
        readConfig();
    }

    /**
     * destroy() : se llama al método destroy() cuando el filtro ya no está en
     * servicio.
     */
    @Override
    public void destroy() {
        this.filterConfig = null;
        revokeList = null;
    }

    /**
     * doFilter() : se llama al método doFilter() antes de que se invoque el
     * servlet con el que está correlacionado este filtro. Dado que este filtro
     * se correlaciona con j_security_check, se llama a este método antes de de
     * que se envíe la acción j_security_check.
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws java.io.IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // acción de inicio de sesión previa
        // obtener nombre de usuario 
        String username = req.getParameter("nameUser");
        String pass = req.getParameter("pass");
        System.out.println("USERNAME " + username + "  " + pass );
        
        // si el usuario está en la lista de revocados, enviar error
        if( revokeList.contains( username ) ) {
            res.sendError( javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED );
            return;
        }

        // llamar al siguiente filtro de la cadena: permitir que j_security_check autentique al 
        // usuario
        chain.doFilter( request, response );

        // acción de inicio de sesión posterior
    }

    /**
     * readConfig() : Lee el archivo de la lista de usuarios y crea una lista de
     * usuarios revocada.
     */
    private void readConfig() {
        if( filterConfig != null ) {

            // obtener el archivo de la lista de usuarios revocados y abrirlo.
            BufferedReader in;
            try {
                String filename = filterConfig.getInitParameter( ruta );
                in = new BufferedReader( new FileReader( filename ) );
            } catch( FileNotFoundException fnfe ) { 
                fnfe.printStackTrace();
                return;
            } 

            // leer todos los usuarios revocados y añadirlos a revokeList. 
            String userName;
            try {
                while( ( userName = in.readLine() ) != null ) {
                    System.out.println( userName );
                    revokeList.add( userName );
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
