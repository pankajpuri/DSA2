package dsa2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates|
 * and open the template in the editor.
 */
/**
 * Food which has a rating, can move around in a panel in random directions. It
 * must run as a thread
 */
public class Food implements Runnable, Comparable<Food> {

    private int x;
    private int y;
    private int panelW;
    private int panelH;
    private int rating;
    private Random random;
    private Color c;
    private int size = 20;
    private int numMovements;
    private int MAX_MOVES_BEFORE_CHANGE = 30;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    };
    private Direction direction;
    private int movement = 10;
    private boolean alive;
    private int SLEEP = 100;
    Snake snake;
     List<Food> foodlist = Collections.synchronizedList(new ArrayList<>());
       

    //Food takes in a panel width and height as well as a food rating which also
    //influences the size of the food
    public Food(int panelW, int panelH, int rating, Snake snake) {
        random = new Random();
        this.panelH = panelH;
        this.panelW = panelW;
        this.snake = snake;
        x = random.nextInt(panelW - (2 * size)) + size;

        y = (int) ((random.nextInt(panelH - (2 * size)) + size) / 1.5);

        this.rating = rating;
      
//        if (rating > 0) {
//            this.rating = random.nextInt(10);
//
//        }
        c = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        size += 2 * rating;
        int newDirection = random.nextInt(4);
        // System.out.println("newDirection is "+newDirection);
        switch (newDirection) {
            case 0:
                direction = Direction.DOWN;
                break;
            case 1:
                direction = Direction.LEFT;
                break;
            case 2:
                direction = Direction.RIGHT;
                break;
            default:
                direction = Direction.UP;
                break;
        }
    }

    @Override
    public void run() {
        alive = true;
       Iterator<Food> foodIterator = null;
        
        while (alive) {
            if (alive == false) {
                break;
            } else {
                
                    foodIterator = snake.eatFoodIfInRange(this);
                
             
//                 while(foodIterator.hasNext())
//                {
//                    Food f = foodIterator.next();
//                    if(compareTo(f) == 0){
//                         moveFood();
//                    }
//                    else{
//                        killFood();
//                    }
//                    
//                }
                moveFood();
                
                
                 
                
            }

            try {
                Thread.sleep(SLEEP);
            } catch (Exception e) {
            }
        }
    }

    public synchronized void killFood() {
        alive = false;
    }
    public synchronized void removeFood(Food o){
        foodlist.remove(o);
    }
    //Moves the food in specified direction and makes sure it doesn't
    //crash in to the panel, changes to a new random direction after several
    //movements
    private synchronized void moveFood() {
        if (direction == Direction.UP) {
            y -= movement;
        } else if (direction == Direction.DOWN) {
            y += movement;
        } else if (direction == Direction.LEFT) {
            x -= movement;
        } else if (direction == Direction.RIGHT) {
            x += movement;
        }

        if (x - (size / 2) < 0) {
            direction = Direction.RIGHT;
            numMovements = 0;
        } else if ((x + size / 2) > panelW) {
            direction = Direction.LEFT;
            numMovements = 0;
        } else if (y - (size / 2) < 0) {
            direction = Direction.DOWN;
            numMovements = 0;
        } else if ((y + size / 2) > panelH) {
            direction = Direction.UP;
            numMovements = 0;
        }
        numMovements++;

        if (numMovements > MAX_MOVES_BEFORE_CHANGE) {
            int newDirection = random.nextInt(4);
            numMovements = 0;
            switch (newDirection) {
                case 0:
                    direction = Direction.DOWN;
                    break;
                case 1:
                    direction = Direction.LEFT;
                    break;
                case 2:
                    direction = Direction.RIGHT;
                    break;
                default:
                    direction = Direction.UP;
                    break;
            }
        }
//        System.out.println("x,y : ("+x+","+y+")");
    }

    public synchronized void drawFood(Graphics g) {
        g.setColor(c);
        g.fillRect(x - (size / 2), (y - size / 2), size, size);
        g.setColor(Color.WHITE);
        g.drawString("" + rating, x, y);
        g.setColor(Color.BLACK);
        g.drawRect(x - (size / 2), (y - size / 2), size, size);
    }

    public int getValue() {
        return rating;
    }

    public Color getColour() {
        return c;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public synchronized int compareTo(Food o) {
             int val =rating - o.rating;
             System.out.println("val : "+val);
        return val;
    }

    @Override
    public synchronized boolean equals(Object o) {
        if (o instanceof Food) {
            Food f = (Food) o;
            return (f.getValue() == rating && f.getColour() == c);
        } else {
            return false;
        }
    }
}
