/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author JaneW
 */
public class BGBoardView extends javax.swing.JPanel implements ActionListener {

    private BGBoard board;
    // What is the size of this view onto the floor
    private int viewHeight, viewWidth, iSquareWidth, iSquareHeight, iBorderWidth, iSelectionState;
    private Color colorSquareBG, colorCounterBG, colorHightlight;

    /**
     * Creates new form BGBoardView
     */
    public BGBoardView(int width, int height) {

        initComponents();

        viewWidth = width;
        viewHeight = height;
        iSquareWidth = iSquareHeight = 90;
        iBorderWidth = 4;
        iSelectionState = 0;

        colorSquareBG = colorCounterBG = new Color(150, 150, 150);
        colorHightlight = new Color(255, 255, 0);
        board = null;

        this.setSize(viewWidth, viewHeight);
        this.setBounds(0, 0, viewWidth, viewHeight);
        this.setDoubleBuffered(true);
        this.setBackground(colorSquareBG);



    }

    public void setBoard(BGBoard board) {
        this.board = board;
        if (board != null) {
            this.viewWidth = board.getWidth() * this.iSquareWidth;
            this.viewHeight = board.getHeight() * this.iSquareHeight;
            this.setSize(viewWidth, viewHeight);
        }
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    // Paint the game board
    public void paint(Graphics g) {



        if (board != null) {
            super.paint(g);

            Graphics2D g2d = (Graphics2D) g;

            int iBoardHeight = board.getHeight();
            int iBoardWidth = board.getWidth();

            for (int y = 0; y < iBoardHeight; y++) {
                for (int x = 0; x < iBoardWidth; x++) {

                    BGSquare currentSquare = board.getSquare(x, y);
                    // Load the image for the counter
                    ImageIcon iiTile = new ImageIcon(this.getClass().getResource("resources/tiles/" + currentSquare.getImageFileName()));
                    Image imgTile = iiTile.getImage();

                    g2d.drawImage(imgTile, modelToView(new Point(x, y)).x, modelToView(new Point(x, y)).y, this.iSquareWidth, this.iSquareHeight, null);

                    BGCounter counter = currentSquare.getCounter();
                    // If there is a counter on this square...
                    if (counter != null) {

                        // Load the image for the counter
                        ImageIcon ii = new ImageIcon(this.getClass().getResource("resources/" + counter.getImageFileName()));
                        Image img = ii.getImage();

                        // get the width and height of the image
                        int iImageWidth = img.getWidth(null);
                        int iImageHeight = img.getHeight(null);


                        double dHeight, dWidth, dXOffset, dYOffset;
                        dXOffset = dYOffset = this.iBorderWidth;

                        // If teh 
                        if (iImageHeight > iImageWidth) {
                            dHeight = this.iSquareHeight - iBorderWidth * 2;
                            dWidth = (dHeight / (double) iImageHeight) * (double) iImageWidth;
                            dXOffset += (dHeight - dWidth) / 2;
                        } else {
                            dWidth = this.iSquareWidth - iBorderWidth * 2;
                            dHeight = (dWidth / (double) iImageWidth) * (double) iImageHeight;
                            dYOffset += (dWidth - dHeight) / 2;
                        }

                        g2d.drawImage(img, modelToView(new Point(x, y)).x + (int) dXOffset, modelToView(new Point(x, y)).y + (int) dYOffset, (int) dWidth, (int) dHeight, null);

                        if (board.getSquare(x, y).isIsHighlighted() == true) {

                            g2d.setColor(this.colorHightlight);
                            g2d.setStroke(new BasicStroke((float) this.iBorderWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                            Rectangle border = new Rectangle(modelToView(new Point(x, y)).x + this.iBorderWidth / 2, modelToView(new Point(x, y)).y + this.iBorderWidth / 2, this.iSquareWidth - this.iBorderWidth, this.iSquareHeight - this.iBorderWidth);
                            g2d.draw(border);

                        }

                    }
                }
            }

            Toolkit.getDefaultToolkit().sync();
            g.dispose();

        }
    }
    // Covert a point in teh model to a point in the view

    private Point modelToView(Point modelPoint) {
        Point viewPoint = new Point();

        viewPoint.x = modelPoint.x * this.iSquareWidth;
        viewPoint.y = modelPoint.y * this.iSquareHeight;

        return viewPoint;

    }

    // Convert a point in the view to a point in the model
    private Point viewToModel(Point viewPoint) {
        Point modelPoint = new Point();

        modelPoint.x = viewPoint.x / this.iSquareWidth;
        modelPoint.y = viewPoint.y / this.iSquareHeight;

        if (modelPoint.x >= board.getWidth()) {
            modelPoint.x = board.getWidth() - 1;
        }

        if (modelPoint.y >= board.getHeight()) {
            modelPoint.y = board.getHeight() - 1;
        }

        return modelPoint;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(0, 0, 0));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:

        if (evt.getButton() == MouseEvent.BUTTON1) {
            Point modelPoint = viewToModel(new Point(evt.getX(), evt.getY()));
            //System.out.println(modelPoint);
            board.addSquareSelection(modelPoint.x, modelPoint.y);
            repaint();
        }
    }//GEN-LAST:event_formMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
