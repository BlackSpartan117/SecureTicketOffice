package DAO;

import entidades.Cuenta;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import configuracion.ConexionMySQL;
import configuracion.PropiedadConexion;
import java.math.BigInteger;
import java.sql.Date;

public class CuentaDAO {

    public static int idActual;
    public static String noTarjetaCreditoActual;
    public static String cvvActual;
    
    private Connection conn;
    private Statement sentenciaSQL;

    public CuentaDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar( Cuenta c ) {
        String query = "INSERT INTO cuenta( id, nombre, apellido, noTarjetaCredito, cvv, saldo, vigencia )"
                + "VALUES( '" + CuentaDAO.idActual + "', '" + c.getNombre() + "', '" + c.getApellido() + "', '" 
                + CuentaDAO.noTarjetaCreditoActual + "', '" + CuentaDAO.cvvActual + "'," + c.getSaldo()+ ",'" + c.getVigencia() + "')";
        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            } else {
                sentenciaSQL = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                System.out.println("Consulta: " + query);
                sentenciaSQL.executeUpdate(query);
                System.out.println("Registro Finalizo con exito!!");
                CuentaDAO.idActual++;
                CuentaDAO.noTarjetaCreditoActual = new BigInteger(CuentaDAO.noTarjetaCreditoActual).add(new BigInteger("1")).toString();
                CuentaDAO.cvvActual = new BigInteger(CuentaDAO.cvvActual).add(new BigInteger("1")).toString();
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
    }
    
    public int leerMaxId(){
        String query = "SELECT MAX(id) FROM cuenta ";
        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            }
            
            sentenciaSQL = this.conn.createStatement();
            System.out.println("Consulta: " + query);
            ResultSet r = sentenciaSQL.executeQuery( query );
            if(r.next()){
                return r.getString(1) == null ? 0 : Integer.parseInt(r.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public Cuenta leerCuenta(int id){
        String query = "SELECT * FROM cuenta where id=" + id;
        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            }
            
            sentenciaSQL = this.conn.createStatement();
            System.out.println("Consulta: " + query);
            ResultSet r = sentenciaSQL.executeQuery( query );
            if(r.next()){
                Cuenta c = new Cuenta();
                c.setNoTarjetaCredito(r.getString(4));
                c.setCvv(r.getString(5));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void actualizarParametros(){
        int id =  this.leerMaxId();
        CuentaDAO.idActual = id + 1;
        if(id > 0){
            Cuenta c = this.leerCuenta(id);
            CuentaDAO.noTarjetaCreditoActual = new BigInteger(c.getNoTarjetaCredito()).add(new BigInteger("1")).toString();
            CuentaDAO.cvvActual = new BigInteger(c.getCvv()).add(new BigInteger("1")).toString();
        }else{
            CuentaDAO.noTarjetaCreditoActual = "1000000000000000";
            CuentaDAO.cvvActual = "100";
        }
    }
    /*
    public void actualizarEvento( Evento ev ) {
        String query = "UPDATE evento set nombre = null";
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate(query);
            System.out.println("Consulta: " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void eliminarEvento( Evento ev ) {
        String query = "DELETE FROM evento";

        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            }

            sentenciaSQL = this.conn.createStatement();
            System.out.println("Consulta: " + query);
            sentenciaSQL.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Evento> obtenerEventos() throws IOException {
        String query = "SELECT * FROM evento";
        Evento evs;
        LinkedList<Evento> eventos = new LinkedList();
        
        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            }
            
            sentenciaSQL = this.conn.createStatement();
            System.out.println("Consulta: " + query);
            ResultSet r = sentenciaSQL.executeQuery( query );
            
            while( r.next() ) {
                evs = new Evento();
                evs.setId( Integer.parseInt( r.getString(1) ) );
                evs.setNombre( r.getString(2) );
                evs.setTipo( r.getString(3) );
                evs.setPrecio( Double.parseDouble( r.getString(4) ) );
                evs.setAsientos( Integer.parseInt( r.getString(5) ) );
                evs.setFecha( r.getDate(6) );
                evs.setFoto( r.getBytes("foto") );
                evs.setLugar( r.getString(8) );
                
                eventos.add( evs );
            }
            
            return eventos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }*/
    
    public static void main(String argv[]){
        String ruta = System.getProperty("user.dir")+ "/src/configuracion/config.properties";
        PropiedadConexion connProp = new PropiedadConexion(ruta);
        ConexionMySQL ConexionBD = new ConexionMySQL(connProp);
        ConexionBD.getConexion();
        CuentaDAO Cuenta2 = new CuentaDAO(ConexionBD.getConn());
        Cuenta2.actualizarParametros();
        
        Cuenta m = new Cuenta (2, "Juan", "Perez", "04454323235", "335", 2500, new Date(2000, 1, 1));
        Cuenta2.insertar(m);
        /*int id = Cuenta2.leerMaxId();
        System.out.println(id);
        Cuenta nueva = Cuenta2.leerCuenta(id);
        System.out.println(nueva.getNoTarjetaCredito() + "|" + nueva.getCvv());*/
    }
  
}