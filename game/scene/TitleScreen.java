package game.scene;

import game.Game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.*;
import java.lang.StringBuffer;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class TitleScreen extends JPanel {
    public HashMap<String, JButton> buttons = new HashMap<String, JButton>();
    public StringBuffer currentButton = new StringBuffer();

    public TitleScreen() {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
        this.setVisible(true);

        GridBagConstraints alignConstraints = new GridBagConstraints();
        alignConstraints.gridwidth = GridBagConstraints.REMAINDER;
        alignConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("ELEMENTAL STICKMAN COMBAT", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        this.add(title, alignConstraints);

        JButton playButton = new JButton("PLAY");
        playButton.setPreferredSize(new Dimension(200, 100));
        playButton.setFont(new Font("Verdana", Font.BOLD, 20));
        buttons.put("play", playButton);
        this.add(playButton);

        playButton.setBackground(Color.BLACK);
        playButton.setForeground(Color.WHITE);

        playButton.addActionListener(e -> {
            playButton.setBackground(Color.WHITE);
            playButton.setForeground(Color.BLACK);
            playButton.setOpaque(true);
            playButton.setBorderPainted(false);

        });

        // for (JButton button : buttons.values()) {
        // button.setBackground(Color.BLACK);
        // button.setForeground(Color.WHITE);
        // button.setOpaque(true);
        // button.setBorderPainted(false);
        // this.add(button);

        // button.addActionListener(e -> {
        // currentButton.delete(0, currentButton.length());
        // currentButton.append(button.getText());
        // for (JButton b : buttons.values()) {
        // b.setVisible(false);
        // }
        // System.out.println(currentButton.toString());
        // });
        // }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
