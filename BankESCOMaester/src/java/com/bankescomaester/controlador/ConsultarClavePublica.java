/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bankescomaester.controlador;

import banco.util.CifradorRSA;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edgar
 */
public class ConsultarClavePublica extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if("publicKey".equals(accion)){
            devolverClavePublica(request, response);
        }else if("descifrar".equals(accion)){
            descifrarBase64(request, response);
        }
    }
    
    private void devolverClavePublica(HttpServletRequest request, HttpServletResponse response){
        CifradorRSA cifrador = new CifradorRSA(this);
        /* Leer llaves del banco*/
        PrivateKey clavePrivadaBanco = (PrivateKey) cifrador.leerLlave("private.key", CifradorRSA.TipoLlave.PRIVADA);
        PublicKey clavePublicaBanco = (PublicKey) cifrador.leerLlave("public.key", CifradorRSA.TipoLlave.PUBLICA);
        
        byte []clavePublicaBase64 = Base64.getEncoder().encode(clavePublicaBanco.getEncoded());
        String clavePublica = new String(clavePublicaBase64);
        try (PrintWriter out = response.getWriter()) {
            out.print(clavePublica);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    private void descifrarBase64(HttpServletRequest request, HttpServletResponse response){
        CifradorRSA cifrador = new CifradorRSA(this);
        /* Leer llaves del banco*/
        PrivateKey clavePrivadaBanco = (PrivateKey) cifrador.leerLlave("private.key", CifradorRSA.TipoLlave.PRIVADA);
        PublicKey clavePublicaBanco = (PublicKey) cifrador.leerLlave("public.key", CifradorRSA.TipoLlave.PUBLICA);
        
        String textoCifradoBase64 = request.getParameter("datos");
        String respuesta = cifrador.decrypt(textoCifradoBase64, clavePrivadaBanco);
        System.out.println(respuesta);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.print(respuesta);
        }catch(IOException ioe){
            ioe.printStackTrace();
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
