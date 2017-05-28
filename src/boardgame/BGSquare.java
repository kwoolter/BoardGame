/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

/**
 *
 * @author KeithW
 */
public class BGSquare {

    private String sType;
    private String sImageFileName;
    private BGCounter counter;
    private boolean isHighlighted;

    public BGSquare() {

        this.sType = "NORMAL";
        this.sImageFileName = "NORMAL.PNG";
        this.counter = null;
        isHighlighted = false;
    }

    public BGSquare(String sType, String sImageFileName) {

        this.sType = sType;
        this.sImageFileName = sImageFileName;
        this.counter = null;
        isHighlighted = false;
    }

    @Override
    public String toString() {
        return "BGSquare{" + "sType=" + sType + ", sImageFileName=" + sImageFileName + ", counter=" + counter + '}';
    }
    
    

    public BGCounter getCounter() {
        return counter;
    }

    public void setCounter(BGCounter counter) {
        this.counter = counter;
    }

    public String getImageFileName() {
        return sImageFileName;
    }

    public void setImageFileName(String sImageFileName) {
        this.sImageFileName = sImageFileName;
    }
    
    

    public String getsType() {
        return sType;
    }

    public boolean isIsHighlighted() {
        return isHighlighted;
    }

    public void setIsHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }
    
    
}
