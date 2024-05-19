package game.scene;

import game.Game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.*;
import java.lang.StringBuffer;
import java.awt.Graphics;

public class TitleScreen extends JPanel {
    public HashMap<String, JButton> buttons = new HashMap<String, JButton>();
    public StringBuffer currentButton = new StringBuffer();
    private Image background = Toolkit.getDefaultToolkit().getImage("game/assets/images/Title.png")
            .getScaledInstance(Game.WINDOW_WIDTH, Game.WINDOW_HEIGHT, Image.SCALE_SMOOTH);;

    public TitleScreen() {
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
        this.setVisible(true);

        JLabel title = new JLabel("ELEMENTAL STICKMAN COMBAT");
        title.setFont(new Font("Arial", Font.BOLD, 50)); // Set the font size and style
        title.setHorizontalAlignment(JLabel.CENTER); // Center align the text
        title.setForeground(Color.WHITE); // Set the text color

        this.add(title);

        JButton startButton = new JButton("Start");
        JButton singlePlayerButton = new JButton("Single Player");
        JButton multiPlayerButton = new JButton("Multi Player");
        JButton devButton = new JButton("Dev");
        String devButtonToolTip = "This button is for developers only. It will not be available in the final game.";
        devButton.setToolTipText(devButtonToolTip);

        buttons.put("start", startButton);
        buttons.put("singlePlayer", singlePlayerButton);
        buttons.put("multiPlayer", multiPlayerButton);
        buttons.put("dev", devButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.WHITE);
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setVisible(false);
        this.add(exitButton);

        for (JButton button : buttons.values()) {
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setBorderPainted(false);
            this.add(button);
            button.addActionListener(e -> {
                currentButton.delete(0, currentButton.length());
                currentButton.append(button.getText());
                for (JButton b : buttons.values()) {
                    b.setVisible(false);
                }
                exitButton.setVisible(true);
                System.out.println(currentButton.toString());
            });
        }

        exitButton.addActionListener(e -> {
            currentButton.delete(0, currentButton.length());
            for (JButton b : buttons.values()) {
                b.setVisible(true);
            }
            exitButton.setVisible(false);
            System.out.println("Exit");
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
}
