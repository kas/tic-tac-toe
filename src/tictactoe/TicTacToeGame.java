    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

/**
 *
 * @author KAS6570
 */
public class TicTacToeGame {

    private char[][] board = new char[3][3];
    private boolean gameWon = false;
    // player 1 is X and player 2 is O
    Scanner keyboard = new Scanner(System.in);
    private int plays = 0;

    public void printBoard() {      // print board to user
        char entry = '1';
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print(entry + " " + getBoard(x, y) + "\t");
                entry += 1;
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    public int placeMarker(int player) {
        boolean planted = false;    // whether or not user has put something down yet during this turn
        int position;   // user enters position to put new mark
        char symbol = 'X';  // default symbol

        int newPlayer = -1; // for debug purposes, if program doesnt change player, this is an issue!

        if (player == 1) {  // set symbols according to which player is going
            symbol = 'X';
            newPlayer = 0;
        } else if (player == 0) {   // computer player has symbol O
            symbol = 'O';
            newPlayer = 1;
        } else if (player == 2) {   // player 2 has symbol O
            symbol = 'O';
            newPlayer = 1;
        }

        do {    // check if position on board is taken up already
            if (player != 0) {  // collect user input, redo if user doesn't input correct number
                do {    // correct input test
                    System.out.print("Where would Player " + player + " like to mark?: ");
                    position = keyboard.nextInt();
                } while (position <= 0 || position >= 10);
            } else {    // give computer a random number to play (between 1 and 9) AND try to guess spot (play 3rd mark after it finds 2 marks in a same line)
                position = ThreadLocalRandom.current().nextInt(1, 9 + 1);
                System.out.println("Computer tries " + position);

                for (int i = 0; i < 3; i++) {
                    if (getBoard(i, 0) == getBoard(i, 1)
                            && getBoard(0, i) == 'O') {
//                System.out.println("ROWS Setting to " + getBoard(i, 0));
                        setBoard(i, 2, symbol);
                        // NEED TO evaluate if there is a mark already here
                        return newPlayer;
                    }
                }
                for (int i = 0; i < 3; i++) {
                    if (getBoard(0, i) == getBoard(1, i)
                            && getBoard(0, i) == 'O') {
//                System.out.println("COLUMNS Setting to " + getBoard(0, i));
                        setBoard(2, i, symbol);
                        // NEED TO evaluate if there is a mark already here
                        return newPlayer;
                    }
                }
                if (getBoard(0, 0) == getBoard(1, 1)
                        && getBoard(0, 0) == 'O') {
//            System.out.println("DIAGONALS 1 Setting to " + getBoard(0, 0));
                    setBoard(2, 2, symbol);
                        // NEED TO evaluate if there is a mark already here
                    return newPlayer;
                }
                if (getBoard(2, 0) == getBoard(1, 1)
                        && getBoard(2, 0) == 'O') {
//            System.out.println("DIAGONALS 2 Setting to " + getBoard(2, 0));
                    setBoard(0,2,symbol);
                    return newPlayer;
                }
            }

            int ctr = 0; // counter variable
            loop:   // label to break out of loop
            for (int y = 0; y < 3; y++) {           // iterate through y axis
                for (int x = 0; x < 3; x++) {       // iterate through x axis
                    ctr++;
                    if (ctr == position) {
                        if (getBoard(x, y) != 'X' && getBoard(x, y) != 'O') {   // check if position already has a marker
                            setBoard(x, y, symbol);
                            planted = true;
                            setPlays(getPlays() + 1);
                            break loop;
                        } else {    // position DOES have a marker in it already
                            System.out.println("SPACE OCCUPIED!!!  Try again!");
                            break loop;
                        }
                    }
                }
            }
        } while (!planted); // let user type in another position if position was taken up

        return newPlayer; // return the next player
    }

    public char validateBoard() {
        // check rows
        for (int i = 0; i < 3; i++) {
            if (getBoard(i, 0) == getBoard(i, 1)
                    && getBoard(i, 1) == getBoard(i, 2)
                    && getBoard(0, i) != 0) {
//                System.out.println("ROWS Setting to " + getBoard(i, 0));
                return getBoard(i, 0);
            }
        }

        // check columns
        for (int i = 0; i < 3; i++) {
            if (getBoard(0, i) == getBoard(1, i)
                    && getBoard(1, i) == getBoard(2, i)
                    && getBoard(0, i) != 0) {
//                System.out.println("COLUMNS Setting to " + getBoard(0, i));
                return getBoard(0, i);
            }
        }
        // check diagonal
        if (getBoard(0, 0) == getBoard(1, 1)
                && getBoard(1, 1) == getBoard(2, 2)
                && getBoard(0, 0) != 0) {
//            System.out.println("DIAGONALS 1 Setting to " + getBoard(0, 0));
            return getBoard(0, 0);
        }
        // check OTHER diagonal
        if (getBoard(2, 0) == getBoard(1, 1)
                && getBoard(1, 1) == getBoard(0, 2)
                && getBoard(2, 0) != 0) {
//            System.out.println("DIAGONALS 2 Setting to " + getBoard(2, 0));
            return getBoard(2, 0);
        }

        // check if it's a tie or not (no more than 9 moves or the board is full)
        if (getPlays() >= 9) {
//            System.out.println("getPlays >= 9 Setting to T");
            return 'T';         // no winner, it's a tie, board is full
        }

        //DEBUG
//        System.out.println("NO winner Setting to N");
        //
        return 'N';   // No winner yet
    }

    /**
     * @return the board
     */
    public char getBoard(int x, int y) {
        return this.board[y][x];
    }

    /**
     * @param board the board to set
     */
    public void setBoard(int x, int y, char symbol) {
        this.board[y][x] = symbol;
    }

    /**
     * @return the gameWon
     */
    public boolean isGameWon() {
        return gameWon;
    }

    /**
     * @param gameWon the gameWon to set
     */
    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    /**
     * @return the plays
     */
    public int getPlays() {
        return plays;
    }

    /**
     * @param plays the plays to set
     */
    public void setPlays(int plays) {
        this.plays = plays;
    }
}
