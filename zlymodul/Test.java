import java.util.Random;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        long starting= System.currentTimeMillis();

        ArrayList<Integer> Typy = new ArrayList<Integer>();

        for(int i = 0; i<6; i++) {
            Typy.add(Integer.parseInt(args[i]));
        }

        System.out.println("Typy uzytkownika: " + Typy);

        int trafienia = 0;

        while(trafienia != 6) {
            trafienia = 0;

            Random random = new Random();
            ArrayList<Integer> wyniki = new ArrayList<Integer>();

            for(int i = 0; i<6; i++) {
                wyniki.add(random.nextInt(49));
            }
//            System.out.println("Wylosowane liczby: " + wyniki);

            for (Integer typ : Typy) {
                if (wyniki.contains(typ)) {
                    trafienia++;
                }
            }

//            System.out.println("Liczba trafień: " + trafienia);

        }

        long ending=System.currentTimeMillis();
        System.out.println("Ile szukalo: "+ (ending-starting) + "ms");

    }

}
