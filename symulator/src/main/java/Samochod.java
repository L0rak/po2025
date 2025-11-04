public class Samochod {

    private boolean stanWlaczenia;

    private String nrRejest;

    private String model;

    private int predkoscMax;


    private static Silnik silnik;

    private static SkrzyniaBiegow skrzynia;

    private static Pozycja aktualnaPozycja;


    public void wlacz() {
        this.stanWlaczenia = true;
    }

    public void wylacz() {
        this.stanWlaczenia = false;
    }

    public void jedzDo(Pozycja cel) {}

    public void getWaga() {}

    public void getAktPredkosc() {}

    public Pozycja getAktPozycja() {
        return aktualnaPozycja;
    }


}

