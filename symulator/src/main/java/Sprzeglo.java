public class Sprzeglo extends Komponent{

    private String stanSprzegla;

    public static void wcisnij() {};

    public static void zwolnij() {};

    public Sprzeglo(String nazwa, float waga, float cena, String stanSprzegla) {
        super(nazwa, waga, cena);

        this.stanSprzegla = stanSprzegla;
    }

}
