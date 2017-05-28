/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author KeithW
 */
public class BGBoard {

    private BGSquare[][] board;
    private int height, width, threshold, iLevel, iGameScore, iSwaps;
    private BG2DVector gravity, up, down, left, right;
    private ArrayList<BG2DVector> directions;
    private LinkedList<Point> selectedSquares;

    // Constructor with size of board...
    public BGBoard(int width, int height) {
        this.height = height;
        this.width = width;

        // How many counters in a row are required?
        this.threshold = 3;

        this.iLevel = 1;

        this.directions = new ArrayList<BG2DVector>();
        this.selectedSquares = new LinkedList<Point>();

        // Valid directions to look for matching counters...
        up = new BG2DVector(0, 1);
        down = new BG2DVector(0, -1);
        left = new BG2DVector(-1, 0);
        right = new BG2DVector(1, 0);

        // Load the available directions into an array
        directions.add(up);
        directions.add(down);
        directions.add(left);
        directions.add(right);

        // Which way do counters fall?
        gravity = up;
    }

    public int getHeight() {
        return height;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getWidth() {
        return width;
    }

    public BGSquare getSquare(int x, int y) {
        return board[x][y];
    }

    public int addScore(int iExtraScore) {
        this.iGameScore += iExtraScore;
        setSquareTile();
        return this.iGameScore;
    }

    public int getGameScore() {


        return iGameScore;
    }

    public void setGameScore(int iGameScore) {
        this.iGameScore = iGameScore;

    }

    public int getLevel() {
        return (this.iGameScore / 1000) + 1;
    }

    public int getSwaps() {
        return iSwaps;
    }

    public int addSwaps(int iAdd) {
        this.iSwaps += iAdd;
        return this.iSwaps;
    }

    @Override
    public String toString() {
        String sDescription = "";
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                sDescription += board[x][y].toString();
            }
            sDescription += "\n";
        }
        return sDescription;
    }

    // print a text version of the board
    public String print() {

        String sDescription = "";

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                BGCounter current = board[x][y].getCounter();

                if (current == null) {
                    sDescription += "NULL,";
                } else if (current.isDeleted() == false) {
                    sDescription += board[x][y].getCounter().getName() + ",";

                } else {
                    sDescription += "BLANK,";
                }
            }

            sDescription += "\n";
        }


        return sDescription;
    }

    // Initialise the game object
    public void initialise() {

        BGCounterFactory.load();
        int iTile = D2Dice.roll(1, 20, 0);


        board = new BGSquare[width][height];

        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                board[x][y] = new BGSquare("Test", "tile" + iTile + ".jpg");
            }
        }

        this.iGameScore = 0;
        this.iSwaps = 0;

    }

    public void newGame() {
        this.iGameScore = 0;
        this.iSwaps = 0;

        this.clear();
    }

    // Load all of the squares with random counters
    public void load() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                board[x][y].setCounter(BGCounterFactory.getRandom(this.iLevel));
            }
        }
    }

    public void setGravity(BG2DVector gravity) {
        this.gravity = gravity;
    }

    // Load all of the squares with no counter
    public void clear() {
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                board[x][y].setCounter(null);
            }
        }
    }

    // look for counters that are in a row
    public boolean matchCounters() {
        boolean bMatched = false;

        // for each square on the board
        for (int y = 0; y < height; y++) {

            for (int x = 0; x < width; x++) {

                // Find out what type of counter is on the current square
                BGCounter contents = board[x][y].getCounter();

                if (contents != null) {

                    String sCounterType = contents.getName();
                    BG2DVector selected = new BG2DVector(x, y);

                    // System.out.println("Analysing " + sCounterType + " at (" + x + "," + y + ")");

                    // Iterate through the available directions
                    for (int i = 0; i < directions.size(); i++) {

                        int iFound = search(sCounterType, selected, directions.get(i), 0);
                        // System.out.println("Found " + iFound + " " + sCounterType + " going " + directions.get(i));

                        if (iFound >= this.threshold) {
                            bMatched = true;
                        }
                    }
                }
            }
        }

        return bMatched;
    }

    // Load new counters at teh top of the board.
    public boolean loadNewCounters() {
        boolean bNewLoaded = false;

        for (int x = 0; x < width; x++) {

            // Find out what type of counter is on the current square
            BGCounter contents = board[x][0].getCounter();
            // if nothing in this square then load a new counter
            if (contents == null) {
                contents = BGCounterFactory.getRandom(this.getLevel());
                board[x][0].setCounter(contents);
                bNewLoaded = true;
            }
        }

        return bNewLoaded;
    }

    // Recursive search method to look for similar counters in adjacent squares
    private int search(String sType, BG2DVector current, BG2DVector direction, int iDepth) {

        // System.out.println(iDepth + ". Searching for " + sType + " at " + current + "going " + direction);

        int iMaxDepth = iDepth;

        // If the current position is on the board and the ounter matched what we were looking for then continue
        if (this.isInbounds(current)) {
            BGCounter counter = board[current.getX()][current.getY()].getCounter();

            // if the square has a counter that matches the type that we are looking for...
            if (counter != null && (counter.getName().equals(sType) || counter.isWildCard())) {

                // keep moving in the same direction
                BG2DVector next = new BG2DVector(current);
                next.add(direction);

                // ..and recursively look for a counter of the same type
                iMaxDepth = search(sType, next, direction, iMaxDepth + 1);


                // If we got to the required depth then process counter for a match
                if (iMaxDepth >= this.getThreshold()) {

                    counter.setScoreMuliplier(iMaxDepth - this.getThreshold() + 1);

                    // If the counter type is explodable then set it to explode as well as the counters around it
                    if (counter.isExplodable()) {
                        counter.setExplode(true);
                        //System.out.println(current + " flagged for exploding");

                        for (int i = 0; i < directions.size(); i++) {
                            BG2DVector adjacent = new BG2DVector(current).add(directions.get(i));
                            if (this.isInbounds(adjacent)) {
                                BGCounter adjacentCounter = board[adjacent.getX()][adjacent.getY()].getCounter();
                                adjacentCounter.setExplode(true);
                                adjacentCounter.setScoreMuliplier(iMaxDepth - this.getThreshold() + 1);
                                //System.out.println(adjacent + " adjacent flagged for exploding");
                            }

                        }


                    } // Else just flag it for deletion...
                    else {
                        counter.setDelete(true);
                    }
                }

            }

        }



        return iMaxDepth;


    }

    // See if a position is on the board
    private boolean isInbounds(BG2DVector test) {
        boolean bInbounds = true;

        int x = test.getX();
        int y = test.getY();

        if (x < 0 || x >= width || y < 0 || y >= height) {
            bInbounds = false;
        }

        return bInbounds;
    }

    // Move any counters in the direction of gravity
    public boolean gravity() {
        boolean bMoved = false;

        // for each square on the board
        for (int y = height - 1; y >= 0; y--) {

            for (int x = 0; x < width; x++) {
                BG2DVector selected = new BG2DVector(x, y);
                BG2DVector below = new BG2DVector(selected);
                below.add(gravity);

                // if there is a square below this one
                if (this.isInbounds(below) == true) {
                    // Find out what type of counter is on the current square
                    BGCounter contents = board[x][y].getCounter();
                    BGCounter belowContents = board[below.getX()][below.getY()].getCounter();

                    if (contents != null && belowContents == null) {
                        board[below.getX()][below.getY()].setCounter(contents);
                        board[x][y].setCounter(null);
                        bMoved = true;
                    }

                }

            }
        }


        return bMoved;
    }

    // Clear out any matched counters and keep a score
    public int deleteCounters() {
        int iScore = 0;

        BGCounter current;

        // for each square on the board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                current = board[x][y].getCounter();
                // If there is a counter on teh current square that is flagged for deletion then delete it.
                if (current != null && current.isDeleted() == true) {
                    iScore += current.getScore();
                    board[x][y].setCounter(null);
                }
            }
        }

        this.addScore(iScore);

        return iScore;
    }

    private void setSquareTile() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[x][y].setImageFileName("tile" + this.getLevel() + ".jpg");
            }
        }

    }
    // Set any counters flagged to explode to deleted

    public int explodeCounters() {
        int iCount = 0;

        BGCounter current;

        // for each square on the board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                current = board[x][y].getCounter();
                // If there is a counter on the current square that is flagged for deletion then delete it.
                if (current != null && current.isExplode() == true && current.isDeleted() == false) {
                    iCount++;
                    current.setDelete(true);
                }
            }
        }
        //System.out.println("Exploded " + iCount);

        return iCount;
    }

    // Add a selected square - but only two can be highled at once.
    public void addSquareSelection(int x, int y) {


        Point coords = new Point(x, y);

        // Check that the square is not already selected
        if (selectedSquares.contains(coords) == false) {

            // If you have already selected two squares then unselect the oldest one
            if (selectedSquares.size() == 2) {
                Point oldCoords = selectedSquares.removeFirst();
                BGSquare unhighlight = board[oldCoords.x][oldCoords.y];
                unhighlight.setIsHighlighted(false);
            }

            BGSquare newSelection = board[x][y];
            newSelection.setIsHighlighted(true);
            selectedSquares.add(coords);
        }

    }

    // Swap over the two currently selected squares if...
    // they are adjacent
    public boolean swapSelected() {

        boolean bSwapped = false;

        // We have got two squares selected...
        if (selectedSquares.size() == 2) {

            Point point1, point2;

            BGSquare selection1, selection2;
            BGCounter counter1, counter2;

            point1 = selectedSquares.peekFirst();
            //selection1.setIsHighlighted(false);

            point2 = selectedSquares.peekLast();
            //selection2.setIsHighlighted(false);

            // see if this swap is a valid one...
            boolean bValidSwap = false;
            BG2DVector difference = new BG2DVector(point1.x - point2.x, point1.y - point2.y);

            for (int i = 0; i < directions.size(); i++) {
                if (directions.get(i).equals(difference)) {
                    bValidSwap = true;
                }
            }

            if (bValidSwap == true) {
                selection1 = board[point1.x][point1.y];
                selection2 = board[point2.x][point2.y];

                counter1 = selection1.getCounter();
                counter2 = selection2.getCounter();

                // if the selected squares actually contain counters... then swap them
                if (counter1 != null && counter2 != null) {
                    // Check that the counters are swappable...
                    if (counter1.isSwappable() && counter2.isSwappable()) {
                        selection1.setCounter(counter2);
                        selection2.setCounter(counter1);
                        bSwapped = true;
                    } else {
                        System.out.println("Non-swappable");
                    }
                }
            }
        }

        // System.out.println("Swapped =" + bSwapped);
        if (bSwapped == true) {
            this.iSwaps++;
        }
        return bSwapped;

    }
}
