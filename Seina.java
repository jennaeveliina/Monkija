package luokka;
/**
 *
 * Harjoitustyö
 *
 * Olio-ohjelmoinnin perusteet, kevät 2016,
 *
 * @author Jenna Halmetoja
 *
 * Seina-luokka
 */
public class Seina extends Osat {
    /**
     * Rakentaja
     * @param rivi
     * @param sarake 
     */
    public Seina(int rivi, int sarake) {
        super(rivi, sarake);
        super.merkki('.');
    }
    @Override
    public String toString() {
        return "Seina    |" + super.toString();
    }
    /*@Override
    public String toString() {
        String apu = "Seina  |";

        //
        String riviVali = rivinPituus(this.rivi());
        String sarakeVali = rivinPituus(this.sarake());
        String energiaVali = rivinPituus(this.energia());

        return apu + this.rivi() + riviVali + this.sarake() + sarakeVali + this.energia() + energiaVali;
    }*/
}
