import java.io.Serializable;

/**
 * Class which stores all details about the position of items on the board.
 *
 * @author (George Kavoussanakis)
 * @version (03/12/23)
 */

public class BoardPosition implements Serializable 
{
    private final int position;
    private int munroPosition;
    private int selkiePosition;
    private int haggisPosition;
    private int bagpipePosition;
    private static final long serialVersionUID = 123456789L;

    /**
     * Constructor for BoardPosition class.
     *
     * @param position The position on the board.
     */
    public BoardPosition(int position) {
        this.position = position;
        this.munroPosition = 0;
        this.selkiePosition = 0;
        this.haggisPosition = 0;
        this.bagpipePosition = 0;
    }

    /**
     * Gets the Munro's position.
     *
     * @return The Munro's position.
     */
    public int getMunroPosition() {
        return munroPosition;
    }

    /**
     * Gets formatted text for displaying Munro's position.
     *
     * @return Formatted text for Munro's position.
     */
    public String getMunroPositionText() {
        return "\u26F0"+">"+ munroPosition;
    }

    /**
     * Sets the Munro's position.
     *
     * @param munroPosition The new Munro's position.
     */
     public void setMunroPosition(int munroPosition) {
        this.munroPosition = munroPosition;
    }

    /**
     * Gets the Selkie's position.
     *
     * @return The Selkie's position.
     */

    public int getSelkiePosition() {
        return selkiePosition;
    }

    /**
     * Gets formatted text for displaying Selkie's position.
     *
     * @return Formatted text for Selkie's position.
     */
    public String getSelkiePositionText() {
        return "\u2621"+">"+ selkiePosition;
    }
    
    /**
     * Sets the Selkie's position.
     *
     * @param selkiePosition The new Selkie's position.
     */
    public void setSelkiePosition(int selkiePosition) {
        this.selkiePosition = selkiePosition;
    }
    
    /**
     * Gets the Haggis's position.
     *
     * @return The Haggis's position.
     */
    public int getHaggisPosition() {
        return haggisPosition;
    }

    /**
     * Gets formatted text for displaying Haggis's position.
     *
     * @return Formatted text for Haggis's position.
     */
    public String getHaggisPositionText() {
        return "\u003F";
    }

    /**
     * Sets the Haggis Bite's position.
     *
     * @param haggisPosition The new Haggis Bite's position.
     */
     public void setHaggisPosition(int haggisPosition) {
        this.haggisPosition = haggisPosition;
    }
    
    /**
     * Gets the Bagpipe's position.
     *
     * @return The Bagpipe's position.
     */
    public int getBagpipePosition() {
        return bagpipePosition;
    }

    /**
     * Gets formatted text for displaying Bagpipe's position.
     *
     * @return Formatted text for Bagpipe's position.
     */
    public String getBagpipePositionText() {
        return "\u003F";
    }

     /**
     * Sets the Bagpipe's position.
     *
     * @param bagpipePosition The new Bagpipe's position.
     */
     public void setBagpipePosition(int bagpipePosition) {
        this.bagpipePosition = bagpipePosition;
    }

    /**
     * Checks if there is a Munro on this position.
     *
     * @return True if there is a Munro, false otherwise.
     */
    public boolean isMunro() {
        return munroPosition > 0;
    }

     /**
     * Checks if there is a Selkie on this position.
     *
     * @return True if there is a Selkie, false otherwise.
     */
    public boolean isSelkie() {
        return selkiePosition > 0;
    }
    
    /**
     * Checks if there is a Haggis on this position.
     *
     * @return True if there is a Haggis, false otherwise.
     */
    public boolean isHaggis() {
        return haggisPosition > 0;
    }
    
    /**
     * Checks if there is a Bagpipe on this position.
     *
     * @return True if there is a Bagpipe, false otherwise.
     */
    public boolean isBagpipe() {
        return bagpipePosition > 0;
    }

    /**
     * Gets the position on the board.
     *
     * @return The position on the board.
     */
    public int getPosition() {
        return position;
    }
}

