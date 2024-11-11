import java.util.Scanner;

import java.util.Arrays;
import java.util.Comparator;
import java.io.*;

/**
 * Class that is executed when user selects 1 of the 3 options from the main menu.
 *
 * @author (George Kavoussanakis)
 * @version (03/12/23)
 */

public class SelkiesAndMunros implements Serializable
{
    private static final String filename = "savedgame.txt";  // File to store saved game data
    private Player[] players;                                // Array to store player objects
    private Board board;                                     // Board object representing the game board
    private int finalPosition = 100;                         // Constants for game board size and final position
    //private int cellCount = 100;
    private static final long serialVersionUID = 6769829250639411880L;
    
    /**
     * Gets the game board.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }
    
    /**
     * Sets the game board.
     *
     * @param board The game board to set.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets the array of players.
     *
     * @return The array of players.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Sets the array of players.
     *
     * @param players The array of players to set.
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Gets the number of players in the game.
     *
     * @return The number of players.
     */
    public int getNumberOfPlayers() {
        return players.length;
    }
    
     /**
     * Saves the current game state to a file.
     *
     * @param players The array of players.
     * @param board   The game board.
     */
    private void saveGame(Player[] players, Board board) {
        System.out.println("Saving game...");
        try (ObjectOutputStream details = new ObjectOutputStream(new FileOutputStream(filename))) {
            details.writeObject(getPlayers());
            details.writeObject(getBoard());
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }
    
    /**
     * Loads a saved game state from a file.
     *
     * @param game The SelkiesAndMunros object to load the game into.
     */
    public void loadGame(SelkiesAndMunros game) {
        System.out.println("Loading game...");
        try (ObjectInputStream readIn = new ObjectInputStream(new FileInputStream(filename))) {
            players = (Player[]) readIn.readObject();
            board = (Board) readIn.readObject();
            
            game.setPlayers(players);
            game.setBoard(board);     
        
            System.out.println("Game loaded successfully."); 
            playLoadedGame(game);
        } catch (FileNotFoundException e) {
            System.out.println("No saved game found. Starting a new game.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Not working.");
        }
    }

    /**
     * Plays the loaded game.
     *
     * @param game The SelkiesAndMunros object representing the loaded game.
     */
    public void playLoadedGame(SelkiesAndMunros game) {
        Scanner sc = new Scanner(System.in);
        String retry = "y";
        do {    
            players = game.getPlayers();                            //Setup game
            board = game.getBoard();
            
            boolean isGameActive = true;
            while (isGameActive) {
                waitForDisplay(sc);
                displayBoard(board, players);
                displayPlayers(players);
                isGameActive = playRound(sc, board, players);
                
                String command;
                boolean validCommand;
                do {                                                //user command handling 
                    command = promptForCommand(sc);
                    switch (command.toLowerCase()) {
                        case "y":
                        validCommand = true;
                        break;
                        
                        case "s":
                        saveGame(players, board);
                        System.out.println("Game saved successfully.");
                        isGameActive = false;
                        validCommand = true;
                        break;
                        
                        case "x":
                        isGameActive = false;
                        validCommand = true;
                        break;
                        
                        default:
                        System.out.println("Invalid command. Please enter 'y', 's', or 'x'.");
                        validCommand = false;
                        break;
                    }
                } while(!validCommand); 
            }
            retry = getPlayAgainResponse(sc);
        } while ("y".equals(retry));
        System.out.println("Thank you for playing.");
        sc.close();
    }    
    
    /**
     * Plays the new game.
     */
    public void playGame() {
        Scanner sc = new Scanner(System.in);
        String retry = "y";
        do {    
            players = getPlayers(sc);
            board = new Board();
            
            boolean isGameActive = true;
            while (isGameActive) {
                waitForDisplay(sc);
                displayBoard(board, players);
                displayPlayers(players);
                isGameActive = playRound(sc, board, players);
                
                String command;
                boolean validCommand;
                do {
                    command = promptForCommand(sc);
                    switch (command.toLowerCase()) {
                        case "y":
                        validCommand = true;
                        break;
                        
                        case "s":
                        saveGame(players, board);
                        System.out.println("Game saved successfully.");
                        validCommand = true;
                        isGameActive = false;
                        break;
                        
                        case "x":
                        isGameActive = false;
                        validCommand = true;
                        break;
                        
                        default:
                        System.out.println("Invalid command. Please enter 'y', 's', or 'x'.");
                        validCommand = false;
                        break;
                    }
                } while(!validCommand); 
            }
            retry = getPlayAgainResponse(sc);
        } while ("y".equals(retry));
        System.out.println("Thank you for playing.");
        sc.close();
    }

    /**
     * Displays game instructions.
     */
    public static void displayInstructions() {     
        System.out.println("Welcome to Selkies & Munros!");
        System.out.println("");
        System.out.println("- This is a 2-4 player game.");
        System.out.println("- There will be a 10x10 grid containing some Selkies, Munros & Mystery squares.");
        System.out.println("- The players will take turns to roll the dice in a randomly decided order.");
        System.out.println("- The player will move advance corresponding to the number rolled.");
        System.out.println("- When a player lands on a Munro, they will be able to climb it and advance further!");
        System.out.println("- When a player lands on a Selkie, they will have to slide down and lose their progress!");
        System.out.println("- When a player lands on a Haggis Bite, they miss a turn next round.");
        System.out.println("- When a player lands on Bagpiper's Delight, they get to roll again!");
        System.out.println("- The game ends when a player reaches square 100.");
        System.out.println("- You can exit and save the game or exit to main menu.");
        System.out.println("");
    }

    /**
     * Gets the number of players and their names.
     *
     * @param sc The Scanner object for input.
     * @return An array of Player objects.
     */
    private Player[] getPlayers(Scanner sc) {
        int numberOfPlayers;
        while (true) {
            System.out.println("");
            System.out.println("Please enter the number of players:");
            numberOfPlayers = sc.nextInt();
            sc.nextLine();
        
            if (numberOfPlayers >= 2 && numberOfPlayers <= 4) {
            break;                           // Exit the loop if the input is valid
            } else {
            System.out.println("Invalid number of players. Please enter a value from 2-4.");
            }
        }

        players = new Player[numberOfPlayers];
        String[] markers = { "\u2460", "\u2461", "\u2462", "\u2463" };

        for (int i = 0; i < numberOfPlayers; i++) {
        System.out.println("Enter Player " + (i+1) + "'s name: ");
        String name = sc.nextLine().trim();
        Player player = new Player(name, markers[i]);
        players[i] = player;
        }

        return players;
    }

    /**
     * Performs a round of the game for each player.
     *
     * @param sc     The Scanner object for input.
     * @param board  The game board.
     * @param players The array of players.
     * @return True if the game is still active, false if a player has won.
     */
    private boolean playRound(Scanner sc, Board board, Player[] players) {
        for (int turn = 0; turn < players.length; turn++) {
            Player player = players[turn];            
            if (player.shouldMissTurn()){
                player.setMissTurn(false);
                continue;
            }
            
            int die = getDieRoll(sc, player, turn);
            int position = player.getBoardPosition();
            position += die;
            
            if (position == finalPosition) {
                declareWinner(player, position, players);
                return false;
            } else if (position > finalPosition) {
                displayOvershoot();
            } else {
                movePlayer(board, player, position, sc, turn);
            }
        }
        return true;
    }
    
    /**
     * Prompts the user for a command (continue, save, or exit).
     *
     * @param sc The Scanner object for input.
     * @return The user's command.
     */
    private String promptForCommand(Scanner sc) {
        System.out.println("Enter your command ('y' to continue, 's' to exit & save the game, 'x' to exit to main menu:");
        return sc.nextLine().trim();
    }
    
    /**
     * Declares the winner of the game and displays the final standings.
     *
     * @param player  The winning player.
     * @param position The final position of the winning player.
     * @param players  The array of players.
     */
    private void declareWinner(Player player, int position, Player[] players) {
        System.out.println(player.getName() + " Won!!");
        System.out.println("Here are the final standings:");
        
        Arrays.sort(players, Comparator.comparingInt(Player::getBoardPosition).reversed());

        for (int i = 0; i < players.length; i++)
        {
            System.out.println((i+1) + ". " + players[i].getName() + " - square " + players[i].getBoardPosition());
        }
    }

    /**
     * Displays a message for overshooting the final position.
     */
    private void displayOvershoot() {
        System.out.println("You can't advance that many spaces.");
        System.out.println("Stay where you are.");
    }

    /**
     * Moves a player on the board based on the dice roll and the board position.
     *
     * @param board   The game board.
     * @param player  The current player.
     * @param position The new position after the dice roll.
     * @param sc      The Scanner object for input.
     * @param turn    The current turn index.
     */
    private void movePlayer(Board board, Player player, int position, Scanner sc, int turn) {
        BoardPosition[] squares = board.getBoard();
        BoardPosition square = squares[position - 1];
        if (square.isSelkie()) {
            displayPosition(position,player);
            displaySelkiePosition(square);
            player.setBoardPosition(square.getSelkiePosition());
        } else if (square.isMunro()) {
            displayPosition(position,player);
            displayMunroPosition(square);
            player.setBoardPosition(square.getMunroPosition());
        } else if (square.isHaggis()) {
            displayPosition(position,player);
            displayHaggisPosition(square);
            player.setBoardPosition(square.getHaggisPosition());
            player.setMissTurn(true);
        } else if (square.isBagpipe()) {
            displayPosition(position, player);
            displayBagpipePosition(square);
            int newRoll = getDieRoll(sc, player, turn); // Add a new roll
            int newPosition = player.getBoardPosition() + newRoll;
            movePlayer(board, player, newPosition, sc, turn);
        }else {
            displayPosition(position, player);
            player.setBoardPosition(position);
        }
    }

    /**
     * Displays information when a player lands on a Selkie.
     *
     * @param square The BoardPosition representing the Selkie.
     */
    private void displaySelkiePosition(BoardPosition square) {
        System.out.println("You landed on a Selkie!");
        System.out.println("You slip to " + square.getSelkiePosition());
    }

     /**
     * Displays information when a player lands on a Munro.
     *
     * @param square The BoardPosition representing the Munro.
     */
    private void displayMunroPosition(BoardPosition square) {
        System.out.println("You landed on a Munro!");
        System.out.println("You climb to " + square.getMunroPosition());
    }
    
    /**
     * Displays information when a player lands on a Haggis Bite.
     *
     * @param square The BoardPosition representing the Haggis Bite.
     */
    private void displayHaggisPosition(BoardPosition square) {
        System.out.println("You landed on a Haggis Bite!");
        System.out.println("You miss a turn.");
    }
    
    /**
     * Displays information when a player lands on Bagpiper's Delight.
     *
     * @param square The BoardPosition representing Bagpiper's Delight.
     */
    private void displayBagpipePosition(BoardPosition square) {
        System.out.println("You landed on Bagpiper's Delight!");
        System.out.println("You get to roll again!");
    }

    /**
     * Displays the player's current position on the board.
     *
     * @param position The player's current position.
     * @param player   The current player.
     */
    private void displayPosition(int position, Player player) {
        System.out.println(player.getName() + " landed on square " + position);
    }

    /**
     * Rolls the dice and returns the result.
     *
     * @param sc     The Scanner object for input.
     * @param player The current player.
     * @param turn   The current turn index.
     * @return The result of the dice roll.
     */
    private int getDieRoll(Scanner sc, Player player, int turn) 
    {
        System.out.println();
        System.out.println("It is " + player.getName() + "'s turn ");
        System.out.println("Press Enter to roll the dice:");
        sc.nextLine();

        // Generate a number between 1 & 6
        int die = (int) (Math.random() * 6 + 1);
        System.out.println(player.getName() + " rolled a " + die + ".");
        return die;
    }
 
    /**
     * Prompts the user to play again or return to the main menu.
     *
     * @param sc The Scanner object for input.
     * @return The user's response.
     */
    private String getPlayAgainResponse(Scanner sc) {
        System.out.println();
        System.out.println("Enter any key to display menu.");  
        return sc.nextLine().trim();
    }

    /**
     * Waits for the user to press Enter before displaying the board.
     *
     * @param sc The Scanner object for input.
     */
    private void waitForDisplay(Scanner sc) {
        System.out.println();
        System.out.print("Press Enter to display board: ");
        sc.nextLine();
    }

    /**
     * Displays corresponding marker to each player.
     *
     * @param players The array of players.
     */
    private void displayPlayers(Player[] players) {
        System.out.println();
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            String marker = player.getMarker();
            String name = player.getName();
            System.out.println("Player " + marker + " --> " + name); 
        }
    }

