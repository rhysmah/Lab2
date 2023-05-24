import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Drives the program.
public class Main {
    public static void main(String[] args) {

        String filePath = "src/dict.txt";
        BufferedReader fileReader;

        // Attempt to open the file.
        try {
            fileReader = new BufferedReader(new FileReader(filePath));

            // Read through text; split tokens based on space character and add to array.
            String text = fileReader.readLine();
            String[] words = text.toLowerCase().split(" ");

            techniqueOne(words);

            techniqueTwo(words);

            techniqueThree(words);

        }
        // If file open fails, print error.
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Anagram Checker -- Technique 1
    public static void techniqueOne(String[] words) {

        double startTime = System.currentTimeMillis(); // Timer to count function runtime.
        int[] anagramCounter = new int[words.length];  // Stores anagram counts for each word

        // Loop through all words in "words" array. Each word
        // "words[i]" will be the target word for that loop.
        for (int i = 0; i < words.length; ++i) {

            // Compare all words to the target word. Each word
            // "words[j]" will be the comparison word.
            for (String word : words) {

                // If "targetWord" and "comparisonWord" are same length AND
                // "targetWord" and "comparisonWord" are NOT the same word,
                // then they _may_ be an anagram.
                if (words[i].length() == word.length() && !words[i].equals(word)) {

                    // Break target word and comparison word into character arrays.
                    char[] targetWordLetters = words[i].toCharArray();
                    char[] comparisonWordLetters = word.toCharArray();

                    // Loop through all letters in the target word. Compare each letter in the
                    // target word to all letters in the comparison word. If there's a match,
                    // remove the first instance of that letter; if not, immediately skip to
                    // the next letter.
                    for (char targetWordLetter : targetWordLetters) {
                        for (int l = 0; l < comparisonWordLetters.length; ++l) {
                            if (targetWordLetter == comparisonWordLetters[l]) {
                                comparisonWordLetters[l] = '\0';
                                break;
                            }
                        }
                    }
                    // Once we've compared all target word letters to comparison word letters,
                    // check if comparison word has any letters left; if so, they two words are
                    // not anagrams.
                    boolean letterNotFound = true;
                    for (char comparisonWordLetter : comparisonWordLetters) {
                        if (comparisonWordLetter != '\0') {
                            letterNotFound = false;
                            break;
                        }
                    }
                    // If there are no letters left in the comparison word, the two words are
                    // anagrams, so we increase the count in the array.
                    if (letterNotFound) {
                        ++anagramCounter[i];
                    }
                }
            }
        }

        // Find the word with the most anagrams -- both the count and the word's index position.
        int maxValue            = anagramCounter[0];
        int indexOfHighestCount = 0;

        for (int i = 0; i < anagramCounter.length; ++i) {
            if (anagramCounter[i] > maxValue) {
                maxValue            = anagramCounter[i];
                indexOfHighestCount = i;
            }
        }
        double endTime       = System.currentTimeMillis();
        double executionTime = (endTime - startTime) / 1000.00;

        System.out.printf("Word with most anagrams: %s\n", words[indexOfHighestCount]);
        System.out.printf("How many anagrams does %s have: %d\n", words[indexOfHighestCount], maxValue);
        System.out.printf("Time to complete search (seconds): %.2f\n", executionTime);
    }

    // Anagram Checker -- Technique 2
    public static void techniqueTwo(String[] words) {}

    // Anagram Checker -- Technique 3
    public static void techniqueThree(String[] words) {}
}