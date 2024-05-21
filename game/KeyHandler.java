package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class implements the KeyListener interface to handle keyboard
 * input for the game.
 * 
 * The KeyHandler updates the current state for the WASD (up, down, left and
 * right) keys, and provides access to their state.
 */
public class KeyHandler extends KeyInfo implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        System.out.println(code);
        if (code == KeyEvent.VK_W) {
            this.up = true;
        }
        if (code == KeyEvent.VK_A) {
            this.left = true;
        }
        if (code == KeyEvent.VK_S) {
            this.down = true;
        }
        if (code == KeyEvent.VK_D) {
            this.right = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            this.up = false;
        }
        if (code == KeyEvent.VK_A) {
            this.left = false;
        }
        if (code == KeyEvent.VK_S) {
            this.down = false;
        }
        if (code == KeyEvent.VK_D) {
            this.right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
