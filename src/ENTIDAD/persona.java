package ENTIDAD;

public class persona {
    int id_per;
    String dni_per;
    String nom_per;
    String ape_per;
    String email_per;
    String dir_per;
    String fec_nac_per;
    int id_tper;

    public persona() {
    }

    public persona(int id_per, String dni_per, String nom_per, String ape_per, String email_per, String dir_per, String fec_nac_per, int id_tper) {
        this.id_per = id_per;
        this.dni_per = dni_per;
        this.nom_per = nom_per;
        this.ape_per = ape_per;
        this.email_per = email_per;
        this.dir_per = dir_per;
        this.fec_nac_per = fec_nac_per;
        this.id_tper = id_tper;
    }

    public int getId_per() {
        return id_per;
    }

    public void setId_per(int id_per) {
        this.id_per = id_per;
    }

    public String getDni_per() {
        return dni_per;
    }

    public void setDni_per(String dni_per) {
        this.dni_per = dni_per;
    }

    public String getNom_per() {
        return nom_per;
    }

    public void setNom_per(String nom_per) {
        this.nom_per = nom_per;
    }

    public String getApe_per() {
        return ape_per;
    }

    public void setApe_per(String ape_per) {
        this.ape_per = ape_per;
    }

    public String getEmail_per() {
        return email_per;
    }

    public void setEmail_per(String email_per) {
        this.email_per = email_per;
    }

    public String getDir_per() {
        return dir_per;
    }

    public void setDir_per(String dir_per) {
        this.dir_per = dir_per;
    }

    public String getFec_nac_per() {
        return fec_nac_per;
    }

    public void setFec_nac_per(String fec_nac_per) {
        this.fec_nac_per = fec_nac_per;
    }

    public int getId_tper() {
        return id_tper;
    }

    public void setId_tper(int id_tper) {
        this.id_tper = id_tper;
    }
    
    
}
