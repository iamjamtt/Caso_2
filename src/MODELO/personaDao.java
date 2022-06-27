package MODELO;

import DB.db;
import ENTIDAD.persona;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class personaDao {
    PreparedStatement ps;
    ResultSet rs;
    
    DB.db con = new db();
    Connection acce;
    
    public int addPersona(Object[] o) {
        int r = 0;
        String sql = "INSERT INTO persona(dni_per,nom_per,ape_per,email_per,dir_per,fec_nac_per,id_tper) VALUES(?,?,?,?,?,?,?)";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            ps.setObject(5, o[4]);
            ps.setObject(6, o[5]);
            ps.setObject(7, o[6]);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error al ingresar persona  " + e);
        }
        
        return r;
    }
    
    public int idPersona(){
        int c=0;
        
        String sql = "SELECT max(id_per) FROM persona";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                c = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error al obtener datos de del ultimo id de la persona ingresada:  " + e);
        }
        
        return c;
    }
    
    public int obtenerIdPersonaPorDNI(String dni){
        int a = 0; 
        String sql = "SELECT id_per FROM persona WHERE dni_per=? AND id_tper=1";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setString(1, dni);
            rs = ps.executeQuery();
            while(rs.next()){
                a = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("erro al obtener id de la persona por medio de su dni: " + e);
        }
        
        return a;
    }
    
    public ENTIDAD.persona datosPersona_id(int id_per){
        ENTIDAD.persona ep = new persona();
        
        String sql = "SELECT * FROM persona WHERE id_per=?";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setInt(1, id_per);
            rs = ps.executeQuery();
            while(rs.next()){
                ep.setId_per(rs.getInt(1));
                ep.setDni_per(rs.getString(2));
                ep.setNom_per(rs.getString(3));
                ep.setApe_per(rs.getString(4));
                ep.setEmail_per(rs.getString(5));
                ep.setDir_per(rs.getString(6));
                ep.setFec_nac_per(rs.getString(7));
                ep.setId_tper(rs.getInt(8));
            }
        } catch (Exception e) {
            System.out.println("error al obtener datos de persona por id:  " + e);
        }
        
        return ep;
    }
}
