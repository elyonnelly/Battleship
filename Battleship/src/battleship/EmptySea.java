package battleship;

public class EmptySea extends Ship {

    public EmptySea() {
        hit = new boolean[1];
        length = 1;
    }
    @Override
    public boolean shootAt(int row, int column) {
        hit[0] = true;
        return false;
    }
    @Override
    public boolean isSunk() {
        return false;
    }
    @Override
    public char getShipCharacter(int row, int column) {
        if (hit[0]) {
            return  '-';
        }
        return  '.';
    }
}
