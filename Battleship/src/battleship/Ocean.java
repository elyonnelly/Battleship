package battleship;

import java.sql.SQLOutput;
import java.util.Random;

public class Ocean {
    public final int size = 10;

    private Ship[][] ships;
    //общее кол-во выстрелов пользователя
    private int shotsFired;
    //количество попаданий. сколько раз выстрел попал в корабль (учитываются даже повторные попадания в одно и то же место)
    private int hitCount;
    //количество потопленных кораблей
    private int shipsSunk;

    /**
     * Initializes a new instance of the Ocean class and its fields before starting the game
     */
    public Ocean() {
        ships = new Ship[size][size];
        fillOcean();
        shotsFired = 0;
        hitCount = 0;
        shipsSunk = 0;
    }

    /**
     * Print information about current state battlefield
     *
     * Use 'S' to indicate a location that you have fired upon and hit a (real) ship,
     * '-' to indicate a location that you have fired upon and found nothing there,
     * 'x' to indication location containing a sunken ship,
     * and '.' to indicate a location that you have never fired upon.
     */
    public void print() {
        for (int i = 0; i < size + 1; i++) {
            if (i == 0) {
                System.out.print("\t");
                for (int j = 0; j < size; j++) {
                    System.out.print(j + "\t");
                }
                System.out.println();
                continue;
            }
            System.out.print(i - 1 + "\t");
            for (int j = 0; j < size; j++) {
                System.out.print(ships[i - 1][j].getShipCharacter(i - 1, j) + "\t");
            }
            System.out.println();
        }
    }

    public void printAllShip() {
        for (int i = 0; i < size + 1; i++) {
            if (i == 0) {
                System.out.print("\t");
                for (int j = 0; j < size; j++) {
                    System.out.print(j + "\t");
                }
                System.out.println();
                continue;
            }
            System.out.print(i - 1 + "\t");
            for (int j = 0; j < size; j++) {
                if (ships[i - 1][j] instanceof EmptySea) {
                    System.out.print(".\t");
                }
                else {
                    System.out.print("S\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * Fill the ocean with EmptySea class instances
     */
    private void fillOcean() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ships[i][j] = new EmptySea();
                ships[i][j].setBowRow(i);
                ships[i][j].setBowColumn(j);
                ships[i][j].setHorizontal(false);
            }
        }
    }

    /**
     * Place ship on random position on the battlefield
     * @param ship
     */
    public void placeShip(Ship ship) {
        var rnd = new Random();
        int bowRow;
        int bowColumn;
        boolean horizontal;
        do {
            bowRow = rnd.nextInt(10);
            bowColumn = rnd.nextInt(10);
            horizontal = rnd.nextBoolean();
        } while (!ship.okToPlaceShipAt(bowRow, bowColumn, horizontal, this));

        //ну как-то так да лол. а я умнее, чем я думала
        ship.placeShipAt(bowRow, bowColumn, horizontal, this);
    }

    /**
     * Place all ships randomly. To be precise, place 1 battleship, 2 cruisers, 3 destroyers, 4 submarines.
     */
    public void placeAllShipsRandomly() {
        placeShip(new Battleship());

        for (int i = 0; i < 2; i++) {
            placeShip(new Cruiser());
        }

        for (int i = 0; i < 3; i++) {
            placeShip(new Destroyer());
        }

        for (int i = 0; i < 4; i++) {
            placeShip(new Submarine());
        }
    }

    /**
     * check if location ({@code row}, {@code column}) contains a real ship
     * @param row - row on table
     * @param column - column on table
     * @return Returns true if the given location contains a ship, false if it does not.
     */
    public boolean isOccupied (int row, int column) {
        return !(ships[row][column] instanceof EmptySea);
    }

    /**Check if shot hit a real ship.
     * In addition, this method updates the number of shots that have been fired, and the number of hits.
     * @param row - row on table
     * @param column - column on table
     * @return Returns true if the given location contains a "real" ship,
     * still afloat, (not an EmptySea), false if it does not.
     */
    public boolean shootAt(int row, int column) {
        shotsFired++;
        if (ships[row][column].shootAt(row, column)) {
            hitCount++;
            if (ships[row][column].isSunk()) {
                shipsSunk++;

            }
            return true;
        }
        return false;
    }

    /**
     * Get the total number of shots fired by the user.
     * @return the number of shots fired (in this game).
     */
    public int getShotsFired() {
        return shotsFired;
    }

    /** Get the number of times a shot hit a ship. If the user shoots the same part of a ship more than once,
     * every hit is counted, even though the additional "hits" don't do the user any good.
     * @return the number of hits recorded (in this game).
     */
    public int getHitCount() {
        return hitCount;
    }

    /** Get the number of ships sunk (10 ships in all).
     * @return the number of ships sunk (in this game).
     */
    public int getShipsSunk() {
        return shipsSunk;
    }

    /** Checks if game is finished and all ships is sunk
     * @return true is game is finished and false otherwise
     */
    public boolean isGameOver() {
        return shipsSunk == 10;
    }

    /**
     * Get array storing links to ships according to their location on the battlefield
     * @return two-dimensional array with ships
     */
    public Ship[][] getShipsArray() {
        return ships;
    }

}
