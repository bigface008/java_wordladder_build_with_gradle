import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * WordMap
 * @author 516030910460
 * @version 0.0.1
 */

public class WordLadder {
    private Set<String> dictionary = null;
    private ArrayList<String> path = null;
    private Map<String, String> map = null;
    private Queue<String> queue = null;

    public WordLadder(String file_name, int len) throws IOException {
        File dic = new File(file_name);
        FileInputStream fin = new FileInputStream(dic);
        BufferedReader br = new BufferedReader(new InputStreamReader(fin));

        String line = null;
        dictionary = new HashSet<>();
        while ((line = br.readLine()) != null)
            if (line.length() == len) // Only add words with the given length into the dictionary.
                dictionary.add(line);

        br.close();
    }

    /**
     * ladder
     * The core function for finding paths from word_2 to word_1 with BFS.
     */

    public void ladder(String word_1, String word_2) {
        checkWords(word_1, word_2);
        queue = new LinkedList<>(); // Queue used in BFS.
        map = new HashMap<>(); // Map used for storing the path.
        queue.add(word_1);
        dictionary.remove(word_1);
        while (!queue.isEmpty()) {
            String temp = queue.poll();
            ArrayList<String> neighbors = findNeighbors(temp);
            for (String n : neighbors) {
                map.put(n, temp);
                if (n.equals(word_2)) { // Check if get the final word.
                    System.out.println("Paths: " + findPath(word_1, word_2)); // Print the result.
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
     * checkWords
     * Check if the requirements listed below are satisfied.
     */

    private void checkWords(String word_1, String word_2) {
        if (word_1.length() != word_2.length()) { // Word must be the same length.
            System.out.println("The words must be the same length.");
            System.exit(1);
        } else if (word_1.equals(word_2)) { // Word shouldn't be the same.
            System.out.println("The words must be different.");
            System.exit(1);
        } else if (!dictionary.contains(word_1) || !dictionary.contains(word_2)) { // Words must exist in the dictionary.
            System.out.println("Make sure to input right words.");
            System.exit(1);
        }
    }

    /**
     * testCheckWords
     * CheckWords() used for testing.
     */

    public boolean testCheckWords(String word_1, String word_2) {
        if (word_1.length() != word_2.length()) { // Word must be the same length.
            return false;
        } else if (word_1.equals(word_2)) { // Word shouldn't be the same.
            return false;
        } else if (!dictionary.contains(word_1) || !dictionary.contains(word_2)) { // Words must exist in the dictionary.
            return false;
        }
        return true;
    }

    /**
     * findNeighbors
     * Find neighbors for word in the dictionary.
     */

    private ArrayList<String> findNeighbors(String word) {
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
     * Build the path with the record sorted in map.
     */

    private String findPath(String word_1, String word_2) {
        path = new ArrayList<>();
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
