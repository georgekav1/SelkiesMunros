import java.io.Serial;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Serializable;

/**
 * Class which contains the main method and displays the main menu to allow the user to navigate the game.
 *
 * @author (George Kavoussanakis)
 * @version (03/12/23)
 */

public class MainMenu implements Serializable {
    private static final SelkiesAndMunros game = new SelkiesAndMunros(); // Create a single instance

    /**
     * The main method
     */
    public static void main(String[] args) {
        processChoices();
    }

    /**
     * Displays the main menu options to the user.
     */
    public static void displayMenu() {
        System.out.println("Welcome to Selkies & Munros!"); // Menu displayed for user to select choice
        System.out.println("Please select one of the options below:");
        System.out.println("1. Start New Game");
        System.out.println("2. Load Previous Game");
        System.out.println("3. Instructions");
        System.out.println("0. Exit");
    }

    /**
     * Processes the user's menu choices.
     */
    public static void processChoices() {
        int choice = -1; // Initialize choice to a non-exit value
        Scanner s = new Scanner(System.in);

        do {
            displayMenu();

            try {
                System.out.print("Enter your choice: ");
                choice = s.nextInt(); // Reads in value entered by the user

                switch (choice) {
                    case 1:
                        game.playGame();
                        break;
                    case 2:
                        game.loadGame(game);
                        break;
                    case 3:
                        SelkiesAndMunros.displayInstructions();
                        break;
                    case 0:
                        System.out.println("Exiting menu."); // Exits the menu when choice 0 is selected
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a number from 0-3."); // Error message when a number outside of 0-3 is entered into the menu
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                s.nextLine(); // Clear the buffer to avoid infinite loop
            }
        } while (choice != 0);

        s.close(); // Close the Scanner to prevent resource leaks
    }
}
