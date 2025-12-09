package org.example.symulator;

import java.util.ArrayList;
import java.util.List;

public class Samochod extends Thread {

    private boolean stanWlaczenia;

    private String nrRejest;

    private String model;

    private int predkoscMax;


    private Silnik silnik;

    private SkrzyniaBiegow skrzynia;

    private Pozycja aktualnaPozycja;

    private List<Listener> listeners = new ArrayList<>();

    private boolean running = true;

    public Samochod(String nrRejest, String model, int predkoscMax, Silnik silnik, SkrzyniaBiegow skrzynia) {
        this.nrRejest = nrRejest;
        this.model = model;
        this.predkoscMax = predkoscMax;
        this.silnik = silnik;
        this.skrzynia = skrzynia;

        this.aktualnaPozycja = new Pozycja();
        this.aktualnaPozycja.aktualizujPozycje(200, 200);

        this.stanWlaczenia = false;

        setDaemon(true); //wylaczy aplikacje jak zamkniemy okno
        start();
    }

    @Override
    public void run() {
        double deltat = 0.1;
        while (running) {
            try {
                Thread.sleep(100);

                if (stanWlaczenia && celPodrozy != null) {
                    obliczRuch(deltat);
                    notifyListeners();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }

        }
    }

    private void obliczRuch(double deltat) {
        double dx = celPodrozy.getX() - aktualnaPozycja.getX();
        double dy = celPodrozy.getY() - aktualnaPozycja.getY();
        double odleglosc = Math.sqrt(dx * dx + dy * dy);

        if (odleglosc > 5.0) {
            double aktualnaPredkosc;
            if (skrzynia.getSprzeglo().czyWcisniete()) {
                aktualnaPredkosc = 0;
            } else {
                aktualnaPredkosc = silnik.getObroty() * skrzynia.getAktualnePrzelozenie() * 0.01;
            }

            double ruchX = aktualnaPredkosc * deltat * (dx / odleglosc) * 50.0; // *50.0 to skala mapy
            double ruchY = aktualnaPredkosc * deltat * (dy / odleglosc) * 50.0;

            aktualnaPozycja.aktualizujPozycje(ruchX, ruchY);
        } else {
            celPodrozy = null; //bo dojechano do celu
        }
    }


    public void addListener(Listener listener) {
        listeners.add(listener);
    }
    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
    private void notifyListeners() {
        for (Listener listener : listeners) {
            listener.update();
        }
    }

    public void jedzDo(Pozycja cel) {
        this.celPodrozy = cel;
    }

    public void wlacz() {
        this.stanWlaczenia = true;
    }

    public void wylacz() {
        this.stanWlaczenia = false;
    }

    public void getWaga() {}

    public String getModel() {
        return model;
    }

    public String getNrRejest() {
        return nrRejest;
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

    public double getAktPredkosc() {
        if (skrzynia.getSprzeglo().czyWcisniete()) return 0;
        return silnik.getObroty() * skrzynia.getAktualnePrzelozenie() * 0.01;
    }

    @Override
    public String toString() {
        return model + " (" + nrRejest + ")";
    }
}

