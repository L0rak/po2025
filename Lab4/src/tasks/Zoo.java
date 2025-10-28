package tasks;

import animals.*;

import java.util.Random;

public class Zoo {
    Animals[] animals = new Animals[100];

    public Zoo() {
        wypelnijZwierzetami();
    }

    private void wypelnijZwierzetami() {
        Random random = new Random();

        for (int i = 0; i < animals.length; i++) {

            int animalType = random.nextInt(3);

            switch (animalType) {
                case 0:
                    animals[i] = new Dog();
                    break;
                case 1:
                    animals[i] = new Parrot();
                    break;
                case 2:
                    animals[i] = new Snake();
                    break;
            }

            animals[i].makeSound();
        }
    }

    public int policzNogi() {
        int totalLegs = 0;

        for (Animals animal : animals) {
            totalLegs += animal.getLegs();
        }
        return totalLegs;
    }

    public Animals[] getAnimals() {
        return animals;
    }

    public static void main(String[] args) {

        Zoo Zoo = new Zoo();

        int nogi = Zoo.policzNogi();

        System.out.println("Całkowita liczba nóg we wszystkich zwierzętach w ZOO: " + nogi);

    }
}
