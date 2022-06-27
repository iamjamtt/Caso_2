package ENTIDAD;

public class producto {
    int id_pro;
    String des_pro;
    double prec_pro;
    int est_pro;

    public producto() {
    }

    public producto(int id_pro, String des_pro, double prec_pro, int est_pro) {
        this.id_pro = id_pro;
        this.des_pro = des_pro;
        this.prec_pro = prec_pro;
        this.est_pro = est_pro;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getDes_pro() {
        return des_pro;
    }

    public void setDes_pro(String des_pro) {
        this.des_pro = des_pro;
    }

    public double getPrec_pro() {
        return prec_pro;
    }

    public void setPrec_pro(double prec_pro) {
        this.prec_pro = prec_pro;
    }

    public int getEst_pro() {
        return est_pro;
    }

    public void setEst_pro(int est_pro) {
        this.est_pro = est_pro;
    }

}
