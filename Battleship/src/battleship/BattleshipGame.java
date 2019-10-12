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
        int parsedInt = tryParseInt(in.next());
        while (parsedInt < 0 || parsedInt >= ocean.size) {
            System.out.println("incorrect input, please repeat");
            System.out.print(message);
            parsedInt = tryParseInt(in.next());
        }
        return  parsedInt;
    }

    /**
     * Clearing information from a player’s last move
     */
    private  static void clearScreen() {
        //не то чтобы работает, да...
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /** Method that implements the game process and interacts with the user
     */
    private static void playGame() {
        Scanner in = new Scanner(System.in);
        do {
            clearScreen();
            ocean.print();
            System.out.println("Shots fired: " + ocean.getShotsFired() + "\t" +
                                "Ships sunk: " + ocean.getShipsSunk() + "\n" +
                                "-----------------------\n" +
                                "Hit count: " + ocean.getHitCount());

            //может просить вводить через пробел?
            int row = getInt("Enter row number: ");
            int column = getInt("Enter column number: ");

            //ohhhh wellllll. И зачем казалось бы вообще ocean?
            /*if (ocean.shootAt(row, column)) {
                System.out.println("hit");
            }*/
            if (ocean.getShipsArray()[row][column].shootAt(row, column)) {
                System.out.println("hit");
            }
            else {
                System.out.printf("miss");
            }
            if (ocean.getShipsArray()[row][column].isSunk()) {
                System.out.println("You just sank a " + ocean.getShipsArray()[row][column].getShipType());
            }
            System.out.println("Enter any key for continue...");
            in.nextLine();
        } while(!ocean.isGameOver());
    }

    public static void main(String[] args) {
        ocean = new Ocean();
        playGame();
    }
}
