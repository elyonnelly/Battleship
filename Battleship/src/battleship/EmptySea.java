package battleship;

public class EmptySea extends Ship {

    public EmptySea() {
        length = 1;
    }
    @Override
    boolean shootAt(int row, int column) {
        return  false;
    }
    @Override
    boolean isSunk() {
        return false;
    }

}
