package org.example.symulator;

public class SkrzyniaBiegow extends Komponent {

    private int aktualnyBieg;
    private int iloscBiegow;
    private float aktualnePrzelozenie;
    private Sprzeglo sprzeglo;

    public SkrzyniaBiegow(String nazwa, float waga, float cena, int iloscBiegow, Sprzeglo sprzeglo) {
        super(nazwa, waga, cena);
        this.iloscBiegow = iloscBiegow;
        this.sprzeglo = sprzeglo;
        this.aktualnyBieg = 1; // Startujemy z jedynki (lub 0 dla luzu)
    }

    public void zwiekszBieg() {
        if (sprzeglo.czyWcisniete()) {
            if (aktualnyBieg < iloscBiegow) {
                aktualnyBieg++;
                System.out.println("Bieg w górę: " + aktualnyBieg);
            }
        } else {
            System.out.println("ZGRZYT! Wciśnij sprzęgło!");
        }
    }

    public void zmniejszBieg() {
        if (sprzeglo.czyWcisniete()) {
            if (aktualnyBieg > 1) { // Zakładamy, że 1 to najniższy, chyba że masz R lub N
                aktualnyBieg--;
                System.out.println("Bieg w dół: " + aktualnyBieg);
            }
        } else {
            System.out.println("ZGRZYT! Wciśnij sprzęgło!");
        }
    }

    public int getAktBieg() {
        return aktualnyBieg;
    }

    // Potrzebne do obliczenia prędkości (uproszczone)
    public float getAktualnePrzelozenie() {
        return aktualnyBieg * 0.5f;
    }

    public Sprzeglo getSprzeglo() {
        return sprzeglo;
    }
}