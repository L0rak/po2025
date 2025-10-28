package animals;

public class Snake extends Animals{

    public Snake() {
        name = "Pajton";
        legs = 0;
    }

    @Override
    public String getDescription() {
        return "No jest taki i sobie chodzi i umie na przyklad siad zrobic czy cos";
    }

    @Override
    public void makeSound() {
        System.out.println("Sssssss");
    }

}
