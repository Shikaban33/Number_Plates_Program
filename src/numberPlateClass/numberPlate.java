package numberPlateClass;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class numberPlate {

    //private Integer num_id;
    private String num_pav;
    private Timestamp num_data;
    private Integer num_kaina;
    private String num_tipas;
    private String num_forma;

    private String num_isvaizd;

    private String num_user_pav;

    @Override
    public String toString() {
        return "numberPlate{" +
                "num_pav='" + num_pav + '\'' +
                ", num_data=" + num_data +
                ", num_kaina=" + num_kaina +
                ", num_forma='" + num_forma + '\'' +
                ", num_tipas='" + num_tipas + '\'' +
                ", num_isvaizd='" + num_isvaizd + '\'' +
                '}';
    }

    public numberPlate(String num_pav, Timestamp num_data, Integer num_kaina, String num_tipas, String num_forma, String num_isvaizd, String num_user_pav) {
        this.num_pav = num_pav;
        this.num_data = num_data;
        this.num_kaina = num_kaina;
        this.num_tipas = num_tipas;
        this.num_forma = num_forma;
        this.num_isvaizd = num_isvaizd;
        this.num_user_pav = num_user_pav;
    }

    public String getNum_pav() {
        return num_pav;
    }

    public void setNum_pav(String num_pav) {
        this.num_pav = num_pav;
    }

    public Timestamp getNum_data() {
        return num_data;
    }

    public void setNum_data(Timestamp num_data) {
        this.num_data = num_data;
    }

    public Integer getNum_kaina() {
        return num_kaina;
    }

    public void setNum_kaina(Integer num_kaina) {
        this.num_kaina = num_kaina;
    }

    public String getNum_tipas() {
        return num_tipas;
    }

    public void setNum_tipas(String num_tipas) {
        this.num_tipas = num_tipas;
    }

    public String getNum_forma() {
        return num_forma;
    }

    public void setNum_forma(String num_forma) {
        this.num_forma = num_forma;
    }

    public String getNum_isvaizd() {
        return num_isvaizd;
    }

    public void setNum_isvaizd(String num_isvaizd) {
        this.num_isvaizd = num_isvaizd;
    }

    public String getNum_user_pav() {
        return num_user_pav;
    }

    public void setNum_user_pav(String num_user_pav) {
        this.num_user_pav = num_user_pav;
    }
}
