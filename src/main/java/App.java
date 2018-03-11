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
            WordMap map = new WordMap(args[0], args[1].length());

            // Change to lower case.
            String word_1 = args[1].toLowerCase();
            String word_2 = args[2].toLowerCase();

            // Main Step.
            map.ladder(word_1, word_2);
        } catch (Exception e) { // File name is wrong.
            System.out.println("Wrong file name.");
            System.exit(1);
        }
    }
}