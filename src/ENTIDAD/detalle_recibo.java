package ENTIDAD;
public class detalle_recibo {
    int id_drec;
    int id_pro;
    int id_rec;
    int cant_drec;
    double sub_drec;

    public double getSub_drec() {
        return sub_drec;
    }

    public void setSub_drec(double sub_drec) {
        this.sub_drec = sub_drec;
    }

    public int getCant_drec() {
        return cant_drec;
    }

    public void setCant_drec(int cant_drec) {
        this.cant_drec = cant_drec;
    }

    public detalle_recibo() {
    }

    public int getId_drec() {
        return id_drec;
    }

    public void setId_drec(int id_drec) {
        this.id_drec = id_drec;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }
    
    
}
