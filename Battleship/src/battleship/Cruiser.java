package battleship;

public class Cruiser extends Ship {

    /**
     * Initializes a new instance of the Submarine -- ship with length = 3
     */
    public Cruiser() {
        length = 3;
        hit = new boolean[3];
    }

    @Override
    public String getShipType()
    {
        return "cruiser";
    }
}
