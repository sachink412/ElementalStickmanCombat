package game;

import game.map.MapUtility;
import game.mechanics.Debris;
import game.mechanics.LaMeanEngine;
import game.mechanics.Vector2D;
import game.objectclasses.Part;
import game.sound.SoundUtility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JProgressBar;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * The GamePanel class represents the panel where the game is displayed and
 * played. It extends the JPanel class and implements the Runnable interface.
 */
public class GamePanel extends JPanel implements Runnable {
    private final int FPS = 60;

    private final MapUtility MAP = new MapUtility();
    private final Elements ELEMENTS = new Elements();

    private KeyHandler keyHandler = new KeyHandler();
    public Thread gameThread;
    public LaMeanEngine engine;
    public SoundUtility soundUtility = new SoundUtility();

    private Image backgroundImage;
    private JLayeredPane layeredPane;
    private JPanel backgroundPanel;

    Player player;
    Bot bot;

    private JProgressBar playerHealthBar;
    private JProgressBar botHealthBar;

    public Game game;

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

        backgroundImage = MAP.getBackgroundImage();
        engine = new LaMeanEngine(this.game);

        // Create the background panel
        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT);
        backgroundPanel.setOpaque(false);
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        player = new Player(this, keyHandler, Color.BLACK, game.workspace);
        bot = new Bot(this, new KeyInfo(), Color.WHITE, game.workspace);

        playerHealthBar = new JProgressBar(0, player.stickman.health);
        playerHealthBar.setValue(player.stickman.health);
        playerHealthBar.setStringPainted(true);
        this.add(playerHealthBar);

        botHealthBar = new JProgressBar(0, bot.stickman.health);
        botHealthBar.setValue(bot.stickman.health);
        botHealthBar.setStringPainted(true);
        this.add(botHealthBar);

        try {
            Part ground = (Part) Instance.create("Part", game.workspace);
            ground.size = new Vector2D(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT * 0.25);
            ground.name = "Ground";
            ground.position = new Vector2D(0, Game.WINDOW_HEIGHT - ground.size.y);
            ground.partType = "Rectangle";
            ground.color = Color.BLACK;
            ground.anchored = true;
        } catch (Exception e) {
            System.out.println("Part failed to load");
        }

        soundUtility.play("bgm");
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

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        if (player.stickman.health < 0 || bot.stickman.health < 0) {
            if (player.stickman.health < 0) {
                game.endGame("Bot");
            } else {
                game.endGame("Player");
            }
        }

        player.update();
        bot.update();
        Debris.updateDebris();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.requestFocusInWindow();
        Graphics2D g2D = (Graphics2D) g;

        backgroundPanel.paint(g2D);
        g2D.drawImage(backgroundImage, 0, 0, null);

        int healthBarWidth = Game.WINDOW_WIDTH / 4;
        int healthBarHeight = Game.WINDOW_HEIGHT / 20;

        // Player Health Bar
        playerHealthBar.setValue(player.stickman.health);
        playerHealthBar.setBounds(0, 0, healthBarWidth, healthBarHeight);
        playerHealthBar.setFont(new Font("Verdana", Font.BOLD, 20));
        playerHealthBar.setForeground(Color.GREEN);
        playerHealthBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        playerHealthBar.setBackground(Color.DARK_GRAY);
        playerHealthBar.paint(g2D);

        // Bot Health Bar
        botHealthBar.setValue(bot.stickman.health);
        botHealthBar.setBounds(Game.WINDOW_WIDTH - healthBarWidth, 0, healthBarWidth, healthBarHeight);
        botHealthBar.setFont(new Font("Verdana", Font.BOLD, 20));
        botHealthBar.setForeground(Color.RED);
        botHealthBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        botHealthBar.setBackground(Color.DARK_GRAY);
        botHealthBar.paint(g2D);

        GameObject[] objects = game.workspace.getDescendants();
        for (GameObject object : objects) {
            object.draw(g2D);
        }

        // Draw the placeholder circles for the element attacks
        int circleDiameter = 100;
        int circleSpacing = 10;
        int circleCount = Element.values()[0].getAttacks().size();
        int startX = getWidth() - (circleCount * (circleDiameter + circleSpacing));
        int y = getHeight() - circleDiameter - circleSpacing;

        g.setColor(Color.GRAY);
        Image[] attackImages = ELEMENTS.elementAndAttacks.get(player.stickman.element);
        g.setFont(new Font("Verdana", Font.BOLD, 30));
        g.setColor(Color.WHITE);

        for (int i = 0; i < circleCount; i++) {
            int x = startX + i * (circleDiameter + circleSpacing);
            g.drawOval(x, y, circleDiameter, circleDiameter);
            g.drawImage(attackImages[i], x, y, circleDiameter, circleDiameter, null);
            g.drawString(Integer.toString(i + 1), x + circleDiameter / 2, y + circleDiameter / 2);
        }

        g2D.setColor(Color.GREEN);
    }
}