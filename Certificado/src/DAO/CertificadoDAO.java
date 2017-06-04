package DAO;

import certificado.CifradorRSA;
import entidades.Cuenta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import configuracion.ConexionMySQL;
import configuracion.PropiedadConexion;
import entidades.Certificado;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Calendar;

public class CertificadoDAO {
    
    private Connection conn;
    private Statement sentenciaSQL;

    public CertificadoDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar( Certificado c ) {
        String query = "INSERT INTO certificado( idCliente, apellido, cumple, expiracion, publicKey, firmaBank)"
                + " VALUES( " + c.getIdCliente() + ", '" + c.getApellido() + "', '" + c.getCumple() + "', '" 
                + c.getExpiacion() + "', ?, ? )";
        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            } else {
                PreparedStatement statement = this.conn.prepareStatement(query);
                statement.setBytes(1, c.getPublicKey());
                statement.setBytes(2, c.getFirmaBank());
                System.out.println("Consulta: " + query);
                statement.executeUpdate();
                System.out.println("Registro Finalizo con exito!!");
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }
    
    public static void main(String argv[]){
        String nombre = "Pancho";
        String apellidoP = "Lopez";
        String apellidoM = "Lopez";
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Calendar c = Calendar.getInstance(); 
        c.setTime(sqlDate); 
        c.add(Calendar.YEAR, 5);
        java.sql.Date vigencia = new java.sql.Date(c.getTimeInMillis());
        
        /********* Generar certificado **********/
        CifradorRSA cifrador = new CifradorRSA();
        KeyPair llavesCliente = cifrador.generarLlaves(2048);
        certificado.Certificado cer;
        
        cer = new certificado.Certificado("1", nombre + " " + apellidoP + " " + apellidoM, vigencia.toString(), llavesCliente.getPublic(), "Banxico", vigencia.toString());
        PrivateKey llavePrivadaBanco = (PrivateKey) cifrador.leerLlave("llaves/private.key", CifradorRSA.TipoLlave.PRIVADA);
        cer.firmar(llavePrivadaBanco);
        cifrador.guardarLlave("certificados/" + cer.getId() + ".key", llavesCliente.getPrivate(), CifradorRSA.TipoLlave.PRIVADA);
        System.out.println("Certificado generado");
        System.out.println(cer);
        
        String ruta = System.getProperty("user.dir")+ "/src/configuracion/config.properties";
        PropiedadConexion connProp = new PropiedadConexion(ruta);
        ConexionMySQL ConexionBD = new ConexionMySQL(connProp);
        ConexionBD.getConexion();
        Certificado elCertificado = new Certificado(1, vigencia, apellidoM, vigencia, cer.getLlave().getEncoded(), cer.getFirma());
        CertificadoDAO cdao = new CertificadoDAO(ConexionBD.getConn());
        cdao.insertar(elCertificado);
    }
  
}