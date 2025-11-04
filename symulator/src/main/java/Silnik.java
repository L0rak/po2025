public class Silnik extends Komponent{

    private int maxObroty;

    private int obroty;

    public void uruchom() {}

    public void zatrzymaj() {}

    public void zwiekszObroty() {}

    public void zmniejszObroty() {}

    public Silnik(String nazwa, float waga, float cena, int maxObroty, int obroty) {
        super(nazwa, waga, cena);

        this.maxObroty = maxObroty;
        this.obroty = 0;
    }

}
