/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author KAS6570
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();

        char winner = 'N';
        int currentPlayer = 1;

        do {                        // do while loop for entirety until a winner is found
            currentPlayer = game.placeMarker(currentPlayer);    // place marker for current player, update the current player
            game.printBoard();
            if (game.validateBoard() == 'T' || game.validateBoard() == 'X' || game.validateBoard() == 'O') {
                winner = game.validateBoard();
            }
        } while (winner == 'N');

        if (winner == 'X') {
            System.out.println("Player 1 wins!");
        } else if (winner == 'O') {
            System.out.println("Computer wins!");
        } else if (winner == 'T') {
            System.out.println("It's a tie!!!");
        } else if (winner == 'N') {
            System.out.println("ERROR!!!  There's no winner!");
        }

    }

}
