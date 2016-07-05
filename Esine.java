package luokka;
/**
 *
 * Harjoitustyö
 *
 * Olio-ohjelmoinnin perusteet, kevät 2016,
 *
 * @author Jenna Halmetoja
 *
 * Esine-luokka
 */
public class Esine extends Sisalto {
    //private int energia;

    public Esine(int r, int s, int e) {
        super(r, s);
        super.merkki('E');
        super.energia(e);
    }
    
    @Override
    public String toString() {
        String vali = super.rivinPituus(this.energia());
        return "Esine    |" + super.toString() +vali;
    }
    
}
