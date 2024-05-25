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
        if (code == KeyEvent.VK_W) {
            this.up = true;
        }
        if (code == KeyEvent.VK_A) {
            this.left = true;
            this.leftRight = false;
        }
        if (code == KeyEvent.VK_S) {
            this.down = true;
        }
        if (code == KeyEvent.VK_D) {
            this.right = true;
            this.leftRight = true;
        }
        if (code == KeyEvent.VK_Q) {
            this.q = true;
        }
        if (code == KeyEvent.VK_1) {
            this.one = true;
        }
        if (code == KeyEvent.VK_2) {
            this.two = true;
        }
        if (code == KeyEvent.VK_3) {
            this.three = true;
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
        if (code == KeyEvent.VK_Q) {
            this.q = false;
        }
        if (code == KeyEvent.VK_1) {
            this.one = false;
        }
        if (code == KeyEvent.VK_2) {
            this.two = false;
        }
        if (code == KeyEvent.VK_3) {
            this.three = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // The keyTyped() method is not used in this program.
    }
}
