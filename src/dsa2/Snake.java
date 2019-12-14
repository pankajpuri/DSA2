/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsa2;

/**
 *
 * @author macbookpro
 */
import dsa2.SnakeGameGui.DrawPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

public class Snake implements Runnable {

    List<Segment> segmentlist;
    List<Snake> snake;
    boolean alive;
    int height, width;// of the panel;
    int SIZE;
    //int x, y;// direction coordinates;
    Direction dir;
    Segment sg;
    DrawPanel drawpanel;
    List<Food> foodlist;

    int rating = 0;

    enum Direction {
        U(0, -15), D(0, 15), L(-15, 0), R(15, 0);

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        final int x, y;
    }

    Snake(int panelWidth, int panelHeight, List<Segment> segmentlist, Direction direction, DrawPanel drawpanel) {
        this.width = panelWidth;
        this.height = panelHeight;
        this.segmentlist = segmentlist;
        this.foodlist = new ArrayList<>();
        foodlist = Collections.synchronizedList(foodlist);
        this.drawpanel = drawpanel;

        this.dir = direction.U;
    }

    public List<Food> getFoodlist() {
        return foodlist;
    }

    public void setFoodlist(List<Food> foodlist) {
        this.foodlist = foodlist;
    }

// check if the snake has hit itself or hasn't eaten food in disordere
    @Override
    public void run() {
        alive = true;
        while (alive) {
            if (isAlive()) {
                moveSnake();
            } else {
//                JOptionPane.showMessageDialog(null, "Sorry! you hit the wall.\n You have lost the game.:-) ",
//                        "SNAKE GAME", JOptionPane.INFORMATION_MESSAGE);
                alive = false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

    }

    private synchronized void growSnake(Color c) {
        Segment tail = segmentlist.get(segmentlist.size() - 1);
        int addAtXPosition = tail.x + dir.x;
        int addAtYPosition = tail.y + dir.y;
        segmentlist.add(new Segment(addAtXPosition, addAtYPosition, 15, c));

    }

    public synchronized void moveSnake() {

        for (int i = segmentlist.size() - 1; i > 0; i--) {
            Segment s1 = segmentlist.get(i - 1);
            Segment s2 = segmentlist.get(i);
            s2.x = s1.x;
            s2.y = s1.y;
        }
        Segment head = segmentlist.get(0);
        head.x += dir.x;
        head.y += dir.y;

    }

    public int getXPosition() {
        return 0;
    }

    public int getYPosition() {
        return 0;
    }

    public synchronized Direction setDirection(Direction direction) {
        if (direction == Direction.U) {
            this.dir = Direction.U;
        } else if (direction == Direction.D) {
            this.dir = Direction.D;
        } else if (direction == Direction.R) {
            this.dir = Direction.R;
        } else {
            this.dir = Direction.L;
        }

        return this.dir;

    }

    public synchronized boolean checkIfSnakeHit(Food food) {
        rating++;
        if (food.getValue() < rating) {
            System.out.println("rating :" + rating);
            return true;
        }

        return false;
    }

    public synchronized void drawSnake(Graphics g) {
        int count = 0;
        for (Segment sglist : segmentlist) {
            sglist.drawSegment(g);
        }
    }

   

    public synchronized Iterator<Food> eatFoodIfInRange(Food food)  {
        Iterator<Food> foodIterator = null;
        Segment head = segmentlist.get(0);
        int snakeX = head.x + dir.x;
        int snakeY = head.y + dir.y;
        int snakeSize = head.getSize();

        int foodX = food.getX();
        int foodY = food.getY();
        int foodSize = food.getSize();

        int x1 = (int) Math.pow((snakeX - foodX), 2);
        int y1 = (int) Math.pow((snakeY - foodY), 2);
        int z1 = (int) Math.pow((snakeSize + foodSize), 2);

        String filePath = "/Users/macbookpro/NetBeansProjects/DSA2/src/dsa2/Sounds/snakehit.mp3";

        if ((x1 + y1) < z1) {
            
            int value = food.getValue();
            int size = food.getSize();
            this.getFoodlist().remove(food);
            food.killFood();
            if (foodlist.isEmpty()) {
                killSnake();
                        // JOptionPane.showMessageDialog(null, "Congratulation! \]nYou have own the game. ");
            }
            if (checkIfSnakeHit(food)) {
                for (int i = 0; i < value; i++) {
                    growSnake(food.getColour());
                }
            } else {
                killSnake();

                for (Food ff : foodlist) {
                    ff.killFood();
                }
                //JOptionPane.showMessageDialog(null, "Sorry! \nyou haven't eaten food in ascending order.:-) ");

            }

        }
//         if (foodlist.isEmpty()){
//            killSnake();
////            JOptionPane.showMessageDialog(this.drawpanel, "Congratulation! \n You won the game.\n Play again :-) ",
////                    "SNAKE GAME", JOptionPane.INFORMATION_MESSAGE);
//        } 

        foodIterator = foodlist.iterator();
        return foodIterator;

    }

    boolean eatsTreat() {
        Segment head = segmentlist.get(0);
        int nextCol = head.x + dir.x;
        int nextRow = head.y + dir.y;
        for (Food p : foodlist) {
            if (p.getX() == nextCol && p.getY() == nextRow) {
                return foodlist.remove(p);
            }
        }
        return false;
    }

    public void killSnake() {
        alive = false;

    }

    public synchronized boolean isAlive() {
        //if snake touch it's body itself then dies
        Segment head = segmentlist.get(0);
        int w = head.x + dir.x;
        int h = head.y + dir.y;
        int wall = -1;
        // if hits walls    
        if (w < wall || h < wall || w > width || h > height) {
            System.out.println("hits walls");
            for (Food f : foodlist) {
                f.killFood();
            }
            return alive = false;

        } else {
            for (Segment s : segmentlist) {
                if (s.x == w && s.y == h) // if snake touches itself
                {
                    System.out.println("hits it's body");
                    return alive = false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }

}
