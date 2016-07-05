
import java.io.FileNotFoundException;

import oopeht.Kayttoliittyma;
/**
 *
 * Harjoitustyö
 *
 * Olio-ohjelmoinnin perusteet, kevät 2016,
 * 
 * @author Jenna Halmetoja
 *
 * Testi-luokka
 */
public class Oope2016HT {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Kayttoliittyma liittyma = new Kayttoliittyma();

        try {
            liittyma.pelaa();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
