import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    /*
    Name: Alexander Pak
    Project: Hangman
     */
        private static final String wordsList = "src/HangmanWordsList.txt";

        public static void main(String[] args) {
            // Generate a random word from the file
            Scanner scanner = new Scanner(System.in);
            String[] secretWord = getRandomWordFromFile(wordsList);


            // Initialize the game state
            boolean solved = false;
            int remainingLives = 6;
            String hiddenWord = "";
            for (int i = 0; i < secretWord[0].length(); i++) {
                hiddenWord += "_";
            }
            System.out.println(hiddenWord);

            // Game loop
            while (!solved && remainingLives > 0) {
                // Get user input

                System.out.print("Enter a letter: ");
                String userGuess = scanner.next().toUpperCase();

                // Check if the user guess is correct
                if (secretWord[0].contains(userGuess)) {
                    System.out.println("Correct guess!");
                    hiddenWord = updateHiddenWord(hiddenWord, secretWord[0], userGuess);
                    if (isWordSolved(hiddenWord)) {
                        solved = true;
                        System.out.println("You have solved the word!");
                    } else {
                        System.out.println(hiddenWord);
                    }
                } else {
                    System.out.println("Incorrect guess!\n"+hiddenWord);
                    remainingLives--;
                    System.out.println("Remaining lives: " + remainingLives);
                }
            }

            // Game over
            if (!solved) {
                System.out.println("Game over! The hidden word was \"" + secretWord[0] + "\".");
            }
        }

        private static String[] getRandomWordFromFile(String filename) {
            try {
                Scanner scanner = new Scanner(new File(filename));
                Random random = new Random();
                int randomIndex = random.nextInt(121806);
                for (int i = 0; i < randomIndex; i++) {
                    scanner.nextLine();
                }
                return scanner.nextLine().split(",");
            }
            catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
                return new String[]{};
            }
        }

        private static boolean isWordSolved(String hiddenWord) {
            return !hiddenWord.contains("_");
        }

        private static String updateHiddenWord(String hiddenWord, String secretWord, String userGuess) {
            boolean found = false;
            for (int i = 0; i < secretWord.length(); i++) {
                if (secretWord.charAt(i) == userGuess.charAt(0)) {
                    hiddenWord = hiddenWord.substring(0, i) + userGuess.charAt(0) + hiddenWord.substring(i + 1);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Letter not found in word.");
            }
            return hiddenWord;
        }
    }