package MODELO;

import DB.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class reciboDao {
    PreparedStatement ps;
    ResultSet rs;
    
    DB.db con = new db();
    Connection acce;
    
    public String maxCodRecibo(){
        String serie = "";
        String sql = "SELECT max(cod_rec) FROM recibo";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                serie = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("error en consultar codigo de recibo " + e);
        }
        
        return serie;
    }
    
    public int addRecibo(Object[] o) {
        int r = 0;
        String sql = "INSERT INTO recibo(cod_rec,fec_rec,mon_rec,est_rec,id_per) VALUES(?,?,?,?,?)";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            ps.setObject(5, o[4]);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error al ingresar recibo  " + e);
        }
        
        return r;
    }
    
    public int idRecibo(){
        int c=0;
        
        String sql = "SELECT max(id_rec) FROM recibo";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                c = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error al obtener datos de del ultimo id del recibo ingresado:  " + e);
        }
        
        return c;
    }
    
    public int maxIdRecibo(){
        int a = 0;
        String sql = "SELECT max(id_rec) FROM recibo";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                a = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("error en consultar id de recibo " + e);
        }
        
        return a;
    }
}
