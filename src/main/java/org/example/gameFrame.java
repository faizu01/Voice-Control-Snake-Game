package org.example;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class gameFrame extends JFrame {
    gameFrame() {

        gamePanel panel = new gamePanel();

        ImageIcon icon = new ImageIcon("D:\\Swing\\Images\\snake.png");
        this.setIconImage(icon.getImage()); // replace javaframe icon by my image
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Snake Game");
        this.setResizable(false);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
