package animals;

public abstract class Animals {
    protected String name;
    protected int legs;
    public abstract String getDescription();
    public abstract void makeSound();

    public int getLegs() {
        return legs;
    }
}
