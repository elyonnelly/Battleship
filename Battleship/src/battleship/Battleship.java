package battleship;

public class Battleship extends Ship {

    /**
     * Initializes a new instance of the Submarine -- ship with length = 4
     */
    public Battleship() {
        length = 4;
        hit = new boolean[4];
    }

    @Override
    public String getShipType()
    {
        return "batteship";
    }


}
