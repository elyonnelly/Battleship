package battleship;

public class Submarine extends Ship {

    /**
     * Initializes a new instance of the Submarine -- ship with length = 1
     */
    public Submarine() {
        length = 1;
        hit = new boolean[1];
    }

    @Override
    public String getShipType()
    {
        return "submarine";
    }
}
