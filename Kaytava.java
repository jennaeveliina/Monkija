package luokka;

import oopeht.OmaLista;

/**
 *
 * Harjoitusty√∂
 *
 * Olio-ohjelmoinnin perusteet, kev√§t 2016,
 *
 * @author Jenna Halmetoja
 *
 * Kaytava-luokka
 */
public class Kaytava extends Osat {

    private OmaLista lattia = new OmaLista();

    /**
     * Rakentaja
     *
     * @param rivi
     * @param sarake
     */
    public Kaytava(int rivi, int sarake) {
        super(rivi, sarake);
        super.merkki(' ');
    }

    public void tulosta(){
        for(int i = 0; i < lattia.koko(); i++){
            System.out.println(lattia.alkio(i));
        }
    }
    /**
     * P‰‰tell‰‰n mik‰ merkki n‰kyy 
     * p‰‰llimm‰isen‰ kartalla
     * 
     * @return p‰‰llimm‰isen olion merkki
     */
    public char ylinMerkki(){
        if(lattia.koko() > 0){
            for(int i = 0; i < lattia.koko(); i++){
                if(lattia.alkio(i) instanceof Monkija){
                    Monkija m = (Monkija)lattia.alkio(i);
                    return m.merkki();
                }
                if(lattia.alkio(i) instanceof Robotti){
                    Robotti r = (Robotti)lattia.alkio(i);
                    return r.merkki();
                }
            }
            Esine e = (Esine)lattia.alkio(0);
            return e.merkki();
        }
        return merkki();
    }
    
    public OmaLista varasto(){
        return lattia;
    }

    @Override
    public String toString() {
        return "Kaytava  |" + super.toString();
    }
    @Override
    public boolean sallittu() {
        return true;
    }
}
