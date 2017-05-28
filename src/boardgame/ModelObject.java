/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

import java.awt.Image;
import java.awt.Rectangle;
import java.lang.Math;
import javax.swing.ImageIcon;

/**
 *
 * @author JaneW
 */
public class ModelObject {


    private double xPos, yPos, xSpeed, ySpeed, xAccel, yAccel, xCOR, yCOR;
    private int width, height, rotation, targetRotation, dRotation;
    private static int xMin, xMax, yMin, yMax;
    private String sImageFileName;
    private Image image;
    private boolean bounce;
    private boolean visible;

    protected ModelObject(double xPos, double yPos, double xSpeed, double ySpeed, double xAccel, double yAccel, double xCOR, double yCOR, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.xAccel = xAccel;
        this.yAccel = yAccel;
        //this.yAccel = 0.3;
        this.xCOR = xCOR;
        this.yCOR = yCOR;
        this.width = width;
        this.height = height;
        
        // Set the initial angle of image object rotation
        this.rotation = 0;
    }

    
    
    // Constructor for objects that have no image
    public ModelObject(int x, int y, int width, int height) {
        
        // Call constructor for setting key properties
        this(x,y,0,0,0,0,0,0,width,height);

        this.sImageFileName = "HIDDEN";
        this.image = null;
        this.visible = false;

    }

    // Constructor for objects that are based off an image file
    public ModelObject(int x, int y, int dx, int dy, boolean bounce, String sImageFileName) {

        // Call constructor for setting key properties
        this(x,y,dx,dy,0,0,0,0,0,0);

       
        // Set the CORs based on wether the object should bounce
        this.bounce = bounce;
        if (this.bounce == true) {
            this.xCOR = -1;
            this.yCOR = -1;
        } else {
            this.xCOR = 0;
            this.yCOR = 0;
        }

        this.sImageFileName = sImageFileName;
        this.visible = true;

        ImageIcon ii = new ImageIcon(this.getClass().getResource("resources/" + this.sImageFileName));
        image = ii.getImage();

        this.width = image.getWidth(null);
        this.height = image.getHeight(null);

        System.out.println(sImageFileName + ":" + getRectangle());
    }

    
    
    // Static function to set the bounds for ALL objects
    public static void setBounds(Rectangle rect) {
        xMin = (int) rect.getX();
        yMin = (int) rect.getY();
        xMax = (int) rect.getMaxX();
        yMax = (int) rect.getMaxY();

    }

    // move this object based on dx, dy, boundaries and bouncy-ness
    public void move() {
        // Move the objects x and y position
        xPos += xSpeed;
        yPos += ySpeed;

        //Change speed by acceleration
        xSpeed += xAccel;
        ySpeed += yAccel;

        this.rotation += dRotation;

        if (rotation < -180) {
            rotation = 180 + dRotation;
        } else if (rotation > 180) {
            rotation = -180 + dRotation;
        }


        // Have we hit the maximum Y position ?   
        if (yPos > (this.yMax - this.getHeight())) {
            yPos = yMax - this.getHeight();
            if (bounce == true) {
                ySpeed *= yCOR;
            }
        } // Have we hit the minimum Y position?
        else if (yPos < this.yMin) {
            yPos = this.yMin;
            if (bounce == true) {
                ySpeed *= yCOR;
            }
        }

        // Have we hit the maximum X position?
        if (xPos > (this.xMax - this.getWidth())) {
            xPos = this.xMax - this.getWidth();
            if (bounce == true) {
                xSpeed *= xCOR;
            }
        } // Have we hit the minimum X position
        else if (xPos < this.xMin) {
            xPos = this.xMin;
            if (bounce == true) {
                xSpeed *= xCOR;
            }
        }


        this.setTargetRotation();

    }

    public void swapDx() {
        this.xSpeed *= -1;
    }

    public void swapDy() {
        this.ySpeed *= -1;
    }

    public int getX() {
        return (int) xPos;
    }

    public int getY() {
        return (int) yPos;
    }

    public void setX(int x) {
        this.xPos = x;
    }

    public void setY(int y) {
        this.yPos = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setDx(int dx) {
        this.xSpeed = dx;
    }

    public void setDy(int dy) {
        this.ySpeed = dy;
    }

    public Rectangle getRectangle() {
        return new Rectangle((int) this.xPos, (int) this.yPos, this.width, this.height);

    }

    public int getRotation() {
        // We remove 90 degrees from the rotation.
        // This is because all atan2(y,x) return angle relative to x=0 plane
        // Easier to design graphics that face the screen (-90 degress  to x=0 plane)
        return rotation - 90;
    }

    public int getTargetRotation() {
        return targetRotation;
    }

    public int getdRotation() {
        return dRotation;
    }

    // Work out which way we need to be heading and how to rotate to that heading
    protected int setTargetRotation() {

        // If we are moving then calculate the angle of movement
        if (xSpeed != 0 || ySpeed != 0) {
            targetRotation = (int) Math.toDegrees(Math.atan2(ySpeed, xSpeed));
        }


        int diff = targetRotation - rotation;


        if (Math.abs(diff) <= Math.abs(dRotation)) {
            dRotation = 0;
            rotation = targetRotation;
        } else if (diff > 0) {
            dRotation = 4;
        } else {
            dRotation = -4;
        }

        if (Math.abs(diff) > 180) {
            dRotation *= -1;
        }
        return targetRotation;
    }

    public String getsImageFileName() {
        return "resources/" + sImageFileName;
    }

    public Image getImage() {
        return image;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return "ModelObject{" + "x=" + xPos + ", y=" + yPos + ", width=" + width + ", height=" + height + ", dx=" + xSpeed + ", dy=" + ySpeed + ", rotation=" + rotation + ", targetRotation=" + targetRotation + ", dRotation=" + dRotation + ", sImageFileName=" + sImageFileName + '}';
    }

    // Work out if we have hit something and what to do as a result.
    public boolean collision(ModelObject object) {
        boolean bHit = false;
        Rectangle rect;

        // Obtain the intersection and it width and height
        rect = this.getRectangle().intersection(object.getRectangle());
        double hitHeight = rect.getHeight();
        double hitWidth = rect.getWidth();

        // Was there an intersection?
        if (rect.isEmpty() == false) {

            bHit = true;

            // If the height of intersection is bigger than the width the side hit
            if (hitHeight > hitWidth) {
                //System.out.println("Hit on side");

                // Is the intersection on the right hand side?           
                if ((int) rect.getMinX() > this.xPos) {
                    //System.out.println("Hit on RIGHT side");
                    this.xPos -= hitWidth;
                    if (this.bounce == true) {
                        xSpeed *= xCOR;
                    }
                } // Hit is on the left side
                else {
                    //System.out.println("Hit on LEFT side");
                    this.xPos += hitWidth;
                    if (this.bounce == true) {
                        xSpeed *= xCOR;
                    }

                }
            } // else top/bottom hit
            else {
                // Is the intersection on the bottom?           
                if ((int) rect.getMinY() > this.yPos) {
                    //System.out.println("Hit on BOTTOM side");
                    this.yPos -= hitHeight;
                    if (this.bounce == true) {
                        ySpeed *= yCOR;
                    }
                } // Hit is on the left side
                else {
                    //System.out.println("Hit on TOP side");
                    this.yPos += hitHeight;
                    if (this.bounce == true) {
                        ySpeed *= yCOR;
                    }
                }
            }
        }


        return bHit;

    }
}
