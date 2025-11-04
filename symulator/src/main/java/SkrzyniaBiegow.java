public class SkrzyniaBiegow extends Komponent{

    private int aktualnyBieg;

    private int iloscBiegow;

    private float aktualnePrzelozenie;

    private Sprzeglo sprzeglo;

    public void zwiekszBieg() {}

    public void zmniejszBieg() {}

    public void getAktBieg() {}

    public void getAktPrzelozenie() {}

    public SkrzyniaBiegow(String nazwa, float waga, float cena, int aktualnyBieg, float aktualnePrzelozenie, int iloscBiegow, Sprzeglo sprzeglo) {
        super(nazwa, waga, cena);

        this.aktualnyBieg = aktualnyBieg;
        this.iloscBiegow = iloscBiegow;
        this.aktualnePrzelozenie = aktualnePrzelozenie;
        this.sprzeglo = sprzeglo;

    }

}
