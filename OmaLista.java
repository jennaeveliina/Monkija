package oopeht;
import luokka.Sisalto;
import fi.uta.csjola.oope.lista.*;
/**
 *
 * Harjoitusty√∂
 *
 * Olio-ohjelmoinnin perusteet, kev√§t 2016,
 *
 * @author Jenna Halmetoja
 *
 * OmaLista-luokka
 */
public class OmaLista extends LinkitettyLista {

    /**
     * Lis√§t√§√§n olioita listalle energian 
     * mukaan kasvavaan j√§rjestykseen
     * @param alkio
     */
    public void varastoi(Object alkio) {
            boolean flag = false;
            //int i = koko;
            Sisalto osa = (Sisalto) alkio;
            if (koko == 0) {
                lisaaAlkuun(osa);
            } else if (koko == 1) {
                if (osa.compareTo(alkio(0)) != 1) {
                    lisaaAlkuun(osa);
                } else {
                    lisaaLoppuun(alkio);
                }
            } else {
                int j = 0;
                while (!flag) {
                    if (osa.compareTo(alkio(j)) != 1) {
                        lisaa(j, osa);
                        flag = true;
                    } else if (j == koko - 1) {
                        lisaaLoppuun(osa);
                        flag = true;
                    }
                    j++;
                }
            }
        }
    public void tulostaVarasto(){
        for(int i = 0; i < koko(); i++){
            System.out.println(alkio(i));
        }
    }
    /**
     * 
     * @param luokka
     * @return
     */
    public Object etsi(String nimi) {
        //etsit‰‰n haluttu alkio.
        for(int i = 0; i < koko; i++) {
            if (alkio(i).getClass().getSimpleName().equals(nimi)) {
                return alkio(i);
            }
        }
        return null;
    }
    
    /** Poistaa annetun nimisen luokan tietoalkiot listalta ja palauttaa
     * viitteet niihin listalla.
     * 
     * @param luokanNimi listalta poistettavien alkioiden luokan nimi.
     * @return lista, jossa viitteet poistettuihin alkoihin. Lista on tyhj‰,
     * jos listalla ei ollut luokan olioita.
     */
    public OmaLista poista(String luokanNimi) {
        // Tehd‰‰n palautettava lista.
        OmaLista poistetut = new OmaLista();

        // Yritet‰‰n poistaa, jos alkioita on v‰hint‰‰n yksi.
        if (koko > 0) {
            // Silmukoidaan lista l‰pi alusta loppuun.
            int i = 0;
            while (i < koko) {
                // Selvitet‰‰n metaolion avulla nykyisen tietoalkion luokan nimi.
                String alkionLuokanNimi = alkio(i).getClass().getSimpleName();

                // Poistetaan alkio, jos luokan nimi on parametrina annettu
                // ja lis‰t‰‰n viite tuloslistan loppuun.
                if (alkionLuokanNimi.equals(luokanNimi))
                    poistetut.lisaaLoppuun(poista(i));
                // Kasvatetaan laskuria vain, kun ei poisteta, jotta alkioita
                // ei j‰isi v‰liin.
                else
                    i++;
            }
        }

        // Palautetaan viite tuloslistaan.
        return poistetut;
    }

    /** Poistaa annettuun viitteeseen liittyv‰n alkion listalta.
     *
     * @param alkio viite poistettavaan tietoalkioon. Paluuarvo on null,
     * jos parametri on null-arvoinen tai poistettavaa alkiota ei lˆytynyt.
     */
    public Object poista(Object alkio) {
        // Apuviite, joka alustetaan aluksi virhekoodilla.
        Object poistettava = null;

        // K‰‰ntyy todeksi, jos lˆydet‰‰n poistettava alkio.
        boolean loydetty = false;

        // K‰yd‰‰n listaa l‰pi alusta loppuun niin pitk‰‰n kuin alkioita on
        // saatavilla tai poistettavaa ei ole lˆydetty.
        int i = 0;
        while (i < koko && !loydetty) {
            // Lˆydettiin tietoalkio, johon liittyy parametri ja listan solmu.
            if (alkio == alkio(i)) {
                // Asetetaan poistettavaan alkioon apuviite, jotta alkiota ei hukata.
                poistettava = poista(i);

                // Lˆydettiin mit‰ haettiin.
                loydetty = true;
            }

            // Siirryt‰‰n seuraavaan paikkaan.
            else
                i++;
        }

        // Palalutetaan viite mahdollisesti poistettuun alkioon.
        return poistettava;
    }
}
