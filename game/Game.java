package game;

import game.mechanics.LaMeanEngine;
import game.objectclasses.Workspace;
import game.scene.TitleScreen;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

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
            System.out.println("WORKSPACE FAILED TO LOAD");
        }

        this.setTitle(WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // Set resizable to false to maintain fixed size
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); // Set preferred size to maximum screen size

        titleScreen = new TitleScreen();
        gamePanel = new GamePanel(this);
        physicsEngine = new LaMeanEngine(this);
        add(titleScreen);
        refresh();
        titleScreen.buttons.get("play").addActionListener(e -> {
            remove(titleScreen);
            add(gamePanel);
            refresh();

            gamePanel.start();
        });
        refresh();
    }

    public void endGame(String winner) {
        new Thread(() -> {
            try {
                System.out.println(winner + " wins!");
                refresh();

                Thread.sleep(3000);
                System.exit(0);
                refresh();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        new Game();
    }
}