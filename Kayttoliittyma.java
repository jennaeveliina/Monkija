package oopeht;

import java.io.FileNotFoundException;

import apulaiset.*;

/**
 *
 * Harjoitustyö
 *
 * Olio-ohjelmoinnin perusteet, kevät 2016,
 *
 * @author Jenna Halmetoja
 *
 * k�ytt�liittym�-luokka
 */
public class Kayttoliittyma {

    public Logiikka peli = new Logiikka();
    public static boolean jatketaan = true;
    public static final String VIRHE = "Virhe!";
    public static final String KOPS = "Kops!";
    public static final String TAPPIO = "Tappio!";
    public static final String VOITTO = "Voitto!";
    public static final String LOPPU = "Ohjelma lopetettu.";
    

    /**
     * pelataan peliä
     * 
     * @throws FileNotFoundException
     */
    public void pelaa() throws FileNotFoundException {
        // tervehditään käyttäjää aluksi.
        System.out.println("***********");
        System.out.println("* SOKKELO *");
        System.out.println("***********");
        // ladataan kenttä ensimmäisen kerran
        peli.lataa();

        while (jatketaan) {
            System.out.println("Kirjoita komento:");
            String kasky = In.readString();

            String[] syote = kasky.split(" ");
            // päätellään minkä komennon käyttäjä on antanut
            if (syote[0].equals("katso") || syote[0].equals("liiku")
                    || syote[0].equals("muunna")) {
                try {
                    if (syote[0].equals("katso")) {
                        peli.katso(syote[1]);

                    } else if (syote[0].equals("liiku")) {
                        if((syote[1].charAt(0) == peli.POHJOINEN ||
                            syote[1].charAt(0) == peli.ETELA || 
                            syote[1].charAt(0) == peli.ITA ||
                            syote[1].charAt(0) == peli.LANSI) && 
                            syote.length == 2){
                        peli.liiku(syote[1].charAt(0));
                        }else{
                            System.out.println(VIRHE);
                        }
                    } else if (syote[0].equals("muunna")) {
                        peli.muunna(Integer.parseInt(syote[1]));
                    } else {
                        System.out.println(VIRHE);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(VIRHE);
                }

            } else if (kasky.equals("lataa")) {
                peli.lataa();
            } else if (kasky.equals("inventoi")) {
                if (peli.ladattu == true) {
                    peli.inventoi();
                } else {
                    System.out.println(VIRHE);
                }
            } else if (kasky.equals("kartta")) {
                    peli.kartta();
            } else if (kasky.equals("odota")) {
                if(jatketaan){
                    //TODO:MIT� T�H�N???!
                    peli.odota();
                }else{
                    System.out.println(LOPPU);
                    lopeta();
                }
            } else if (kasky.equals("tallenna")) {
                //System.out.println("tallennan kentan...");
                peli.tallenna();
            } else if (kasky.equals("lopeta")) {
                peli.kartta();
                System.out.println(LOPPU);
                lopeta();
            } else {
                System.out.println(VIRHE);
            }
            
        }
    }

    public static void lopeta() {
        //System.out.println(Kayttoliittyma.LOPPU);
        jatketaan = false;
        //System.out.println(LOPPU);
    }

}
