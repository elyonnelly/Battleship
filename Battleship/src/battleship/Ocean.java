package battleship;
import com.sun.source.doctree.SummaryTree;

import java.util.Random;
import java.util.Scanner;

public class Ocean {
    private Ship[][] ships;
    private int shotsFired;
    private int hitCount;
    private int shipsSunk;

    final int size = 10;

    public Ocean() {
        ships = new Ship[size][size];
        fillOcean(new EmptySea());
        shotsFired = 0;
        hitCount = 0;
        shipsSunk = 0;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(ships[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void fillOcean(Ship ship) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ships[i][j] = ship;
            }
        }
    }

    private void placeShip(Ship ship) {
        var rnd = new Random();
        //Battleship battleship = new Battleship();
        int bowRow;
        int bowColumn;
        boolean horizontal;
        do {
            bowRow = rnd.nextInt(10);
            bowColumn = rnd.nextInt(10);
            horizontal = rnd.nextBoolean();
        } while (!ship.okToPlaceShipAt(bowRow, bowColumn, horizontal, this));

        ship.placeShipAt(bowRow, bowColumn, horizontal, this);
    }

    private void placeAllShipsRandomly() {
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

    private boolean isOccupied (int row, int column) {
        return !(ships[row][column] instanceof EmptySea);
    }

    private boolean shootAt(int row, int column) {
        shotsFired++;
        if (ships[row][column].shootAt(row, column)) {
            hitCount++;
            return true;
        }
        return false;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public int getHitCount() {
        return hitCount;
    }

    public int getShipsSunk() {
        return shipsSunk;
    }

    public boolean isGameOver() {
        return shipsSunk == 10;
    }

    Ship[][] getShipsArray() {
        return ships;
    }

}
