package battleship;

import java.util.Scanner;

public class BattleshipGame {

    private static Ocean ocean;

    /**
     * Converts the string representation of a number to its 32-bit signed integer equivalent.
     * A return value indicates whether the conversion succeeded
     * @param value A string containing a number to convert.
     * @return Contains the integer value equivalent of the number contained in value or -1 if the conversation failed.
     */
    private static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {
            return -1;
        }
    }

    /**
     * Gets an integer from the user that can be a row or column number.
     * @param message message to user
     * @return correct entered number
     */
    private static int getInt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        int parsedInt = tryParseInt(in.nextLine());

        while (parsedInt < 0 || parsedInt >= ocean.size) {
            System.out.println("incorrect input, please repeat");
            System.out.print(message);
            parsedInt = tryParseInt(in.nextLine());
        }
        return  parsedInt;
    }

    /**
     * Clearing information from a player’s last move
     */


    private static void printInfo() {
        ocean.print();
        System.out.println("Shots fired: " + ocean.getShotsFired() + "\t" +
                "Hit count: " + ocean.getHitCount() + "\t" +
                "Ships sunk: " + ocean.getShipsSunk() + "\n" );
    }

    /** Method that implements the game process and interacts with the user
     */
    private static void playGame() {
        Scanner in = new Scanner(System.in);
        ocean.placeAllShipsRandomly();

        System.out.println("Welcome to a new game! \nNow ships are placed on this field that is an ocean.\n " +
                "There are 4 submarines, 3 destroyers, 2 cruisers and 1 battleship.\n " +
                "The submarine occupies 1 cell, the destroyer - 2, the cruiser - 3, the battleship - 4.\n " +
                "You do not know how they are located. To make a shot at some cell you must enter a row and a column.\n" +
                " The computer will say you hit or missed. The goal is to sink all the ships. \nGood luck! ");
        do {
            printInfo();

            int row = getInt("Enter row number: ");
            int column = getInt("Enter column number: ");

            if (ocean.shootAt(row, column)) {
                System.out.println("hit");
                if (ocean.getShipsArray()[row][column].isSunk()) {
                    System.out.println("You just sank a " + ocean.getShipsArray()[row][column].getShipType());
                }
                if (ocean.isGameOver()) {
                    System.out.println("You are win! Game is over! You took " + ocean.getShotsFired() + " shots\n");
                    return;
                }
            }
            else {
                System.out.println("miss");
            }
            System.out.println("Press \"enter\" to take a new shot or \"stop\" to stop the game");
        } while(!in.nextLine().equals("stop"));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        do {
            ocean = new Ocean();
            playGame();
            System.out.println("Print \"yes\" if you want to play again or any other key otherwise ");
        } while(in.nextLine().equals("yes"));
        System.out.println("Goodbye!");
    }
}
