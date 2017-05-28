/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

/**
 *
 * @author KeithW
 */
public class BG2DVector {
    
    private int x,y;

    public BG2DVector(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
     public BG2DVector(BG2DVector copy) {
        this.x = copy.getX();
        this.y = copy.getY();
    }   

    @Override
    public String toString() {
        return "BG2DVector{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        BG2DVector compare = (BG2DVector)obj;
        if(this.x == compare.getX()  && this.y == compare.getY()) {
            return true;
        }
        else {
            return false;
            
        }
     }
    
    public BG2DVector add(BG2DVector addVector) {
        
        this.x += addVector.getX();
        this.y += addVector.getY();
        return this;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
    
    
}
