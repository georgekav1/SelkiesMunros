import java.io.Serializable;

/**
 * Class which stores all details about the game board and items upon it.
 *
 * @author (George Kavoussanakis)
 * @version (03/12/23)
 */

public class Board implements Serializable
{
    public int cellCount;                    //total number of cells on the board

    private BoardPosition[] board;           //array of BoardPosition representing each position on the board
    private static final long serialVersionUID = 123456789L;
    /**
     * Constructs a new game board with a default cell count of 100 and initializes the board.
     */
    public Board() {
        this.cellCount = 100;
        board = new BoardPosition[cellCount];
        generateBoard();
    }

    /**
     * Generates the initial configuration of the game board, including setting up items
     * such as Selkies, Munros, Haggis, and Bagpipes.
     */
    private void generateBoard() {
        initialiseBoard();
        setupSelkies();
        setupMunros();
        setupHaggis();
        setupBagpipes();
    }

     /**
     * Initializes each board position on the game board.
     */
    private void initialiseBoard() {
        for (int i = 0; i < cellCount; i++) {
            BoardPosition boardPosition = new BoardPosition(i + 1);
            board[i] = boardPosition;
        }
    }

    /**
     * Sets up the positions of Selkies on the game board.
     */
    private void setupSelkies() {
        int[] position = {99, 92, 81, 51, 49, 37, 73};
        int[] selkiePosition = {45, 76, 24, 47, 23, 29, 41};

        for (int i = 0; i < position.length; i++) {
            BoardPosition boardPosition = board[position[i] - 1];
            boardPosition.setSelkiePosition(selkiePosition[i]);
        }
    }

     /**
     * Sets up the positions of Munros on the game board.
     */
    private void setupMunros() {
        int[] position = {82, 55, 33, 19, 16,  9, 4};
        int[] munroPosition = {97, 63, 83, 80, 25, 39, 14};

        for (int i = 0; i < position.length; i++) {
            BoardPosition boardPosition = board[position[i] - 1];
            boardPosition.setMunroPosition(munroPosition[i]);
        }
    }
    
     /**
     * Sets up the positions of Haggis on the game board.
     */
    private void setupHaggis() {
        int[] position = {13, 39, 58};
        int[] haggisPosition = {13, 39, 58};
        for (int i = 0; i < position.length; i++) {
        BoardPosition boardPosition = board[position[i] - 1];
        boardPosition.setHaggisPosition(haggisPosition[i]);
        }
    }
    
     /**
     * Sets up the positions of Bagpipes on the game board.
     */
    private void setupBagpipes() {
        int[] position = {77, 62, 95};
        int[] bagpipePosition = {77, 62, 95};

        for (int i = 0; i < position.length; i++) {
        BoardPosition boardPosition = board[position[i] - 1];
        boardPosition.setBagpipePosition(bagpipePosition[i]);
        }
    }

    /**
     * Gets the array of board positions on the game board.
     *
     * @return an array of {@link BoardPosition} objects representing the game board
     */
    public BoardPosition[] getBoard() {
        return board;
    }

    /**
     * Gets the total number of cells on the game board.
     *
     * @return the cell count of the game board
     */
    public int getCellCount() {
        return cellCount;
    }
}