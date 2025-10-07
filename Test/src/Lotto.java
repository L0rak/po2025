import java.util.Random;

public class Lotto {

    static Random random = new Random();

    void main() {

        for(int i = 0; i<6; i++) {
            System.out.println(random.nextInt(49));
        }
    }
}
