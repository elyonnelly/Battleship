package battleship;

public class Ship {
    private int bowRow;
    private int bowColumn;
    private boolean horizontal;
    protected int length;
    protected boolean [] hit;

    /** get the number of squares occupied by the ship. An "empty sea" location has length 1.
     * @return length of ship
     */
    public int getLength() {
        return  length;
    }
    /**
     * get the row (0 to 9) which contains the bow (front) of the ship.
     * @return row where ship is located
     */
    public int getBowRow() {
        return bowRow;
    }
    /** get the column (0 to 9) which contains the bow (front) of the ship.
     * @return column where ship is located
     */
    public int getBowColumn() {
        return bowColumn;
    }
    /**get ship orientation
     * @return true if the ship occupies a single row, false otherwise.
     */
    public boolean isHorizontal() {
        return horizontal;
    }
    /**Sets the bow row of the ship
     * @param row new value of bow row
     */
    public void setBowRow(int row)
    {
        bowRow = row;
    }
    /**Sets the bow column of the ship
     * @param column new value of bow column
     */
    public void setBowColumn(int column)
    {
        bowColumn = column;
    }
    /** Set the ship orientation
     * @param horizontal new value of orientation
     */
    public void setHorizontal(boolean horizontal)
    {
        this.horizontal = horizontal;
    }
    /** Get ship type
     * @return ship name
     */
    public String getShipType()
    {
        return "abstract ship";
    }
    /**Check if part of ship can be place on this location
     * @param row row of location
     * @param column column of location
     * @param ocean space ship
     * @return Returns true if it is okay to put a part of ship of this length with its bow in this location,
     * and returns false otherwise.
     */
    private boolean okToPlacePartOfShipAt(int row, int column, Ocean ocean) {

        //TODO: проверить еще раз, правда ли это
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

    /**Check if ship can be place on this location
     * @param row bow row of the ship
     * @param column bow column of the ship
     * @param horizontal orientation of ship
     * @param ocean space ship
     * @return Returns true if it is okay to put a ship of this length with its bow in this location,
     * and returns false otherwise.
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
    //TODO: а точно ли паблик методы?
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

    /**Place ship at a certain location
     * @param row bow row of the ship
     * @param column bow column of the ship
     * @param horizontal orientation of ship
     * @param ocean space ship
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        //экшуалли мы создали экземпляр корабля иии поместили его на локацию через this.placeShipAt()
        this.setBowRow(row);
        this.setBowColumn(column);
        this.setHorizontal(horizontal);
        if (horizontal) {
            for (int i = row; i < row + this.length; i++) {
                ocean.getShipsArray()[i][column] = this;
            }
        } else {
            for (int i = column; i < column + this.length; i++) {
                ocean.getShipsArray()[row][i] = this;
            }
        }
    }

    /**Check if a part of the ship occupies the given row and column, and the ship hasn't been sunk and mark that part as "hit"
     * @param row part of ship location row
     * @param column part of ship location column
     * @return true if shoot hit at unsinked ship and false otherwise
     */
    public boolean shootAt(int row, int column)
    {
        //todo: разобраться с назначением, хех.
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

    /**
     * Check if ship sunk
     * @return true if ship sunk and false otherwise
     */
    public boolean isSunk()
    {
        for (int i = 0; i < hit.length; i++) {
            if (!hit[i]) {
                return  false;
            }
        }
        return  true;
    }

    /**This method sets the symbolic representation of the ship: "x" if the ship has been sunk, "S" if it has not been sunk.
     * @return a single-character String representation of the ship
     */
    @Override
    public String toString()
    {
        if (isSunk()) {
            return "x";
        }
        return  "S";
    }


    /** Get a symbol denoting a specific part of the ship
     * @param row part of ship location row
     * @param column part of ship location column
     * @return
     */
    //мое временное или не очень решение проблемы
    public char getShipCharacter(int row, int column) {
        if (isSunk()) {
            return 'x';
        }
        for (int i = 0; i < length; i++) {
            if (horizontal && row - bowRow == i && hit[i]
                    || !horizontal && column - bowColumn == i && hit[i]) {
                return 'S';
            }
        }
        return '.';
    }

}
