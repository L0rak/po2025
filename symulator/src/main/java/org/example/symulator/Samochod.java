package org.example.symulator;

public class Samochod {

    private boolean stanWlaczenia;

    private String nrRejest;

    private String model;

    private int predkoscMax;


    private Silnik silnik;

    private SkrzyniaBiegow skrzynia;

    private Pozycja aktualnaPozycja;

    public Samochod(String nrRejest, String model, int predkoscMax, Silnik silnik, SkrzyniaBiegow skrzynia) {
        this.nrRejest = nrRejest;
        this.model = model;
        this.predkoscMax = predkoscMax;
        this.silnik = silnik;
        this.skrzynia = skrzynia;

        this.aktualnaPozycja = new Pozycja();
        this.stanWlaczenia = false;
    }

    public void wlacz() {
        this.stanWlaczenia = true;
    }

    public void wylacz() {
        this.stanWlaczenia = false;
    }

    public void getWaga() {}

    public double getAktPredkosc() {
        return aktualnaPredkosc;
    }

    public Pozycja getAktPozycja() {
        return aktualnaPozycja;
    }

    public Silnik getSilnik() {
        return silnik;
    }

    public SkrzyniaBiegow getSkrzynia() {
        return skrzynia;
    }

    public boolean czyWlaczony() {
        return stanWlaczenia;
    }

    private Pozycja celPodrozy;

    private double aktualnaPredkosc;

    public void jedzDo(Pozycja cel) {
        this.celPodrozy = cel;
        System.out.println("Cel ustawiony: " + cel.getPozycje());
    }

    public void aktualizujStan(double deltaTime) {
        if (!stanWlaczenia) {
            aktualnaPredkosc = 0;
            return;
        }

        if (skrzynia.getSprzeglo().czyWcisniete()) {
            aktualnaPredkosc *= 0.99;
        } else {
            double mocNapedu = silnik.getObroty() * skrzynia.getAktualnePrzelozenie() * 0.01;
            aktualnaPredkosc = mocNapedu;
        }

        if (celPodrozy != null && aktualnaPredkosc > 0.1) {
            double obecneX = aktualnaPozycja.getX();
            double obecneY = aktualnaPozycja.getY();

            double celX = celPodrozy.getX();
            double celY = celPodrozy.getY();

            double dx = celX - obecneX;
            double dy = celY - obecneY;
            double dystans = Math.sqrt(dx * dx + dy * dy);

            if (dystans > 5.0) {

                double kierunekX = dx / dystans;
                double kierunekY = dy / dystans;


                double ruch = aktualnaPredkosc * deltaTime * 50.0;

                aktualnaPozycja.aktualizujPozycje(kierunekX * ruch, kierunekY * ruch);
            } else {
                // Dojechaliśmy
                aktualnaPredkosc = 0;
            }
        }
    }





}

