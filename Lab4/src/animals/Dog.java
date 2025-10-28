package animals;

public class Dog extends Animals{

    public Dog() {
        name = "Burek";
        legs = 4;
    }


    @Override
    public String getDescription() {
        return "No jest taki i sobie chodzi i umie na przyklad siad zrobic czy cos";
    }


    @Override
    public void makeSound() {
        System.out.println("Szczek szczek");
    }


}
