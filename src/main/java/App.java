import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * App for WordLadder
 * @author 516030910460
 * @version 0.0.1
 */
public class App {
    public static void main(String[] args) {
        if (args.length != 3) { // Check if args are enough.
            System.out.println("Args are not enough.");
            System.exit(1);
        } else if (args[1].length() != args[2].length()) { // Check if words are the same length.
            System.out.println("The words must be the same length.");
            System.exit(1);
        } else if (args[1].equals(args[2])) { // Check if words are different.
            System.out.println("The words must be different.");
            System.exit(1);
        }

        try {
            // Build the dictionary.
            Set<String> dictionary = dictionaryBuilder(args[0], args[1].length());

            // Change to lower case.
            String word_1 = args[1].toLowerCase();
            String word_2 = args[2].toLowerCase();

            // Check if words exist in the dictionary.
            if (!(dictionary.contains(word_1) && dictionary.contains(word_2))) {
                System.out.println("Make sure to input right words.");
                System.exit(0);
            }

            // Try to find the shortest path.
            ladder(word_1, word_2, dictionary);

            System.exit(0);
        } catch (IOException e) { // File name is wrong.
            System.out.println("Wrong file name.");
            System.exit(1);
        }
    }

    /**
     * dictionaryBuilder
     * Build the dictionary with the given length.
     */
    static Set<String> dictionaryBuilder(String file_name, int len) throws IOException {
        File dic = new File(file_name);
        FileInputStream fin = new FileInputStream(dic);
        BufferedReader br = new BufferedReader(new InputStreamReader(fin));

        String line = null;
        Set<String> dictionary = new HashSet<>();
        while ((line = br.readLine()) != null)
            if (line.length() == len) // Only add words with the given length into the dictionary.
                dictionary.add(line);

        br.close();
        return dictionary;
    }

    /**
     * ladder
     * The core function.
     */
    static void ladder(String word_1, String word_2, Set<String> dictionary) {
        Queue<String> queue = new LinkedList<>(); // Queue used in BFS.
        Map<String, String> map = new HashMap<>(); // Map used for storing the path.
        queue.add(word_1);
        dictionary.remove(word_1);
        while (!queue.isEmpty()) {
            String temp = queue.poll();
            ArrayList<String> neighbors = findNeighbors(temp, dictionary);
            for (String n : neighbors) {
                map.put(n, temp);
                if (n.equals(word_2)) { // Check if get the final word.
                    System.out.println("Paths: " + findPath(map, word_1, word_2));
                    System.exit(0);
                }
                dictionary.remove(n); // Ensure to remove the used word from the dictionary.
                queue.add(n);
            }
        }
        // No path avalible.
        System.out.println("No such path between " + word_1 + " and " + word_2 + ".");
        System.exit(1);
    }

    /**
     * findNeighbors
     * Find neighbors for word in dictionary.
     */
    static ArrayList<String> findNeighbors(String word, Set<String> dictionary) {
        ArrayList<String> neighbors = new ArrayList<>();
        for (String str : dictionary) {
            int len = word.length();
            int check = 0; // Record the number of different char in one word.
            for (int i = 0; i < len; i++) {
                if (word.charAt(i) != str.charAt(i))
                    check++;
                if (check > 1) // If str is not the right word.
                    break;
            }
            if (check == 1)
                neighbors.add(str);
        }
        return neighbors;
    }

    /**
     * findPath
     * Find path from word_2 back to word_1 in the given map.
     */
    static String findPath(Map<String, String> map, String word_1, String word_2) {
        ArrayList<String> path = new ArrayList<>();
        path.add(word_2);

        // Get word from map
        String temp = map.get(word_2);
        while (!temp.equals(word_1)) {
            path.add(temp);
            temp = map.get(temp);
        }

        String result = "";
        for (String word : path) {
            result += (word + " ");
        }
        result += (word_1 + " "); // Don't forget to add word_1.
        return result;
    }
}