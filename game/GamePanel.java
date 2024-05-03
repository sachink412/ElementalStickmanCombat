package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import game.map.RenderedMap;

public class GamePanel extends JPanel implements Runnable {
    final int SIZE = 16;
    final int SCALE = 4;

    public final int TILE_SIZE = SIZE * SCALE;
    final int MAX_COLUMNS = 16;
    final int MAX_ROWS = 9;
    final int SCREEN_WIDTH = TILE_SIZE * MAX_COLUMNS;
    final int SCREEN_HEIGHT = TILE_SIZE * MAX_ROWS;

    private RenderedMap map;

    private final int GRAVITY = 5;

    KeyHandler kH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, kH, Color.WHITE);

    final int FPS = 60;
    private Game game;

    public GamePanel(Game game) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(kH);
        this.setFocusable(true);
        this.game = game;
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

        if (player.y < groundHeight) {
            player.y += GRAVITY;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        if (map != null && map.getBackground() != null) {
            // g.drawImage(map.getBackground(), 0, 0, this);
        }

        player.draw(g);
        g2D.dispose();

    }
}