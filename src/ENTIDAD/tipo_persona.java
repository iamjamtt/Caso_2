package ENTIDAD;

public class tipo_persona {
    int id_tper;
    String des_tper;

    public tipo_persona() {
    }

    public tipo_persona(int id_tper, String des_tper) {
        this.id_tper = id_tper;
        this.des_tper = des_tper;
    }

    public int getId_tper() {
        return id_tper;
    }

    public void setId_tper(int id_tper) {
        this.id_tper = id_tper;
    }

    public String getDes_tper() {
        return des_tper;
    }

    public void setDes_tper(String des_tper) {
        this.des_tper = des_tper;
    }
    
}
