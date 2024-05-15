package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;
import javax.imageio.ImageIO;

import game.map.MapUtility;

/**
 * The GamePanel class represents the panel where the game is displayed and
 * played. It extends the JPanel class and implements the Runnable interface.
 */
public class GamePanel extends JPanel implements Runnable {
    public final int SIZE = 16;
    public final int SCALE = 4;
    public final int TILE_SIZE = SIZE * SCALE;
    public final int MAX_COLUMNS = 16;
    public final int MAX_ROWS = 9;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_COLUMNS;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROWS;
    private final int FPS = 60;

    private MapUtility map;
    private KeyHandler keyHandler = new KeyHandler();
    public Thread gameThread;

    private final int GRAVITY = 5;

    Player player = new Player(this, keyHandler, Color.WHITE);

    private Game game;
    // private Image backgroundMap = map.getBackgroundImage();

    public GamePanel(Game game) {
        this.game = game;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        map = new MapUtility();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private int groundHeight = SCREEN_HEIGHT - TILE_SIZE;

    public void update() {
        player.update();

        // Apply gravity
        if (player.y < groundHeight) {
            player.y += GRAVITY;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        GameObject[] objs = game.workspace.getDescendants();
        for (GameObject obj : objs) {
            obj.draw(g2D);
        }

        // g.drawImage(map.getBackgroundImage(), 0, 0, null);

        player.draw(g);
        g2D.dispose();
    }
}