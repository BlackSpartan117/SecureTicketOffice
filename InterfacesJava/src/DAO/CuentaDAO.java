package DAO;

import entidades.cuenta;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import negocio.ConexionMySQL;
import negocio.PropiedadConexion;
import java.sql.Date;

public class CuentaDAO {

    private Connection conn;
    private Statement sentenciaSQL;

    public CuentaDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar( cuenta c ) {
        String query = "INSERT INTO cuenta( id, nombre, apellido, noTarjetaCredito, cvv, saldo, vigencia )"
                + "VALUES( " + c.getId()+ ", '" + c.getNombre() + "', '" + c.getApellido() + "','" 
                + c.getNoTarjetaCredito()+ "', '"+ c.getCvv() +" '," + c.getSaldo()+ ",'" + c.getVigencia()+ "')" ;
        System.out.println(query);
        
        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            } else {
                sentenciaSQL = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                System.out.println("Consulta: " );
                sentenciaSQL.executeUpdate(query);
                System.out.println("Registro Finalizo con exito!!");
            }

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
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
    public static void main(String[] args) {
        String ruta = System.getProperty("user.dir")+ "/src/DAO/config.properties";
        PropiedadConexion connProp = new PropiedadConexion(ruta);
        ConexionMySQL ConexionBD = new ConexionMySQL(connProp);
        ConexionBD.getConexion();
        CuentaDAO Cuenta2 = new CuentaDAO(ConexionBD.getConn());
        
        cuenta m = new cuenta (1, "Juan", "Perez", "04455", "100", 2500, new Date(2000, 1, 1));
        Cuenta2.insertar(m);
}
  
}