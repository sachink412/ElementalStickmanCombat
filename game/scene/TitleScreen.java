package game.scene;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class TitleScreen extends JPanel {
    public HashMap<String, JButton> buttons = new HashMap<String, JButton>();

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
        playButton.setOpaque(true);
        playButton.setBorderPainted(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
