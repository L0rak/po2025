package org.example.symulator;

public class Silnik extends Komponent{

    private int maxObroty;

    private int obroty;

    public void uruchom() {}

    public void zatrzymaj() {}

    public Silnik(String nazwa, float waga, float cena, int maxObroty, int obroty) {
        super(nazwa, waga, cena);

        this.maxObroty = maxObroty;
        this.obroty = 1000;
    }

    public int getObroty() {
        return obroty;
    }

    public int getMaxObroty() {
        return maxObroty;
    }

    public void zwiekszObroty() {
        if (obroty + 100 <= maxObroty) obroty += 100;
    }

    public void zmniejszObroty() {
        if (obroty - 100 >= 0) obroty -= 100;
    }

}
