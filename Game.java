import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;

import javax.swing.JFrame;

import java.awt.*;

public class Game extends Canvas implements Runnable, KeyListener {
    private Thread thread;
    public static int WIDTH;
    public static int HEIGHT;

    public Game() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = screenSize.width;
        HEIGHT = screenSize.height;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(getMinimumSize());
        setMaximumSize(getMaximumSize());

        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
    }

    public void start() {
        thread = new Thread(this, "Game Thread");
        thread.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame gameFrame = new JFrame("Elemental Stickman Combat");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(true);
        gameFrame.add(game);
        gameFrame.pack();
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setVisible(true);
    }
}