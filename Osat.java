package luokka;
import apulaiset.Paikallinen;
/**
 *
 * Harjoitustyö
 *
 * Olio-ohjelmoinnin perusteet, kevät 2016,
 *
 * @author Jenna Halmetoja
 *
 * Abstrakti Osat-luokka
 * Juuri-luokka, joka toteuttaa Paikallinen-rajapinnan
 */
public abstract class Osat implements Paikallinen {

    public int rivi = 0;
    public int sarake = 0;
    //tieto merkeistä(aksessorit): aliluokkiin rakentajissa aseta arvo
    char merkki;


    /**
     * Rakentaja
     * @param rivi
     * @param sarake 
     */
    public Osat(int rivi, int sarake) {
        this.rivi = rivi;
        this.sarake = sarake;
    }
    /**
     * Setterit ja getterit kentälle 
     * asetettavaa merkkiä varten 
     * 
     */
    public void merkki(char m) {
        merkki = m;
    }

    public char merkki() {
        return merkki;
    }

    @Override
    public void rivi(int r) {
        rivi = r;
    }

    @Override
    public int rivi() {
        return rivi;
    }

    @Override
    public void sarake(int s) {
        sarake = s;
    }

    @Override
    public int sarake() {
        return sarake;
    }

    @Override
    public boolean sallittu() {
        return false;
    }

    @Override
    public String toString() {
        String riviVali = rivinPituus(this.rivi());
        String sarakeVali = rivinPituus(this.sarake());
        //String energiaVali = rivinPituus(this.energia());

        return this.rivi() + riviVali + this.sarake() + sarakeVali;
    }
    
    /**
     * Vertaillaan rivin pituutta, jotta saadaan
     * sopiva määrä merkkejä toString-metodin korvaukseen.
     * 
     * @param v
     * @return vali
     */
    public String rivinPituus(int v) {
        String vali = null;
        //0-10 niin kolme valilyontia
        if (v < 10) {
            vali = "   |";
        } //10-99 kaks valilyontia
        else if (v >= 10 && v < 100) {
            vali = "  |";
        } //100-999 yksi valilyontia
        else if (v >= 100 && v <= 999) {
            vali = " |";
        }
        else{
            vali = "|";
        }
        return vali;
    }
}
