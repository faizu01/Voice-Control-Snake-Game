package org.example;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class gamePanel extends JPanel implements ActionListener {
    static  int SCREEN_WIDTH = 700;
    static  int SCREEN_HEIGHT = 700;
    static  int UNIT_SIZE = 25; // How big we want objects in this game
    static  int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; // how many objects we can fit on the //
                                                                              // screen
    static  int DELAY = 150;
    final int[] X = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 4;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    public static boolean running = false;
    Timer timer;
    Random random;

    gamePanel() {

        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.setBackground(Color.black);
        myKeyAdapter adapter = new myKeyAdapter(); //instance of mykeyadapter method
        this.addKeyListener(adapter);// add keylistener to panel so that game will run

        startGame();

    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {


            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            /* Drawing snake */
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    // i==0 maeans we are dealing with head of snake
                    g.setColor(Color.yellow);
                    g.fillOval(X[i], y[i], UNIT_SIZE, UNIT_SIZE);

                } else {

                    g.setColor(new Color(5, 102, 8));
                    g.fillOval(X[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            /* Score display at top of screen */
            g.setColor(Color.red);
            g.setFont(new Font("MV Boli", Font.BOLD, 25));
            g.drawString("Score: ", 550, 40);
            /* Score */
            g.setColor(Color.green);
            g.setFont(new Font("MV Boli", Font.BOLD, 25));
            g.drawString(String.valueOf(appleEaten), 640, 40);
        }

        else {
            gameOver(g);
        }

    }

    public void newApple() {
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            X[i] = X[i - 1]; // means moving one unit in x direction
            y[i] = y[i - 1];// moving one step in y direction
        }

        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                X[0] = X[0] + UNIT_SIZE;
                break;
            case 'L':
                X[0] = X[0] - UNIT_SIZE;
                break;
        }
    }

    public void eatApple() {
        if (X[0] == appleX && y[0] == appleY) {
            appleEaten++;
            bodyParts += 1;
            newApple();
        }
    }

    public void checkCollision() {
        for (int i = bodyParts; i > 0; i--) {
            // snake touch its body
            if (X[0] == X[i] && y[0] == y[i]) {
                running = false;
            }
            // touch its left border
            if (X[0] < 0) {
                running = false;
            }
            // touch its right border

            if (X[0] > SCREEN_WIDTH) {
                running = false;
            }
            // checking for up and down border
            if (y[0] < 0 || y[0] > SCREEN_HEIGHT) {
                running = false;
            }

            if (!running) {
                timer.stop();
            }

        }

    }

    public void gameOver(Graphics g) {
        // setting game over text in middle of panel
        g.setColor(Color.red);
        g.setFont(new Font("MV Boli", Font.BOLD, 70));
        g.drawString("Game Over", 150, 350);
        // setting score at end in panel
        g.setColor(Color.green);
        g.setFont(new Font("Font.SERIF", Font.PLAIN, 40));
        g.drawString("Score: " + appleEaten, 280, 450);

    }

    //THis is not working  some error
//    public void setDirection(char newDirection) {
//        // Check if the new direction is valid to avoid conflicting directions
//        if (newDirection == 'L' && direction != 'R' ||
//                newDirection == 'R' && direction != 'L' ||
//                newDirection == 'U' && direction != 'D' ||
//                newDirection == 'D' && direction != 'U') {
//            direction = newDirection;
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            eatApple();
            checkCollision();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';

                    }
                    break;

            }
        }

    }

}
