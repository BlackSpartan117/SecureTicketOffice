package negocio;

import entidades.Evento;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;

public class DAO {

    private Connection conn;
    private Statement sentenciaSQL;

    public DAO(Connection conn) {
        this.conn = conn;
    }

    public void insertar(String tipo, String lugar, String ruta, String rutaAlterna, String fecha, String id) {
        String query = "INSERT INTO evento VALUES( '"
                + tipo + "',  '" + lugar + "', '" + ruta + "', '" + rutaAlterna + "', '" + fecha + "', '" + id + "' )";

        try {
            if (this.conn == null) {
                System.out.println("La conexion es nula");
            } else {
                sentenciaSQL = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                System.out.println("Consulta: " + query);
                sentenciaSQL.executeUpdate(query);
                System.out.println("Registro Finalizo con exito!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarFigura(String figura, String id) {
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

    public void eliminar(String id) {
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
    }
}
