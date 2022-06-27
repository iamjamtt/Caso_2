package ENTIDAD;

public class usuario {
    int id_user;
    String usu_user;
    String pass_user;
    int est_user;
    int id_per;

    public usuario() {
    }

    public usuario(int id_user, String usu_user, String pass_user, int est_user, int id_per) {
        this.id_user = id_user;
        this.usu_user = usu_user;
        this.pass_user = pass_user;
        this.est_user = est_user;
        this.id_per = id_per;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsu_user() {
        return usu_user;
    }

    public void setUsu_user(String usu_user) {
        this.usu_user = usu_user;
    }

    public String getPass_user() {
        return pass_user;
    }

    public void setPass_user(String pass_user) {
        this.pass_user = pass_user;
    }

    public int getEst_user() {
        return est_user;
    }

    public void setEst_user(int est_user) {
        this.est_user = est_user;
    }

    public int getId_per() {
        return id_per;
    }

    public void setId_per(int id_per) {
        this.id_per = id_per;
    }
    
}
