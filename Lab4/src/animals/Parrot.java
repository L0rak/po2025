package animals;

public class Parrot extends Animals{

    public Parrot() {
        name = "Ara";
        legs = 2;
    }

    @Override
    public String getDescription() {
        return "Lata i kraka, calkiem kolorowa jest standardowo";
    }

    @Override
    public void makeSound() {
        System.out.println("Kra kra");
    }
}
