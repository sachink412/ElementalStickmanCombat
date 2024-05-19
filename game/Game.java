package game;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import game.objectclasses.Part;
import game.objectclasses.Workspace;
import game.scene.TitleScreen;

/**
 * The main class representing the game.
 * This class extends the JFrame class to create the game window.
 */
public class Game extends JFrame {
    private final String WINDOW_TITLE = "Elemental Stickman Combat";

    // Get the maximum wdth and height of the screen
    public static final int WINDOW_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getMaximumWindowBounds().width;
    public static final int WINDOW_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getMaximumWindowBounds().height;

    public final int COLUMNS = 16;
    public final int ROWS = 9;

    public TitleScreen titleScreen;
    public GamePanel gamePanel;
    public Workspace workspace;
    public LaMeanEngine physicsEngine;

    public void refresh() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        revalidate();
        repaint();
    }

    public Game() {
        try {
            this.workspace = (Workspace) Instance.create("Workspace");
        } catch (Exception e) {
            System.out.println("Workspace failed to load");
        }

        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Set resizable to false to maintain fixed size
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); // Set preferred size to maximum screen size

        titleScreen = new TitleScreen();
        gamePanel = new GamePanel(this); // Pass 'this' reference to GamePanel so it can access Game object
        physicsEngine = new LaMeanEngine(this);
        add(titleScreen);
        refresh();

        titleScreen.buttons.get("dev").addActionListener(e -> {
            System.out.println("CLICKEDD");
            remove(titleScreen);
            add(gamePanel);
            refresh();
        });

        refresh();
        gamePanel.startGameThread();

        try {
            Part part = (Part) Instance.create("Part", workspace);
            part.size = new Vector2D(100, 100);
            part.position = new Vector2D(200, 200);
            part.partType = "Triangle";
            part.setColor("Yellow");

            Part part2 = (Part) Instance.create("Part", workspace);
            part2.size = new Vector2D(100, 100);
            part2.position = new Vector2D(50, 50);
            part2.partType = "Rectangle";
            part2.setColor("Red");

            Part part3 = (Part) Instance.create("Part", workspace);
            part3.size = new Vector2D(100, 100);
            part3.position = new Vector2D(69, 50);
            part3.partType = "Rectangle";
            part3.setColor("Blue");

            Part intertest1 = (Part) Instance.create("Part", workspace);
            intertest1.size = new Vector2D(10, 10);
            intertest1.position = new Vector2D(150, 150);
            intertest1.partType = "Ellipse";
            intertest1.setColor("Green");

            Part intertest2 = (Part) Instance.create("Part", workspace);
            intertest2.size = new Vector2D(10, 10);
            intertest2.position = new Vector2D(69, 150);
            intertest2.partType = "Ellipse";
            intertest2.setColor("Green");

            Part intertest3 = (Part) Instance.create("Part", workspace);
            intertest3.size = new Vector2D(10, 10);
            intertest3.position = new Vector2D(150, 50);
            intertest3.partType = "Ellipse";
            intertest3.setColor("Green");

            part2.anchored = true;
            part3.anchored = true;

            intertest1.anchored = true;
            intertest2.anchored = true;
            intertest3.anchored = true;

            Part ground = (Part) Instance.create("Part", workspace);
            ground.size = new Vector2D(WINDOW_WIDTH, 150);
            ground.position = new Vector2D(0, WINDOW_HEIGHT - 50);
            ground.partType = "Rectangle";
            ground.setColor("Green");
            ground.anchored = true;
        } catch (Exception e) {
            System.out.println("Part failed to load");
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}