package org.example.symulator;

public class Sprzeglo extends Komponent {

    private boolean wcisniete; // true = rozłączone (można zmieniać biegi)

    public Sprzeglo(String nazwa, float waga, float cena) {
        super(nazwa, waga, cena);
        this.wcisniete = false; // Domyślnie "sklejone" (napęd przekazywany)
    }

    public void wcisnij() {
        this.wcisniete = true;
    }

    public void zwolnij() {
        this.wcisniete = false;
    }

    public boolean czyWcisniete() {
        return wcisniete;
    }
}