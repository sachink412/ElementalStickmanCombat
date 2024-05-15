package game;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

import game.scene.TitleScreen;

/**
 * The main class representing the game.
 * This class extends the JFrame class to create the game window.
 */
public class Game extends JFrame {
    private final String WINDOW_TITLE = "Elemental Stickman Combat";
    private final int WINDOW_WIDTH = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    private final int WINDOW_HEIGHT = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

    public final int COLUMNS = 16;
    public final int ROWS = 9;

    private TitleScreen titleScreen;

    private GamePanel gamePanel;

    public Workspace workspace = new Workspace();

    public void refresh() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        revalidate();
        repaint();
    }

    public Game() {
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Set resizable to false to maintain fixed size
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); // Set preferred size to maximum screen size

        titleScreen = new TitleScreen();
        gamePanel = new GamePanel(this); // Pass 'this' reference to GamePanel so it can access Game object
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
    }

    public static void main(String[] args) {
        new Game();
    }
}