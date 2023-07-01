
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionUtil {
    Connection con = null;
    
    public static Connection conDB()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/personas", "root", "123456");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConexionUtil : "+ex.getMessage());
           return null;
        }
    }
    //make sure you add the lib
}


