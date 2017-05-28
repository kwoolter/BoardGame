/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author KeithW
 */
public class BoardGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
/*
        BGBoard board;

        board = new BGBoard(8, 8);

        board.initialise();
        board.load();

        System.out.println("start...");
        System.out.println(board.print());

        boolean bMoving = true;

        while (bMoving == true) {

            board.matchCounters();
            board.deleteCounters();
            System.out.println("matching...");
            System.out.println(board.print());

            bMoving = board.gravity();
            System.out.println("gravity...");
            System.out.println(board.print());
        }
*/


        BoardGameFrame mainFrame = new BoardGameFrame();
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        mainFrame.pack();
        mainFrame.setVisible(true);

    }
}
