package com.bankescomaester.controlador;

import com.bankescomaester.xml.ReadXML;
import com.bankescomaester.xml.WriteXML;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author isaac_stark
 */

public class Cuenta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String a, b, xmlr;
        float x, y, resultado;

        a = request.getParameter("a");
        b = request.getParameter("b");
        xmlr = request.getParameter("xml");

        System.out.println( "EN BANCUS   " + xmlr );
        
        if( xmlr != null ) {
            new ReadXML().cargarXml( xmlr );
        }
        
        try {
            x = Float.parseFloat(a);
            y = Float.parseFloat(b);
            resultado = x + y;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            out.println("Error al recibir parametros con GET");
            out.print(e);
            out.close();

            return;
        }

        /*out.println("<html>");
        out.println("<head><title>Un servlet básico</title></head>");
        out.println("<body>");
        out.println("<h1>Este es el servlet de BankESCOMaester</h1>");
        out.println("<h1>La suma de " + x + " + " + y + " = " + resultado + " </h1>");
        out.println("</body></html>");*/
        
        String xml = new WriteXML().crearXML();
        response.setContentType("text/xml");
        response.getWriter().println( xml );
                
        out.close();
        
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
