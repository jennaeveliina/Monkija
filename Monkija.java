package luokka;

import oopeht.OmaLista;
import apulaiset.Suunnallinen;

public class Monkija extends Sisalto implements Suunnallinen {

    private int energia;
    private char suunta;
    public OmaLista varasto = new OmaLista();

    public Monkija(int r, int s, int e, char i) {
        super(r, s);
        energia(e);
        suunta(i);
        super.merkki('M');
    }
    
    public OmaLista varasto(){
        return varasto;
    }
    
    @Override
    public int energia() {
        return energia;
    }

    @Override
    public void energia(int energia) {
        this.energia = energia;
    }
    /**
     * Olion suunnan palauttava metodi.
     *
     * @return jokin yllä määritellyistä neljästä pääilmansuunnan symbolista.
     */
    public char suunta() {
        return suunta;
    }

    /**
     * Olion suunnan asettava metodi.
     *
     * @param ilmansuunta uusi suunta, joka on jokin neljästä pääilmansuunnasta.
     * @throws IllegalArgumentException jos parametri ei ollut jokin yllä
     * määritellyistä pääilmansuunnan symboleista.
     */
    public void suunta(char ilmansuunta) throws IllegalArgumentException {
        if (ilmansuunta == POHJOINEN || ilmansuunta == LANSI || ilmansuunta == ITA || ilmansuunta == ETELA) {
            suunta = ilmansuunta;
        }
    }

    @Override
    public boolean sallittu() {
        return true;
    }
    @Override
    public String toString() {
        String vali = super.rivinPituus(this.energia());
        String vali2 = super.suuntaValit(this.suunta());
        return "Monkija  |" + super.toString() +vali+ suunta + vali2;
    }

}
