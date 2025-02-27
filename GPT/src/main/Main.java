package me.abbatrombone.traz;

import java.util.Scanner;
import java.util.Random;

public class Main {
    /*
    Prompt: In java write an app that lets you practice typing in stratagems
    */

        // Array of stratagems for practice
        private static final String[] STRATAGEMS = {
                "Know yourself and you will win all battles.",
                "The supreme art of war is to subdue the enemy without fighting.",
                "All warfare is based on deception.",
                "He will win who knows when to fight and when not to fight.",
                "If your opponent is of choleric temper, seek to irritate him."
        };

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();

            System.out.println("Welcome to the Stratagem Typing Practice App!");
            System.out.println("You will be given a stratagem to type.");
            System.out.println("Try to type it as quickly and accurately as possible.");

            while (true) {
                // Randomly pick a stratagem to display
                String stratagem = STRATAGEMS[random.nextInt(STRATAGEMS.length)];

                // Show the stratagem to the user
                System.out.println("\nHere is your stratagem:");
                System.out.println("\"" + stratagem + "\"");

                // Start the timer
                long startTime = System.currentTimeMillis();

                // Get user input
                System.out.println("\nType the above stratagem and press Enter when done:");
                String userInput = scanner.nextLine();

                // Stop the timer
                long endTime = System.currentTimeMillis();
                long timeTaken = endTime - startTime;

                // Calculate accuracy
                int correctCharacters = countCorrectCharacters(stratagem, userInput);
                double accuracy = (double) correctCharacters / stratagem.length() * 100;

                // Display results
                System.out.println("\nResults:");
                System.out.printf("Time taken: %.2f seconds\n", timeTaken / 1000.0);
                System.out.printf("Accuracy: %.2f%%\n", accuracy);

                // Ask if the user wants to practice again
                System.out.println("\nDo you want to try again? (y/n)");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("n")) {
                    System.out.println("Thanks for practicing! Goodbye.");
                    break;
                }
            }

            scanner.close();
        }

        // Method to count the number of correct characters typed by the user
        private static int countCorrectCharacters(String stratagem, String userInput) {
            int correctCount = 0;
            for (int i = 0; i < Math.min(stratagem.length(), userInput.length()); i++) {
                if (stratagem.charAt(i) == userInput.charAt(i)) {
                    correctCount++;
                }
            }
            return correctCount;
        }
}