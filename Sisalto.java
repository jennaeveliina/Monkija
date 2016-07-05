package luokka;

/**
 *
 * Harjoitustyö
 *
 * Olio-ohjelmoinnin perusteet, kevät 2016,
 *
 * @author Jenna Halmetoja
 *
 * Abstrakti sisältö-luokka asennetaan robotin ja monkijan ja esineiden energiat
 */
public abstract class Sisalto extends Osat implements Comparable<Object>{

    private int energia;

    /**
     * rakentaja
     *
     * @param r
     * @param s
     */
    public Sisalto(int r, int s) {
        super(r, s);
    }

    public void energia(int e) {
        if (e >= 0) {
            energia = e;
        }
    }

    public int energia() {
        return energia;
    }

    @Override
    public int compareTo(Object v) {
        Sisalto verrattava = (Sisalto) v;
        int vertailu = 0;
        //taistelu
        if (this.energia() > verrattava.energia()) {
            vertailu = 1;
        } else if (this.energia() == verrattava.energia()) {
            vertailu = 0;
        } else if (this.energia() < verrattava.energia()) {
            vertailu = -1;
        }
        return vertailu;
    }
    @Override
    public String toString() {
        return super.toString() + Integer.toString(energia());
    }
    /**
     * Vertaillaan rivin pituutta, jotta saadaan
     * sopiva määrä merkkejä toString-metodin korvaukseen.
     * 
     * @param v
     * @return vali
     */
    public String suuntaValit(char c) {
        String vali = "   |";
        return vali;
    }
}
