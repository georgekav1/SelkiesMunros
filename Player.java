import java.io.Serializable;

/**
 * Class which stores all details about each player in the game.
 *
 * @author (George Kavoussanakis)
 * @version (03/12/23)
 */

public class Player implements Serializable
{
    private int boardPosition;            //current position of player on board
    private boolean missTurn;             //indicates whether player should miss next turn  

    private final String name;            // name of player
    private final String marker;         //represents player on board
    private static final long serialVersionUID = 123456789L;
    /**
     * Constructs a new player with the specified name and marker.
     *
     * @param name   the name of the player
     * @param marker the marker representing the player on the game board
     */
    public Player(String name, String marker) {
        this.name = name;
        this.marker = marker;
        this.boardPosition = 0;
    }

    /**
     * Gets the current board position of the player.
     *
     * @return the board position of the player
     */
    public int getBoardPosition() {
        return boardPosition;
    }

     /**
     * Sets the board position of the player.
     *
     * @param boardPosition the new board position of the player
     */
    public void setBoardPosition(int boardPosition) {
        this.boardPosition = boardPosition;
    }
    
     /**
     * Checks whether the player should miss their next turn.
     *
     * @return {@code true} if the player should miss their turn, {@code false} otherwise
     */
     public boolean shouldMissTurn() {
        return missTurn;
    }

    /**
     * Sets whether the player should miss their next turn.
     *
     * @param missTurn {@code true} if the player should miss their turn, {@code false} otherwise
     */
    public void setMissTurn(boolean missTurn) {
        this.missTurn = missTurn;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the marker representing the player on the game board.
     *
     * @return the marker of the player
     */
    public String getMarker() {
        return marker;
    }
}

