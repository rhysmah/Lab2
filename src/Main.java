import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// Drives the program.
public class Main {
    public static void main(String[] args) {

        BufferedReader fileReader;
        String filePath = "src/dict.txt";

        // Attempt to open the file.
        try {
            fileReader = new BufferedReader(new FileReader(filePath));

            String text = fileReader.readLine();
            String[] words = text.toLowerCase().split(" ");

            technique1(words);
            technique2(words);
            technique3(words);
        }

        // If file open fails, print error.
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Anagram Checker -- Technique 1
    public static void technique1(String[] words) {

        double startTime = System.currentTimeMillis(); // Timer to count function runtime.

        int largestAnagramCount = 0;
        int[] anagramCounter    = new int[words.length];  // Stores anagram counts for each word

        // Loop through all words in "words" array. Each word
        // "words[i]" will be the target word for that loop.
        for (int wordsIndex = 0; wordsIndex < words.length; ++wordsIndex) {

            // Once target word identified, convert to char array.
            char[] targetWord = words[wordsIndex].toCharArray();

            // Compare all words to the target word. Each word
            // "words[j]" will be the comparison word.
            for (String word : words) {

                // If "targetWord" and "comparisonWord" are same length AND
                // "targetWord" and "comparisonWord" are NOT the same word,
                // then they _may_ be an anagram.
                if (words[wordsIndex].length() == word.length() && !words[wordsIndex].equals(word)) {

                    // Break target word and comparison word into character arrays.
                    char[] comparisonWord = word.toCharArray();

                    // Loop through all letters in the target word. Compare each letter in the
                    // target word to all letters in the comparison word. If there's a match,
                    // remove the first instance of that letter; if not, immediately skip to
                    // the next letter.
                    for (char targetWordLetter : targetWord) {
                        for (int letterIndex = 0; letterIndex < comparisonWord.length; ++letterIndex) {
                            if (targetWordLetter == comparisonWord[letterIndex]) {
                                comparisonWord[letterIndex] = '\0';
                                break;
                            }
                        }
                    }
                    // Once we've compared all target word letters to comparison word letters,
                    // check if comparison word has any letters left; if so, they two words are
                    // not anagrams.
                    boolean letterNotFound = true;
                    for (char comparisonWordLetter : comparisonWord) {
                        if (comparisonWordLetter != '\0') {
                            letterNotFound = false;
                            break;
                        }
                    }
                    // If there are no letters left in the comparison word, the two words are
                    // anagrams, so we increase the count in the array.
                    if (letterNotFound) {
                        ++anagramCounter[wordsIndex];
                    }
                }
            }
            if (anagramCounter[wordsIndex] > largestAnagramCount) {
                largestAnagramCount = anagramCounter[wordsIndex];
            }
        }
        // Find the word with the most anagrams -- both the count and the word's index position.
        ArrayList<String> results     = countHighestAnagram(words, anagramCounter, largestAnagramCount);
        double endTime                = System.currentTimeMillis();
        double executionTime          = (endTime - startTime) / 1000.00; // Calculate time in seconds.

        System.out.println("*** SORTING TECHNIQUE 1 ***");
        System.out.printf("Word(s) with most anagrams (%d): \"%s\".\n", largestAnagramCount, results);
        System.out.printf("Time to complete search (sec): %.2f\n\n", executionTime);
    }

//  Anagram Checker -- Technique 2
    public static void technique2(String[] words) {

        double startTime = System.currentTimeMillis(); // Timer to count function runtime.

        int   largestAnagramCount = 0;
        int[] anagramCounter      = new int[words.length]; // Stores anagram counts for each word

        // Loop through all strings in "words" array.
        // Each word selected here becomes the "target" word.
        for (int wordsIndex = 0; wordsIndex < words.length; ++wordsIndex) {

            // Once target word has been selected, convert to char array and sort.
            char[] targetWord = words[wordsIndex].toCharArray();
            Arrays.sort(targetWord);

            // Once a target word has been selected, loop through
            // remaining words; remaining words are "comparison" words.
            for (String word : words) {

                // Check that words are the same length AND that they're not the same word.
                // If words _are_ the same length, they _may_ be anagrams.
                if (words[wordsIndex].length() == word.length() && !words[wordsIndex].equals(word)) {

                    // Convert strings to char arrays and sort.
                    char[] comparisonWord = word.toCharArray();
                    Arrays.sort(comparisonWord);

                    // Loop through sorted arrays and check that they're equal.
                    boolean isAnagram = true;
                    for (int k = 0; k < targetWord.length; ++k) {
                        if (targetWord[k] != comparisonWord[k]) {
                            isAnagram = false;
                            break;
                        }
                    }
                    // If the two sorted arrays are equal, they're anagrams; increase count.
                    if (isAnagram) {
                        ++anagramCounter[wordsIndex];
                    }
                }
                if (anagramCounter[wordsIndex] > largestAnagramCount) {
                    largestAnagramCount = anagramCounter[wordsIndex];
                }
            }
        }
        // Find the word with the most anagrams -- both the count and the word's index position.
        ArrayList<String> results     = countHighestAnagram(words, anagramCounter, largestAnagramCount);
        double endTime                = System.currentTimeMillis();
        double executionTime          = (endTime - startTime) / 1000.00; // Calculate time in seconds.

        System.out.println("*** SORTING TECHNIQUE 2 ***");
        System.out.printf("Word(s) with most anagrams (%d): \"%s\".\n", largestAnagramCount, results);
        System.out.printf("Time to complete search (sec): %.2f\n\n", executionTime);
    }

    // Anagram Checker -- Technique 3
    public static void technique3(String[] words) {

        double startTime = System.currentTimeMillis(); // Timer to count function runtime.

        int   largestAnagramCount = 0;
        int[] anagramCounter      = new int[words.length];  // Stores anagram counts for each word

        // Loop through all strings in "words" array.
        // Each word selected here becomes the "target" word.
        for (int wordsIndex = 0; wordsIndex < words.length; ++wordsIndex) {

            // Once target word has been selected, convert to char array.
            int[] targetLetterCounter = new int[123];
            char[] targetWord         = words[wordsIndex].toCharArray();

            for (char c : targetWord) {
                ++targetLetterCounter[c];
            }

            // Once a target word has been selected, loop through
            // remaining words; remaining words are "comparison" words.
            for (String word : words) {

                // Check that words are the same length AND that they're not the same word.
                // If words _are_ the same length, they _may_ be anagrams.
                if (words[wordsIndex].length() == word.length() && !words[wordsIndex].equals(word)) {

                    // Using larger size, so we can increment letter counts using letter ascii code.
                    int[] comparisonLetterCounter = new int[123];

                    // Convert strings to char arrays and sort.
                    char[] comparisonWord = word.toCharArray();

                    for (char c : comparisonWord) {
                        ++comparisonLetterCounter[c];
                    }
                    boolean isAnagram = true;
                    for (int letterCountCompare = 0; letterCountCompare < comparisonLetterCounter.length; ++letterCountCompare) {
                        if (targetLetterCounter[letterCountCompare] != comparisonLetterCounter[letterCountCompare]) {
                            isAnagram = false;
                            break;
                        }
                    }
                    if (isAnagram) {
                        ++anagramCounter[wordsIndex];
                    }
                }
                if (anagramCounter[wordsIndex] > largestAnagramCount) {
                    largestAnagramCount = anagramCounter[wordsIndex];
                }
            }
        }
        // Find the word with the most anagrams -- both the count and the word's index position.
        ArrayList<String> results     = countHighestAnagram(words, anagramCounter, largestAnagramCount);
        double endTime                = System.currentTimeMillis();
        double executionTime          = (endTime - startTime) / 1000.00; // Calculate time in seconds.

        System.out.println("*** SORTING TECHNIQUE 3 ***");
        System.out.printf("Word(s) with most anagrams (%d): \"%s\".\n", largestAnagramCount, results);
        System.out.printf("Time to complete search (sec): %.2f\n\n", executionTime);
    }

    public static ArrayList<String> countHighestAnagram(String[] listOfWords, int[]anagramCount, int highestCount) {

        ArrayList<String> wordsWithHighestAnagramCount = new ArrayList<>();
        for (int i = 0; i < listOfWords.length; ++i) {
            if (anagramCount[i] == highestCount) {
                wordsWithHighestAnagramCount.add(listOfWords[i]);
            }
        }
        return wordsWithHighestAnagramCount;
    }
}