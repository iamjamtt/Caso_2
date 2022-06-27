package MODELO;

import DB.db;
import ENTIDAD.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class productoDao {
    PreparedStatement ps;
    ResultSet rs;
    
    DB.db con = new db();
    Connection acce;
    
    public ENTIDAD.producto datosProducto_id(int id_pro){
        ENTIDAD.producto ep = new producto();
        
        String sql = "SELECT * FROM producto WHERE id_pro=?";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setInt(1, id_pro);
            rs = ps.executeQuery();
            while(rs.next()){
                ep.setId_pro(rs.getInt(1));
                ep.setDes_pro(rs.getString(2));
                ep.setPrec_pro(rs.getDouble(3));
                ep.setEst_pro(rs.getInt(4));
            }
        } catch (Exception e) {
            System.out.println("error al obtener datos de producto por id:  " + e);
        }
        
        return ep;
    }
    
    //Aqui hacemos una consulta para mostrar los datos de los productos en una tabla
    public DefaultTableModel consultarProducto(String b){
        String []titulos={"ID","DESCRIPCION","PRECIO","ESTADO"};
        DefaultTableModel m = new DefaultTableModel(null, titulos);
        Object[] o = new Object[4];
        
        String sql = "SELECT * FROM producto WHERE est_pro=1 AND (id_pro LIKE '%" + b + "%' OR des_pro LIKE '%" + b +"%')";
   
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                o[0] = rs.getInt(1);
                o[1] = rs.getString(2);
                o[2] = rs.getString(3);
                if(Integer.parseInt(rs.getString(4))==1){
                    o[3] = "ACTIVO";
                }else{
                    o[3] = "DESACTIVO";
                }
                
                m.addRow(o);
            }
        } catch (Exception e) {
            System.out.println("error consultar datos de los productos para mostrar en la tabla: " + e);
        }

        return m;
    }
    
    public int updateProducto(Object[] o) {
        int r = 0;
        String sql = "UPDATE producto SET des_pro=?, prec_pro=? WHERE id_pro=?";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setObject(1, o[0]);
            ps.setObject(2, o[1]);
            ps.setObject(3, o[2]);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("error actualizar producto " + e);
        }
        
        return r;
    } 
    
    public String[] obtenerCantidadDelDiaReportePorProducto(String fecha, int id_pro){
        String[] a = new String[2]; 
        String sql = "SELECT p.des_pro,SUM(d.cant_drec) FROM detalle_rec d INNER JOIN recibo r ON d.id_rec=r.id_rec INNER JOIN producto p ON d.id_pro=p.id_pro WHERE r.fec_rec=? AND d.id_pro=?";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setInt(2, id_pro);
            rs = ps.executeQuery();
            while(rs.next()){
                a[0] = rs.getString(1);
                a[1] = ""+rs.getInt(2);
            }
        } catch (Exception e) {
            System.out.println("erro al obtener la cantidad de los productos por dia - reporte: " + e);
        }
        
        return a;
    }
    
    public int obtenerCountProducto(){
        int a = 0; 
        String sql = "SELECT COUNT(id_pro) FROM producto";
        
        try {
            acce = con.conectardb();
            ps = acce.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                a = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("erro al obtener la cantidad de productos en la db: " + e);
        }
        
        return a;
    }
}
