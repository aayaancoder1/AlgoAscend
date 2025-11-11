import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class fastg1 extends JFrame implements KeyListener, Runnable {

    JPanel panel;
    boolean ready = false;
    boolean gameOver = false;

    public fastg1() {
        setLayout(new FlowLayout());

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setBackground(Color.red);
        add(panel);

        addKeyListener(this);
        setFocusable(true);

        setTitle("Fastest Finger");
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
            int t = 2000 + (int)(Math.random() * 3000);
            Thread.sleep(t);
            panel.setBackground(Color.green);
            ready = true;
        } 
        catch (Exception e) {}
    }

    public void keyPressed(KeyEvent e) {
        if (gameOver) return;

        char c = Character.toUpperCase(e.getKeyChar());

        if (!ready) {
            if (c == 'A') {
                JOptionPane.showMessageDialog(this, "Player 1 pressed early!\nPlayer 2 wins!");
            } else if (c == 'L') {
                JOptionPane.showMessageDialog(this, "Player 2 pressed early!\nPlayer 1 wins!");
            }
            finish();
            return;
        }

        if (c == 'A') {
            JOptionPane.showMessageDialog(this, "Player 1 wins!");
            finish();
        } else if (c == 'L') {
            JOptionPane.showMessageDialog(this, "Player 2 wins!");
            finish();
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    void finish() {
        gameOver = true;
        dispose();
    }

    public static void main(String[] args) {
        new fastg1();
    }
}