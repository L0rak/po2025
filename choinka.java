// public class choinka {
//     void main() {
//         for (int i=1; i<21; i++) {
//             for (int j=1; j<i+1; j++) {
//                 System.out.print("*");
//             }
//             System.out.println("");
//         }
//     }
// }

public class choinka {
    public static void main(String[] args) {

        int wysokosc = Integer.parseInt(args[0]);

        for (int i=1; i<wysokosc+1; i++) {
            for (int j=1; j<wysokosc+1-i; j++) {
                System.out.print(" ");
            }
            for (int j=1; j<i+1; j++) {
                System.out.print("**");
            }
            System.out.println("");
        }
    }
}