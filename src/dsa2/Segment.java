package dsa2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

/**
 * A Segment which can be appended to the snake body
 *
 */
public class Segment implements Comparable<Segment> {

    public int size;
    

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    int x;
    int y;
    private int value;
    private Color colour;
    int count = 0;

    //takes in starting postion and colour and size of snake
    public Segment(int startX, int startY, int size, Color colour) {
        this.size = size;
        this.x = startX;
        this.y = startY;
        this.value = value;
        this.colour = colour;
        
    }

    //draws the segment with x,y in the middle of size value
    public void drawSegment(Graphics g) {
            g.setColor(colour);
            g.fillOval(x - (size / 2), (y - size / 2), size, size);
            g.setColor(Color.BLACK);
            g.drawOval(x - (size / 2), (y - size / 2), size, size);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    //No need to worry about comparable for this.
    @Override
    public int compareTo(Segment o) {

        return (x - o.x) + (y - o.y);
    }
}
