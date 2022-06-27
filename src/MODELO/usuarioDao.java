package MODELO;

import DB.db;
import ENTIDAD.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class usuarioDao {
    
    PreparedStatement ps;
    ResultSet rs;
    
    DB.db con = new db();
    Connection acce;
    
    
    //Sirve para validar los campos al iniciar sesion en el sistema
    public ENTIDAD.usuario ValidarUsuario(String username, String pass){
        ENTIDAD.usuario eu = new usuario();
        
        String sql = "SELECT * FROM usuario WHERE usu_user=? AND pass_user=? AND est_user=1";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            while(rs.next()){
                eu.setId_user(rs.getInt(1));
                eu.setUsu_user(rs.getString(2));
                eu.setPass_user(rs.getString(3));
                eu.setEst_user(rs.getInt(4));
                eu.setId_per(rs.getInt(5));
            }
        } catch (Exception e) {
            System.out.println("error al validad usuario del login:  " + e);
        }
        
        return eu;
    }
}
