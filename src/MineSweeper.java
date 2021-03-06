
import javax.swing.*;
import java.awt.*;

/**
 * This is the Main class for my Mine Sweeper game. This class is responsible
 * for logic that needs information from or affects all the boxes on the
 * mineSweeper JFrame.
 *
 * @author Logan
 */
public class MineSweeper extends JFrame {

    static MSPanel[][] board;
    static boolean clicked = false;

    /**
     * Constructor method for the MineSweeper Class
     *
     * @param width width of the board
     * @param height height of the board
     */
    public MineSweeper(int width, int height) {
        super("MineSweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(width, height, 1, 1));

        //builds the board
        board = new MSPanel[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[x][y] = new MSPanel(x, y);
                add(board[x][y]);
            }
        }

        //sets the window size, makes the window visible, and locks the window size
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //checks if the player has won the game or not
    public static boolean checkOver() {
        int count = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y].getClicked() == true || board[x][y].getMine() == 1) {
                    count++;
                }
            }
        }
        if (count == board.length * board[0].length) {
            return true;
        } else {
            return false;
        }
    }

    public static void layMines(int inX,int inY) {
        //this lays a known number of mines in random places
        int targetMines = 15;
        int laidMines = 0;
        while (laidMines < targetMines) {
            int x = (int) (Math.random() * (board.length));
            int y = (int) (Math.random() * (board[0].length));
            if (board[x][y].getMine() == 0 && (x != inX || y != inY)) {
                board[x][y].setMine(true);
                laidMines++;
            }
        }

        //sets mineNum from the MSPanel class
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {
                int count = 0;
                //looks and counts the mines in the surrounding areas
                for (int up = -1; up <= 1; up++) {
                    for (int side = -1; side <= 1; side++) {
                        if ((x + up < 0) || (x + up > board.length - 1) || (y + side < 0) || (y + side > board[0].length - 1)) {
                        } else {
                            count += board[x + up][y + side].getMine();
                        }
                    }
                }
                board[x][y].setNumber(count);
            }
        }
        clicked = true;
    }
    
    //floods the board if the player clicks on a spot with zero mines surrounding it
    public static void flood(int x, int y) {
        if (board[x][y].getNum() == 0) {
            for (int up = -1; up <= 1; up++) {
                for (int side = -1; side <= 1; side++) {
                    if ((x + up < 0) || (x + up > board.length - 1) || (y + side < 0) || (y + side > board[0].length - 1)) {
                    } else if (board[x + up][y + side].getClicked() == false) {
                        board[x + up][y + side].setClicked(true);
                    }
                }
            }
        }
    }

    //method for stopping the game if the player won or lost
    public static void win(boolean win) {
        if (win == true) {
            JOptionPane.showMessageDialog(null, "You Won!");
            System.exit(0);
        } else {
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[0].length; y++) {
                    board[x][y].setClicked(false);
                }
            }
            JOptionPane.showMessageDialog(null, "You Hit a Mine :(");
            System.exit(0);
        }
    }
    
    public static boolean getClicked() {
        return clicked;
    }

    //starts the game
    public static void main(String[] args) {
        MineSweeper game = new MineSweeper(10, 10);
    }

}
