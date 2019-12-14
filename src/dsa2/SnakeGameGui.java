package dsa2;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import dsa2.Snake.Direction;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Color.black;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;


public class SnakeGameGui extends JPanel implements ActionListener, KeyListener {

    public final static int PANEL_WIDTH = 800;
    public final static int PANEL_HEIGHT = 800;
    public final static int NUM_FOOD = 10;
    public Timer timer;
    public DrawPanel drawpanel;
    private final JButton restartButton;
    private final JButton startBtn;
    private static JFrame frame;
    Direction dir;
    List<Food> foodlist;
    List<Snake> snakelist;
    List<Segment> segmentlist;
    Snake snake;
    Random gen;
    int x, y;
    ListIterator<Food> foodlistItr;
    ListIterator<Snake> snakeItr;
    Segment segment;
    Font smallFont;
    JPanel panel;
    Image snakehead;
    boolean gameOver;

    public SnakeGameGui() {
        super();
        setLayout(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        drawpanel = new DrawPanel();
        timer = new Timer(50, this);

        foodlist = Collections.synchronizedList(new ArrayList<>());
        snakelist = Collections.synchronizedList(new LinkedList<>());

        segmentlist = Collections.synchronizedList(new ArrayList<>());
        gen = new Random();

        ImageIcon imageIcon = new ImageIcon("src/dsa2/Assests/upmouth.png");
        snakehead = imageIcon.getImage();

        panel = new JPanel();
        restartButton = new JButton("Restart");
        startBtn = new JButton("Start");
        startBtn.addActionListener(this);
        restartButton.addActionListener(this);
        panel.add(restartButton);
        // panel.add(startBtn);
        timer.start();
//        foodlistItr = foodlist.listIterator();
        snakeItr = snakelist.listIterator();
        dir = Direction.U;
        gameOver = true;
        add(drawpanel, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameOver) {
                    initGame();
                    if(!snake.isAlive()){
                        gameOver = true;
                    }
                    frame.setFocusable(true);
                    frame.requestFocusInWindow();
                    repaint();
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();
//
//        if (source == startBtn) {
//            initGame();
//            frame.setFocusable(true);
//            frame.requestFocusInWindow();
//            panel.remove(startBtn);
//
//        }

        if (source == timer) {
            drawpanel.repaint();

        }
        if (source == restartButton) {
            segmentlist.clear();
            foodlist.clear();
            initGame();

//            JOptionPane.showMessageDialog(drawpanel, "YOU HAVE PRESSED THE RESTART BUTTON, YOU THIS FOR GAME MESSAGES :-) ",
//                    "SNAKE GAME", JOptionPane.INFORMATION_MESSAGE);
//            //Give Keyboard Focus back to the Frame DO NOT REMOVE! 
            frame.setFocusable(true);
            frame.requestFocusInWindow();

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key listener is called");
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (dir != Direction.D) {
                    dir = Direction.U;
                }
                //System.out.println("UP PRESSED");
                break;
            case KeyEvent.VK_DOWN:
                if (dir != Direction.U) {
                    dir = Direction.D;
                }
                //System.out.println("DOWN PRESSED");
                break;
            case KeyEvent.VK_LEFT:
                if (dir != Direction.R) {
                    dir = Direction.L;
                }
                // System.out.println("LEFT PRESSED");
                break;
            case KeyEvent.VK_RIGHT:
                if (dir != Direction.L) {
                    dir = Direction.R;
                }
                //System.out.println("RIGHT PRESSED");
                break;
            default:
                //System.out.println("SOME DIFFERENT KEY!");
                break;
        }

        snake.setDirection(dir);
        drawpanel.repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //IGNORE
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //IGNORE
    }

    public void initGame() {
        gameOver = false;
        Food food = null;
        for (int i = 0; i < 4; i++) {
            segment = new Segment((drawpanel.getWidth() / 2), (drawpanel.getHeight() / 2), 15, black);
            segmentlist.add(segment);
        }

        snake = new Snake(drawpanel.getWidth(), drawpanel.getHeight(), segmentlist, dir, drawpanel);
        Thread th = new Thread(snake);
        snakelist.add(snake);
        th.start();

        for (int i = 0; i < NUM_FOOD; i++) {
            food = new Food(drawpanel.getWidth(), drawpanel.getHeight(), i, snake);
            Thread thread = new Thread(food);
            foodlist.add(food);

            thread.start();
        }
        snake.setFoodlist(foodlist);
//        
    }

    public class DrawPanel extends JPanel {

        public DrawPanel() {
            super();
            setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            setBackground(Color.WHITE);
        }

        //can be used to draw the snake and food
        void drawStartScreen(Graphics g) {
            g.setColor(Color.blue);
            g.setFont(getFont());
            g.drawString("Snake", PANEL_WIDTH/2, (PANEL_HEIGHT/2)-100);
            g.setColor(Color.orange);
            g.setFont(smallFont);
            g.drawString("(click to start)", (PANEL_WIDTH/2)-20, (PANEL_HEIGHT/2)-50);
        }

        @Override
        public synchronized void paintComponent(Graphics g) {
            super.paintComponent(g);
            int count = 0;
            //drawStartScreen(g);
            if (gameOver) {
                drawStartScreen(g);
            }
            for (Food foo : foodlist) {
                foo.drawFood(g);
            }
            for (Snake s : snakelist) {
//                if (count == 0){
//                    g.drawImage(snakehead,PANEL_WIDTH/2,PANEL_HEIGHT/2, drawpanel);
//                    count++;
//                } else {
                s.drawSnake(g);
                //}

            }

        }
    }

    public static void main(String[] args) {

        System.out.println("============SNAKE===============");
        SnakeGameGui game = new SnakeGameGui();
        frame = new JFrame("SNAKE GAME GUI");
        frame.setFocusable(true);
        //add a keylistener
        frame.addKeyListener(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(game);
        //gets the dimensions for screen width and height to calculate center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        //System.out.println(screenHeight);
        frame.pack();             //resize frame apropriately for its content
        //positions frame in center of screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);

    }
}
