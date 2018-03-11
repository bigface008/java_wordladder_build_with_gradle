/**
 * App of WordLadder
 * @author 516030910460
 * @version 0.0.2
 */

public class App {
    public static void main(String[] args) {
        if (args.length != 3) { // Check if args are enough.
            System.out.println("Make sure to input 3 arguments.");
            System.exit(1);
        }

        try {
            // Build the dictionary.
            WordLadder map = new WordLadder(args[0], args[1].length());

            // Main Step.
            map.ladder(args[1], args[2]);
        } catch (Exception e) { // File name is wrong.
            System.out.println("Wrong file name.");
            System.exit(1);
        }
    }
}