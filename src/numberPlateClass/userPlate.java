package numberPlateClass;

import javax.security.auth.callback.TextInputCallback;
import java.sql.Timestamp;

public class userPlate {
    public int asmn_k;
    public String sav_num_pav;
    public String sav_vard;
    public String sav_pavard;
    public Timestamp sav_gim_d;

    public userPlate(int asmn_k, String sav_num_pav, String sav_vard, String sav_pavard, Timestamp sav_gim_d) {
        this.asmn_k = asmn_k;
        this.sav_num_pav = sav_num_pav;
        this.sav_vard = sav_vard;
        this.sav_pavard = sav_pavard;
        this.sav_gim_d = sav_gim_d;
    }

    public int getAsmn_k() {
        return asmn_k;
    }

    public void setAsmn_k(int asmn_k) {
        this.asmn_k = asmn_k;
    }

    public String getSav_num_pav() {
        return sav_num_pav;
    }

    public void setSav_num_pav(String sav_num_pav) {
        this.sav_num_pav = sav_num_pav;
    }

    public String getSav_vard() {
        return sav_vard;
    }

    public void setSav_vard(String sav_vard) {
        this.sav_vard = sav_vard;
    }

    public String getSav_pavard() {
        return sav_pavard;
    }

    public void setSav_pavard(String sav_pavard) {
        this.sav_pavard = sav_pavard;
    }

    public Timestamp getSav_gim_d() {
        return sav_gim_d;
    }

    public void setSav_gim_d(Timestamp sav_gim_d) {
        this.sav_gim_d = sav_gim_d;
    }
}

