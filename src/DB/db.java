package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class db {
    Connection con;
    String controlador = "com.mysql.jdbc.Driver";
    String db = "db_caso2";
    String url="jdbc:mysql://localhost:3306/"+db;
    String user="root";
    String pass="";
    
    
    public Connection conectardb(){
        try {
            Class.forName(controlador);
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            System.out.println("error al activar DB: " + e);
        }
        return con;
    }
}
