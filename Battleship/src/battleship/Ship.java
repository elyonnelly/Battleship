package battleship;

public class Ship {
    int bowRow;
    int bowColumn;
    int length;
    boolean horizontal;
    boolean [] hit;
    int getLength() {
        return  length;
    }
    int getBowRow() {
        return bowRow;
    }
    int getBowColumn() {
        return bowColumn;
    }
    boolean isHorizontal() {
        return horizontal;
    }
    void setBowRow(int row)
    {
        bowRow = row;
    }
    void setBowColumn(int column)
    {
        bowColumn = column;
    }
    void setHorizontal(boolean horizontal)
    {
        this.horizontal = horizontal;
    }
    String getShipType()
    {
        return "abstract ship";
    }

    boolean okToPlacePartOfShipAt(int row, int column, Ocean ocean) {

        return  (ocean.getShipsArray()[row][column] instanceof EmptySea
                && ocean.getShipsArray()[Math.max(row - 1, 0)][column] instanceof EmptySea
                && ocean.getShipsArray()[Math.min(row + 1, ocean.size - 1)][column] instanceof EmptySea
                && ocean.getShipsArray()[row][Math.max(column - 1, 0)] instanceof EmptySea
                && ocean.getShipsArray()[row][Math.min(column + 1, ocean.size - 1)] instanceof EmptySea

                && ocean.getShipsArray()[Math.max(row - 1, 0)][Math.max(column - 1, 0)] instanceof EmptySea
                && ocean.getShipsArray()[Math.min(row + 1, ocean.size - 1)][Math.max(column - 1, 0)] instanceof EmptySea
                && ocean.getShipsArray()[Math.max(row - 1, 0)][Math.min(column + 1, ocean.size - 1)] instanceof EmptySea
                && ocean.getShipsArray()[Math.min(row + 1, ocean.size - 1)][Math.min(column + 1, ocean.size - 1)] instanceof EmptySea);

    }

    boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {

        if (row + length > ocean.size || column + length > ocean.size) {
            return false;
        }

        if (horizontal)
        {
            for (int i = row; i < row + length; i++)
            {
                if (!okToPlacePartOfShipAt(i, column, ocean)){
                    return  false;
                }
            }
            return true;
        }
        else
        {
            for (int i = column; i < column + length; i++)
            {
                if (!okToPlacePartOfShipAt(row, i, ocean))
                {
                    return false;
                }
            }
            return true;
        }
    }


    void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        Ship ship = new Battleship();
        if (okToPlaceShipAt(row, column, horizontal, ocean)) {
            switch (getShipType()) {
                case "battleship": {
                    ship = new Battleship();
                    break;
                }
                case "cruiser": {
                    ship = new Cruiser();
                    break;
                }
                case "destroyer": {
                    ship = new Destroyer();
                }
                case "submarine": {
                    ship = new Submarine();
                }
            }
            ship.setBowRow(row);
            ship.setBowColumn(column);
            ship.setHorizontal(horizontal);
        }
        if (horizontal) {
            for (int i = row; i < row + length; i++) {
                ocean.getShipsArray()[i][column] = ship;
            }
        } else {
            for (int i = column; i < column + length; i++) {
                ocean.getShipsArray()[row][i] = ship;
            }
        }

    }

    boolean shootAt(int row, int column)
    {
        if (horizontal && bowRow <= row && row <= bowRow + length && bowColumn == column && !hit[row - bowRow] ) {
            hit[row - bowRow] = true;
            return true;
        }
        if (!horizontal && bowColumn <= column && column <= bowColumn + length && bowRow == row && !hit[column - bowColumn]) {
            hit[column - bowColumn] = true;
            return true;
        }
        return false;
    }

    boolean isSunk()
    {
        for (int i = 0; i < hit.length; i++) {
            if (hit[i] == false) {
                return  false;
            }
        }

        return  true;
    }

    @Override
    public String toString()
    {
        if (isSunk()) {
            return "x";
        }
        return  "S";
    }

}
