/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Random;
import javax.crypto.spec.DHParameterSpec;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author edgar
 */
public class Handshake extends HttpServlet {
    
    private BigInteger primoDH;
    private BigInteger generadorDH;

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
        if( "parametros".equals(accion) ) {
             DiffieHellman( request,response );
             
        } else if ("resultadoDH".equals(accion)){
            claveDH(request, response);
        } else {
            response.getWriter().print("Error");
        }
    }
    
    private void DiffieHellman( HttpServletRequest reques, HttpServletResponse response ) {
        try {
            AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator.getInstance("DH");
            paramGen.init(512);

            AlgorithmParameters params = paramGen.generateParameters();
            DHParameterSpec dhSpec;
            dhSpec = (DHParameterSpec) params.getParameterSpec(DHParameterSpec.class);

            System.out.println("" + dhSpec.getP() + "\n" + dhSpec.getG() + "\n" + dhSpec.getL());

            JsonObjectBuilder respuesta = Json.createObjectBuilder();
            
            primoDH = dhSpec.getP();
            generadorDH = dhSpec.getG();
            
            respuesta.add("primo", primoDH);
            respuesta.add("generador", generadorDH);
            respuesta.add("longitud",dhSpec.getL());
            
            JsonObject o = respuesta.build();

            PrintWriter out = response.getWriter();
            System.out.println(o);
            out.print(o);
    
        } catch (InvalidParameterSpecException | IOException | NoSuchAlgorithmException ex) {
            
        }
    }
    
    private void claveDH(HttpServletRequest request, HttpServletResponse response){
        try{
            String resultadoCliente = request.getParameter("resultado");
            BigInteger res = new BigInteger(resultadoCliente);
            BigInteger xa = randomBigInteger(primoDH);
            BigInteger y = modulo(generadorDH, xa, primoDH);
            try{
               response.getWriter().print(y.toString());
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
            BigInteger clave = modulo(res, xa, primoDH);
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            byte []claveSesion = md.digest(clave.toString().getBytes());
            
            HttpSession sesion = request.getSession(true);
            sesion.setAttribute("clave",claveSesion);
            
            String hexClave = bytesToHex(claveSesion);
            System.out.println("Clave acordada: " + hexClave);
            
            //System.out.println("Clave acordada: " + claveSesion.toString());
        }catch(NoSuchAlgorithmException nsae){
            nsae.printStackTrace();
        }
    }
    
    private BigInteger randomBigInteger(BigInteger n) {
        Random rnd = new Random();
        int maxNumBitLength = n.bitLength();
        BigInteger aRandomBigInt;
        do {
            aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
        } while (aRandomBigInt.compareTo(n.subtract(new BigInteger("2"))) > 0 || aRandomBigInt.compareTo(new BigInteger("2")) < 0); 
        return aRandomBigInt;
    }
    
    private BigInteger modulo( BigInteger a, BigInteger b, BigInteger c ) {
        BigInteger x = new BigInteger("1");
        BigInteger y = a;
        BigInteger zero = new BigInteger("0");
        BigInteger dos = new BigInteger("2");

        while( b.compareTo( zero ) == 1 ) {
            if( b.mod( dos ).compareTo( new BigInteger("1") ) == 0 ) {
                x = ( x.multiply( y ) ).mod( c );
            }

            y = ( y.multiply( y ) ).mod( c ); // squaring the base
            b = b.divide( dos );
        }

        return x.mod( c );
    }
    
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
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
