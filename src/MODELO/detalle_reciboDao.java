package MODELO;

import DB.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class detalle_reciboDao {
    PreparedStatement ps;
    ResultSet rs;
    
    DB.db con = new db();
    Connection acce;
    
    public int addRecibo(Object[] o) {
        int r = 0;
        String sql = "INSERT INTO detalle_rec(id_pro,id_rec,cant_drec,sub_drec) VALUES(?,?,?,?)";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            ps.setObject(4, o[3]);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error al ingresar detalle de recibo  " + e);
        }
        
        return r;
    }
}
