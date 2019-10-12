package battleship;

public class Destroyer extends Ship {

    /**
     * Initializes a new instance of the Submarine -- ship with length = 2
     */
    public Destroyer() {
        length = 2;
        hit = new boolean[2];
    }

    @Override
    public String getShipType()
    {
        return "destroyer";
    }
}
