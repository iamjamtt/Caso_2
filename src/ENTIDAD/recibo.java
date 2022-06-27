package ENTIDAD;

public class recibo {
    int id_rec;
    String cod_rec;
    String fec_rec;
    double mon_rec;
    int est_rec;
    int id_per;

    public recibo() {
    }

    public recibo(int id_rec, String cod_rec, String fec_rec, double mon_rec, int est_rec, int id_per) {
        this.id_rec = id_rec;
        this.cod_rec = cod_rec;
        this.fec_rec = fec_rec;
        this.mon_rec = mon_rec;
        this.est_rec = est_rec;
        this.id_per = id_per;
    }

    public int getId_rec() {
        return id_rec;
    }

    public void setId_rec(int id_rec) {
        this.id_rec = id_rec;
    }

    public String getCod_rec() {
        return cod_rec;
    }

    public void setCod_rec(String cod_rec) {
        this.cod_rec = cod_rec;
    }

    public String getFec_rec() {
        return fec_rec;
    }

    public void setFec_rec(String fec_rec) {
        this.fec_rec = fec_rec;
    }

    public double getMon_rec() {
        return mon_rec;
    }

    public void setMon_rec(double mon_rec) {
        this.mon_rec = mon_rec;
    }

    public int getEst_rec() {
        return est_rec;
    }

    public void setEst_rec(int est_rec) {
        this.est_rec = est_rec;
    }

    public int getId_per() {
        return id_per;
    }

    public void setId_per(int id_per) {
        this.id_per = id_per;
    }
    
    
}
