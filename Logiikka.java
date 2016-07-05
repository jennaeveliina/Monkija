package oopeht;

import apulaiset.*;

import java.io.*;

import luokka.*;

/**
 *
 * Harjoitusty√∂
 *
 * Olio-ohjelmoinnin perusteet, kevat 2016,
 *
 * @author Jenna Halmetoja
 * 
 * Logiikka-luokka
 */
public class Logiikka {

    // vakioita ilmansuunnille.
    public final char POHJOINEN = 'p';
    public final char ETELA = 'e';
    public final char ITA = 'i';
    public final char LANSI = 'l';
    // attribuutteja
    public Object[][] sokkelo = null;
    public OmaLista robottiLista = new OmaLista();
    private Monkija monkija;

    private int rivi;
    private int sarake;
    private int ekaRivi;
    private int ekaSarake;
    // private int energia;
    // private char suunta;
    private int esineita = 0;
    boolean ladattu = false;
    boolean liikuttu = false;
    private int siemen = 0;
    private String[] kohta;

    /**
     * metodi, joka tulostaa monkijan tiedot
     */
    public void inventoi() {
        if (monkija.varasto() != null && monkija != null) {
            System.out.println(monkija);
            monkija.varasto().tulostaVarasto();
        }
    }

    /**
     * Metodi, joka lataa tiedot tekstitiedostosta ja luo listat
     *
     * @param kentta
     * @return kentta
     */
    public void lataa() {
        String tietue = "";
        Kaytava kaytava = null;
        sokkelo = null;
        robottiLista = new OmaLista();

        try {
            FileInputStream syotevirta = new FileInputStream("sokkelo.txt");
            InputStreamReader lukija = new InputStreamReader(syotevirta);
            // luetaan ekan rivin tiedot erikseen
            try (BufferedReader puskuroituLukija = new BufferedReader(lukija)) {
                // luetaan ekan rivin tiedot erikseen
                puskuroituLukija.ready();
                tietue = puskuroituLukija.readLine();
                // havitetaan valily√∂nnit
                tietue = tietue.replaceAll("\\s", "");
                // katkaistaan valimerkkien kohdalta
                kohta = tietue.split("\\|");
                kohta[0].trim();

                siemen = Integer.parseInt(kohta[0]);
                ekaRivi = Integer.parseInt(kohta[1]);
                ekaSarake = Integer.parseInt(kohta[2]);
                Automaatti.alusta(siemen);
                sokkelo = new Object[ekaRivi][ekaSarake];

                while (puskuroituLukija.ready()) {
                    // luetaan rivit
                    tietue = puskuroituLukija.readLine();
                    // System.out.println(tietue);
                    // havitetaan valily√∂nnit
                    tietue = tietue.replaceAll("\\s", "");
                    // katkaistaan valimerkkien kohdalta
                    kohta = tietue.split("\\|");

                    rivi = Integer.parseInt(kohta[1]);
                    sarake = Integer.parseInt(kohta[2]);
                    
                    // System.out.println(kohta[0].trim());
                    switch (kohta[0]) {

                    case "Seina":
                        Seina seina = new Seina(rivi, sarake);
                        sokkelo[rivi][sarake] = seina;
                        // System.out.println("SEINAT");
                        break;
                    case "Kaytava":
                        kaytava = new Kaytava(rivi, sarake);
                        sokkelo[rivi][sarake] = kaytava;
                        // System.out.println("KAYTAVAT");
                        break;
                    case "Robotti":
                        int energia = Integer.parseInt(kohta[3]);
                        char suunta = kohta[4].charAt(0);
                        Robotti robotti = new Robotti(rivi, sarake, energia,
                                suunta);
                        // lis‰t‰‰n robotti robottien listalle
                        robottiLista.lisaaLoppuun(robotti);
                        kaytava.varasto().varastoi(robotti);
                        // System.out.println("ROBOTIT");
                        break;
                    case "Esine":
                        energia = Integer.parseInt(kohta[3]);
                        Esine esine = new Esine(rivi, sarake, energia);
                        kaytava = (Kaytava) sokkelo[rivi][sarake];
                        kaytava.varasto().varastoi(esine);
                        // System.out.println(sokkelo[rivi][sarake]);
                        for(int i = 0; i < kaytava.varasto().koko(); i++){
                            if (kaytava.varasto().alkio(i) instanceof Monkija) {
                                //System.out.println("moi");
                                keraaEsine(kaytava);
                                paivitaEsineet();
                                // tarkistaEsineet();
                            }
                        }
                        esineita++;
                        // System.out.println("ESINEET");
                        break;
                    case "Monkija":
                        energia = Integer.parseInt(kohta[3]);
                        suunta = kohta[4].charAt(0);
                        kaytava = (Kaytava) sokkelo[rivi][sarake];
                        // esine = new Esine(rivi, sarake, energia);
                        monkija = new Monkija(rivi, sarake, energia, suunta);
                        kaytava.varasto().varastoi(monkija);
                        // System.out.println("MONKIJAT");
                        break;
                    default:
                        break;
                    }

                }
                puskuroituLukija.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Tiedosto hukassa!");
        } catch (IOException e) {
            System.out.println("Lukuvirhe!");
        }
        ladattu = true;
    }

    /**
     * Metodi, joka tulostaa kentan
     *
     * @param t
     */
    public void kartta() {

        for (int i = 0; i < sokkelo.length; i++) {
            for (int j = 0; j < sokkelo[0].length; j++) {

                if (sokkelo[i][j] instanceof Seina) {
                    Seina s = (Seina) sokkelo[i][j];
                    System.out.print(s.merkki());
                } else {
                    Kaytava k = (Kaytava) sokkelo[i][j];
                    System.out.print(k.ylinMerkki());
                }
            }
            System.out.println("");
        }
    }

    /**
     * 
     * @param robo
     * @return vertailu
     */
    public boolean taistelu(Paikallinen robotti) {
        Robotti robo = (Robotti) robotti;
        if (monkija.compareTo(robotti) >= 0) {
            monkija.energia(monkija.energia() - robo.energia());
            robottiLista.poista(robotti);
            Kaytava kaytava = (Kaytava) sokkelo[robo.rivi()][robo.sarake()];
            kaytava.varasto().poista(robotti);
            return true;
        } else {
            return false;
        }
    }

    /**
     * muunnetaan energiaa monkijalle
     * 
     * @param energiamaara
     */
    public void muunna(int maara) {
        // tarkistetaan ett‰ monkijan listalla on esineit‰
        if (maara <= monkija.varasto().koko() && maara > 0) {
            // muunnetaan esineit‰
            for (int i = 0; i < maara; i++) {
                Esine e = (Esine) monkija.varasto().alkio(0);
                int energiaMaara = e.energia();
                int monkijaEnergia = monkija.energia();
                monkija.energia(energiaMaara + monkijaEnergia);
                monkija.varasto().poista(0);

            }

        } else {
            System.out.println(Kayttoliittyma.VIRHE);
        }
    }

    /**
     * Katsotaan suuntaan x
     * 
     * @return
     */
    public void katso(String suuntaan) {
        if (suuntaan.length() == 1) {
            if (suuntaan.charAt(0) == POHJOINEN) {
                // monkija.suunta(POHJOINEN);
                tulostaPaikka(monkija.rivi() - 1, monkija.sarake());
            } else if (suuntaan.charAt(0) == ETELA) {
                // monkija.suunta(ETELA);
                tulostaPaikka(monkija.rivi() + 1, monkija.sarake());
            } else if (suuntaan.charAt(0) == LANSI) {
                // monkija.suunta(LANSI);
                tulostaPaikka(monkija.rivi(), monkija.sarake() - 1);
            } else if (suuntaan.charAt(0) == ITA) {
                // monkija.suunta(ITA);
                tulostaPaikka(monkija.rivi(), monkija.sarake() + 1);
            } else {
                System.out.println(Kayttoliittyma.VIRHE);
            }
        } else {
            System.out.println(Kayttoliittyma.VIRHE);
        }
    }
    /**
     * katsomisen apumetodi, tulostaa
     * halutun paikan
     * @param riv
     * @param sar
     */
    public void tulostaPaikka(int riv, int sar) {
        if (sokkelo[riv][sar] instanceof Seina) {
            System.out.println(sokkelo[riv][sar]);
        } else {
            Kaytava k = (Kaytava) sokkelo[riv][sar];
            System.out.println(sokkelo[riv][sar]);
            k.tulosta();
        }
    }

    /**
     * Metodi, joka liikuttaa olioita kentalla
     */
    public void liiku(char suunta) {

        // apumuuttuja jonka avulla tarkkaillaan seuraavaa
        // paikkaa, riippuen liikkeen suunnasta.
        Object paikka;

        // liikutetaan olioita
        switch (suunta) {

        case POHJOINEN:
            monkija.suunta(POHJOINEN);
            paikka = sokkelo[monkija.rivi() - 1][monkija.sarake()];
            if (paikka instanceof Seina) {
                System.out.println(Kayttoliittyma.KOPS);
            } else {
                Kaytava kaytava = (Kaytava) paikka;
                if (kaytava.varasto().koko() > 0) {
                    // jos vastaan tulee esine,
                    // niin ker‰t‰‰n se mˆnkij‰n varastoon
                    while (kaytava.varasto().etsi("Esine") != null) {
                        keraaEsine(kaytava);
                        paivitaEsineet();
                    }
                }
                Kaytava vanhaPaikka = (Kaytava) sokkelo[monkija.rivi()][monkija
                        .sarake()];
                vanhaPaikka.varasto().poista(monkija);
                Kaytava uusiPaikka = (Kaytava) paikka;
                monkija.rivi(uusiPaikka.rivi);
                monkija.sarake(uusiPaikka.sarake);

                uusiPaikka.varasto().varastoi(monkija);
                paivitaEsineet();
            }
            break;
        case ITA:
            monkija.suunta(ITA);
            paikka = sokkelo[monkija.rivi()][monkija.sarake() + 1];
            if (paikka instanceof Seina) {
                System.out.println(Kayttoliittyma.KOPS);
            } else {
                Kaytava kaytava = (Kaytava) paikka;
                if (kaytava.varasto().koko() > 0) {
                    while (kaytava.varasto().etsi("Esine") != null) {
                        keraaEsine(kaytava);
                        paivitaEsineet();
                    }
                }
                Kaytava vanhaPaikka = (Kaytava) sokkelo[monkija.rivi()][monkija
                        .sarake()];
                vanhaPaikka.varasto().poista(monkija);
                Kaytava uusiPaikka = (Kaytava) paikka;
                monkija.rivi(uusiPaikka.rivi);
                monkija.sarake(uusiPaikka.sarake);
                uusiPaikka.varasto().varastoi(monkija);
                paivitaEsineet();
            }
            break;
        case ETELA:
            monkija.suunta(ETELA);
            paikka = sokkelo[monkija.rivi() + 1][monkija.sarake()];
            if (paikka instanceof Seina) {
                System.out.println(Kayttoliittyma.KOPS);
            } else {
                Kaytava kaytava = (Kaytava) paikka;
                if (kaytava.varasto().koko() > 0) {
                    while (kaytava.varasto().etsi("Esine") != null) {
                        keraaEsine(kaytava);
                        paivitaEsineet();
                    }
                    while (kaytava.varasto().etsi("Robotti") != null
                            && Kayttoliittyma.jatketaan == true) {
                        System.out.println("PƒƒSIN TƒNNE");
                        tutkiVoitto(kaytava);
                    }
                }
                Kaytava vanhaPaikka = (Kaytava) sokkelo[monkija.rivi()][monkija
                        .sarake()];
                vanhaPaikka.varasto().poista(monkija);
                Kaytava uusiPaikka = (Kaytava) paikka;
                monkija.rivi(uusiPaikka.rivi);
                monkija.sarake(uusiPaikka.sarake);
                uusiPaikka.varasto().varastoi(monkija);
                paivitaEsineet();
            }
            break;
        case LANSI:
            monkija.suunta(LANSI);
            paikka = sokkelo[monkija.rivi()][monkija.sarake() - 1];
            if (paikka instanceof Seina) {
                System.out.println(Kayttoliittyma.KOPS);
            } else {
                Kaytava kaytava = (Kaytava) paikka;

                if (kaytava.varasto().koko() > 0) {
                    while (kaytava.varasto().etsi("Esine") != null) {
                        keraaEsine(kaytava);
                        paivitaEsineet();
                    }
                }
                Kaytava vanhaPaikka = (Kaytava) sokkelo[monkija.rivi()][monkija
                        .sarake()];
                vanhaPaikka.varasto().poista(monkija);
                Kaytava uusiPaikka = (Kaytava) paikka;
                monkija.rivi(uusiPaikka.rivi);
                monkija.sarake(uusiPaikka.sarake);
                uusiPaikka.varasto().varastoi(monkija);
                paivitaEsineet();
            }
            break;
        default:
            System.out.println(Kayttoliittyma.VIRHE);
        }
        tarkistaEsineet();
        if (esineita != 0) {
            vuoronVaihto();
            kartta();
        }
        liikuttu = true;
    }
    /**
     * ker‰ill‰‰n esineet monkij‰n varastoon
     * @param kaytava
     */
    public void keraaEsine(Kaytava kaytava) {
        Esine esine = (Esine) kaytava.varasto().etsi("Esine");
        monkija.varasto().varastoi(esine);
        kaytava.varasto().poista(esine);
        esineita--;
    }
    /**
     * tutkitaan kumpi voittaa, 
     * robotti vai mˆnkij‰
     * @param kaytava
     */
    public void tutkiVoitto(Kaytava kaytava) {
        Robotti robotti;
        robotti = (Robotti) kaytava.varasto().etsi("Robotti");
        //jos false niin taistelu h‰vittiin ja peli loppuu
        boolean taistelunTulos = taistelu(robotti);
        if (!taistelunTulos) {
            System.out.println(Kayttoliittyma.TAPPIO);
            Kayttoliittyma.lopeta();
        } else {
            System.out.println(Kayttoliittyma.VOITTO);
        }
    }
    /**
     * jos esineet on loppu niin lopetetaan peli
     */
    public void tarkistaEsineet() {
        if (esineita == 0) {
            kartta();
            System.out.println(Kayttoliittyma.LOPPU);
            Kayttoliittyma.lopeta();
        }
    }
    /**
     * p‰ivitet‰‰n esineiden arvot 
     * samoiksi kuin mˆnkij‰n
     */
    public void paivitaEsineet() {
        int i = 0;
        while (i < monkija.varasto().koko()) {
            Esine esine = (Esine) monkija.varasto().alkio(i);

            esine.rivi(monkija.rivi());
            esine.sarake(monkija.sarake());
            i++;
        }
    }

    /**
     * pelaaja haluaa odottaa yhden vuoron.
     */
    public void odota() {
        vuoronVaihto();
        kartta();
    }

    /**
     * vaihdetaan vuoroa robotin ja monkijan v‰lill‰
     */
    public void vuoronVaihto() {
        //poistetaan robotit listalta ennen p‰ivityst‰
        for (int i = 0; i < robottiLista.koko(); i++) {
            // poistetaan kaikki robot kent‰lt‰
            Paikallinen apu = (Paikallinen) robottiLista.alkio(i);
            Kaytava k = (Kaytava) sokkelo[apu.rivi()][apu.sarake()];
            k.varasto().poista("Robotti");
        }
        //p‰ivitet‰‰n robottilistan paikat
        Automaatti.paivitaPaikat(robottiLista, sokkelo);
        
        //lis‰t‰‰n robotit listoille
        for (int i = 0; i < robottiLista.koko(); i++) {
            Paikallinen apu = (Paikallinen) robottiLista.alkio(i);
            
            Kaytava k = (Kaytava) sokkelo[apu.rivi()][apu.sarake()];
            k.varasto().varastoi(apu);
            
            
            for (int j = 0; j < k.varasto().koko(); j++) {
                Osat sis = (Osat) k.varasto().alkio(j);
                
                //taistellaan jos vastassa on mˆnkij‰
                if (sis instanceof Monkija) {
                    if(taistelu(apu)){
                        System.out.println(Kayttoliittyma.VOITTO);
                        i--;
                    }else{
                        Kayttoliittyma.lopeta();
                    }
                }
            }
        }
    }
    /**
     * Tallennukset v‰lilyˆntilaskuri
     * @param str
     * @return
     */
    private String tallennusValit(String str) {
        String palautus = str;

        while (palautus.length() < 4) {
            palautus += " ";
        }
        return palautus;
    }

    /**
     * tallennus-metodi
     */
    public void tallenna() {
        final String TIEDNIMI = "sokkelo.txt";
        Kaytava k;
        try {
            // Luodaan tiedosto-olio
            File tiedosto = new File(TIEDNIMI);
            // Luodaan tulostusvirta ja
            // liitet‰‰n se tiedostoon
            FileOutputStream tulostusvirta = new FileOutputStream(tiedosto);
            // Luodaan virtaan kirjoittaja
            PrintWriter kirjoittaja = new PrintWriter(tulostusvirta, true);
            // otetaan apumuuttujiin talteen oikeat v‰lim‰‰r‰t
            String siemenLuku = tallennusValit(Integer.toString(siemen));

            String riveja = tallennusValit(Integer.toString(ekaRivi));
            String sarakkeita = tallennusValit(Integer.toString(ekaSarake));
            //
            // System.out.println(siemen + " " + riveja + " " + sarakkeita);
            // tallennetaan eka rivi tiedostoon
            kirjoittaja.println(tallennusValit(siemenLuku + "|" + riveja + "|"
                    + sarakkeita+ "|"));
            // otetaan loput rivit talteen
            for (int i = 0; i < sokkelo.length; i++) {
                for (int j = 0; j < sokkelo[0].length; j++) {
                    kirjoittaja.println(sokkelo[i][j]);
                    
                    if (sokkelo[i][j] instanceof Kaytava) {
                        k = (Kaytava) sokkelo[i][j];
                        //jos monkija, niin tulosta monkija ja sis‰ltˆ
                        if(k.varasto().etsi("Monkija") != null){

                            kirjoittaja.println(monkija);
                            for(int x = 0; x < monkija.varasto().koko(); x++){
                                kirjoittaja.println(monkija.varasto().alkio(x));
                            }
                        }
                        //jos ei monkijaa tulostetaan k‰yt‰v‰n varasto
                        else{
                            for(int l = 0; l < k.varasto().koko(); l++){
                                kirjoittaja.println(k.varasto().alkio(l));
                            }
                        }
                    }
                }
            }

            kirjoittaja.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
