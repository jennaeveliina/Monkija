package luokka;
import apulaiset.*;
/**
 *
 * Harjoitustyö
 *
 * Olio-ohjelmoinnin perusteet, kevät 2016,
 *
 * @author Jenna Halmetoja
 *
 * Robotti-luokka, joka mallintaa robotti-olioita
 */
public class Robotti extends Sisalto implements Suunnallinen {

    private char suunta;
    /**
     * rakentaja
     * @param r, rivi indeksi
     * @param s, sarake indeksi
     * @param e, energia
     * @param i, ilmansuunta
     */
    public Robotti(int r, int s, int e, char i) {
        super(r, s);
        energia(e);
        suunta(i);
        super.merkki('R');
    }

    @Override
    public char suunta() {
        return suunta;
    }

    @Override
    public void suunta(char ilmansuunta) throws IllegalArgumentException {
        //jos suunta on jokin rajapinnan vakioiduista suunnista.
        if (ilmansuunta == POHJOINEN || ilmansuunta == ETELA || 
                ilmansuunta == ITA || ilmansuunta == LANSI) {
            suunta = ilmansuunta;
        }
    }
    @Override
    public String toString() {
        String vali = super.rivinPituus(this.energia());
        String vali2 = super.suuntaValit(this.suunta());
        return "Robotti  |" + super.toString() +vali+ suunta + vali2;
    }
}
