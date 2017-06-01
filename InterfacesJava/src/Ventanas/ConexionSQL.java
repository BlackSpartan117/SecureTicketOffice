/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author kars
 */
public class ConexionSQL {
    Connection co;
    Statement stm;
    public ConexionSQL(){
        try{
            Class.forName("com.msql.jdbc.Driver");
            Connection  con = DriverManager.getConection("")
            Statement stm = co.createStatement();
        }
        catch(ClassNotFoundException exc){
            exc.printStackTrace();
        }
    catch (SQLExeption ex){
        Logger.getLogger(ConnectionSQL.class.getName()).log(Level,SEVERE, null, ex);
    }
    }
}
