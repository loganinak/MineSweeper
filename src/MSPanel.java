
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * This class is made to be instanced many times to make keeping track of clicks, flags, mines, and the number of mines around it super simple. 
 * For example instead of figuring out if a box was clicked based on the position of the mouse, each box will be a JPanel the will automatically know if it's been clicked.
 * 
 * @author Logan
 */
public class MSPanel extends JPanel {

    //instance variables
    boolean clicked = false;
    boolean mine;
    boolean flagged = false;
    String mineNumber;
    int xCord;
    int yCord;

    //Constructor
    /**
     * This is the constructor for the MSPanel Class. 
     * The X and Y values of its position are needed to figure out where to check for flooding.
     * @param inX The X position of this particular panel
     * @param inY the Y position of this particular panel
     */
        public MSPanel(int inX, int inY) {
        xCord = inX;
        yCord = inY;
        setPreferredSize(new Dimension(40, 40));
        addMouseListener(new MSListener());

        repaint();
    }

    //The all important paint method
    @Override
    public void paint(Graphics g) {
        //changes font and gets prepared for centering
        Font f = new Font("Times", Font.PLAIN, 20);
        g.setFont(f);
        FontMetrics fm = g.getFontMetrics();
        int a = fm.getAscent();
        int h = fm.getHeight();
        //paints an unclicked looking box if it hasn't been clicked on
        if (clicked == false) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 40, 40);
            g.setColor(new Color(179, 173, 143));
            g.fillRect(0, 0, 38, 38);
            //paints a ! if it's been flagged
            if (flagged == true) {
                g.setColor(Color.RED);
                int w = fm.stringWidth("!");
                g.drawString("!", 20 - w / 2, 20 + a - h / 2);
            }
            //paints a red box if a mine is in that spot
        } else if (clicked == true && mine == true) {
            g.setColor(Color.RED);
            g.fillRect(0, 0, 40, 40);
            //paints a clicked looking box and the number of mines nearby
        } else if (clicked == true) {
            int w = fm.stringWidth(mineNumber);
            g.setColor(new Color(186, 185, 177));
            g.fillRect(0, 0, 40, 40);
            g.setColor(Color.BLUE);
            g.drawString(mineNumber, 20 - w / 2, 20 + a - h / 2);
        }
    }

    //user input stuff
    private class MSListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            //System.out.println("press");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println("release");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //System.out.println("mouse entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //System.out.println("mouse exited");
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //right click
            if (SwingUtilities.isRightMouseButton(e)) {
                System.out.println("right Click");
                flagged = !flagged;
                repaint();
            }
            //left click
            if (SwingUtilities.isLeftMouseButton(e) && !flagged) {
                System.out.println("left click");
                clicked = true;
                repaint();
                //floods the board if there is no mines around it
                if ("".equals(mineNumber)) {
                    MineSweeper.flood(xCord, yCord);
                }
                //makes the player lose if there is a mine
                if (mine == true) {
                    MineSweeper.win(false);
                }
                //checks for a win and makes the player win if ther
                if (MineSweeper.checkOver() == true) {
                    MineSweeper.win(true);
                }
            }

        }
    }

    //Method needed for counting the mines around a particular spot(returns 0 or 1 based on the boolean value of mine)
    public int getMine() {
        if (mine == true) {
            return 1;
        } else {
            return 0;
        }
    }

    //Method needed to check for game over
    public boolean getClicked() {
        return clicked;
    }

    //returns the number of mines surrounding it. Needed for the flood method
    public int getNum() {
        if ("".equals(mineNumber)) {
            return 0;
        } else {
            return Integer.parseInt(mineNumber);
        }
    }

    //needed to change the value of mineNumber after setting the mines
    public void setNumber(int inNum) {
        if (inNum == 0) {
            mineNumber = "";
        } else {
            mineNumber = Integer.toString(inNum);
        }
    }

    //Needed to change clicked when flooding when flooding
    public void setClicked() {
        System.out.println("virtual click");
        clicked = true;
        repaint();
        //floods the area around it
        MineSweeper.flood(xCord, yCord);
    }
    
    //allows the mine value to be changed
    public void setMine(boolean input) {
        mine = input;
    }

}
