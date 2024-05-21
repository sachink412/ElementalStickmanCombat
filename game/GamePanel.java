package game;

import game.LaMeanEngine.CollisionManager;
import game.map.MapUtility;
import game.objectclasses.Part;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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

    private final MapUtility map = new MapUtility();
    private KeyHandler keyHandler = new KeyHandler();
    public Thread gameThread;

    public LaMeanEngine engine;
    Player player;

    private Image backgroundImage;

    private JLayeredPane layeredPane;
    private JPanel backgroundPanel;
    private JPanel gameObjectsPanel;

    private Game game;

    public GamePanel(Game game) {
        this.game = game;

        this.setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.requestFocus();

        // Layered pane to hold the background and game objects
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT));
        this.add(layeredPane);

        backgroundImage = map.getBackgroundImage();
        engine = new LaMeanEngine(this.game);

        // Create the background panel
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        backgroundPanel.setOpaque(false);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        player = new Player(this, keyHandler, Color.WHITE, game.workspace);

        try {
            Part part = (Part) Instance.create("Part", game.workspace);
            part.size = new Vector2D(100, 100);
            part.position = new Vector2D(0,0);
            part.partType = "Ellipse";
            part.setColor("Yellow");



            Part ground = (Part) Instance.create("Part", game.workspace);
            ground.size = new Vector2D(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT * 0.125);
            ground.position = new Vector2D(0, Game.WINDOW_HEIGHT - ground.size.y);
            ground.partType = "Rectangle";
            ground.color = Color.BLACK;
            ground.anchored = true;

        } catch (Exception e) {
            System.out.println("Part failed to load");
        }
    }

    @Override
    public void run() {
        double drawInterval = 1e9 / FPS;
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
                engine.step();
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

    public void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        backgroundPanel.paint(g2d);
        g2d.drawImage(backgroundImage, 0, 0, null);

        GameObject[] objects = game.workspace.getDescendants();
        for (GameObject object : objects) {
            object.draw(g2d);
        }

        player.draw(g2d);
        g2d.drawString("Intersections", 100, 100);
        g2d.setColor(Color.GREEN);
    }
}