    /**
     * Displays the game board.
     *
     * @param board   The game board.
     * @param players The array of players.
     */
    private void displayBoard(Board board, Player[] players) {
        int cellWidth = 16;
        //int finalPosition = board.getCellCount();
        int cellCount = board.getCellCount();
        int cells = (int) Math.sqrt(cellCount);
        displayDashLine(cellWidth, cells);

        for (int i = 0; i < cells; i += 2) {
            cellCount = displayCells(board, players, cells, cellWidth, cellCount, -1);
            displayDashLine(cellWidth, cells);
            cellCount = displayCells(board, players, cells, cellWidth, cellCount, +1);
            displayDashLine(cellWidth, cells);
        }
    }

    /**
     * Displays a line of dashes to separate rows on the game board.
     *
     * @param cellWidth The width of each cell on the board.
     * @param cells     The number of cells in a row.
     */
    private void displayDashLine(int cellWidth, int cells) {
        int width = cellWidth * cells + 1;
        for (int dash = 1; dash <= width; dash++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Displays cells on the game board.
     *
     * @param board     The game board.
     * @param cells     The number of cells in a row.
     * @param cellWidth The width of each cell on the board.
     * @param increment The increment value for position calculation.
     * @param cellCount     The number of cells on the board.
     */
    private int displayCells(Board board, Player[] players, int cells, int cellWidth, int cellCount, int increment) {
        int temp = calculateStartingCell(cells, cellCount, increment);
        displayPositionNumber(board, cells, cellWidth, increment, temp);

        temp = calculateStartingCell(cells, cellCount, increment);
        displayPositionText(board, cells, cellWidth, increment, temp);

        temp = calculateStartingCell(cells, cellCount, increment);
        displayPositionPlayer(board, players, cells, cellWidth, increment, temp);

        return cellCount - cells;   
    }

    /**
     * Displays the numbers corresponding to the positions on the game board.
     *
     * @param board     The game board.
     * @param cells     The number of cells in a row.
     * @param cellWidth The width of each cell on the board.
     * @param increment The increment value for position calculation.
     * @param temp      The starting position for display.
     */
    private void displayPositionNumber(Board board, int cells, int cellWidth, int increment, int temp) {
        for (int i = 0; i < cells; i++) {
            temp += increment;
            BoardPosition boardPosition = board.getBoard()[temp];

            if (i == 0) {
                System.out.print("|");
            }

            int position = boardPosition.getPosition();
            String text = Integer.toString(position);
            String s = generateTextLine(text, cellWidth);
            System.out.print(s);
        }
        System.out.println();
    }

    /**
     * Displays text indicating the type of each position on the game board.
     *
     * @param board     The game board.
     * @param cells     The number of cells in a row.
     * @param cellWidth The width of each cell on the board.
     * @param increment The increment value for position calculation.
     * @param temp      The starting position for display.
     */
    private void displayPositionText(Board board, int cells, int cellWidth, int increment, int temp) {
        for (int i = 0; i < cells; i++) {
            temp += increment;
            BoardPosition boardPosition = board.getBoard()[temp];

            if (i == 0) {
                System.out.print("|");
            }

            String text = "";
            if (boardPosition.isSelkie()) {
                text = boardPosition.getSelkiePositionText();
            } else if (boardPosition.isMunro()) {
                text = boardPosition.getMunroPositionText();
            }else if (boardPosition.isHaggis()) {
                text = boardPosition.getHaggisPositionText();
            }else if (boardPosition.isBagpipe()) {
                text = boardPosition.getBagpipePositionText(); 
            }    
            String s = generateTextLine(text, cellWidth);
            System.out.print(s);
        }
        System.out.println("");
    }

    /**
     * Displays markers for players on the game board.
     *
     * @param board     The game board.
     * @param players   The array of players.
     * @param cells     The number of cells in a row.
     * @param cellWidth The width of each cell on the board.
     * @param increment The increment value for position calculation.
     * @param temp      The starting position for display.
     */
    private void displayPositionPlayer(Board board, Player[] players, int cells, int cellWidth, int increment, int temp) {
        for (int i = 0; i < cells; i++) {
            temp += increment;

            if (i == 0) {
                System.out.print("|");
            }

            String text = "";
            for (int j = 0; j < getNumberOfPlayers(); j++) {
                Player player = players[j];
                if (player.getBoardPosition() == (temp + 1)) {
                    text += player.getMarker() + "  ";
                }
            }
            text = text.trim();
            String s = generateTextLine(text, cellWidth);
            System.out.print(s);
        }
        System.out.println();
    }

    /**
     * Calculates the starting position for display.
     *
     * @param cells     The number of cells in a row.
     * @param cellCount The total number of cells on the board.
     * @param increment The increment value for position calculation.
     * @return The starting position for display.
     */
    private int calculateStartingCell(int cells, int cellCount, int increment) {
        int temp = cellCount;
            if (increment > 0) {
            temp -= cells + 1;
        }
        return temp;
    }

     /**
     * Generates a formatted text line for representing a column on the game board.
     *
     * @param text      The text to be displayed.
     * @param cellWidth The width of each cell on the board.
     * @return The formatted text line.
     */
    private String generateTextLine(String text, int cellWidth) {
        String output = "";
 
        int spaces = (cellWidth - text.length()) / 2;
        output += createBlankString(spaces);

        output += text;

        int width = cellWidth - spaces - text.length() - 1;
        output += createBlankString(width);

        output += "|";
        return output;
    }

    /**
     * Creates a blank string for an empty cell.
     *
     * @param width The width of the blank string.
     * @return The blank string.
     */
    private String createBlankString(int width) {
        String output = "";
        for (int i = 0; i < width; i++) {
            output += " ";
        }
        return output;
    }
}
