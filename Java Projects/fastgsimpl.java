import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class fastgsimpl extends JFrame implements KeyListener, Runnable {

    CardLayout cl = new CardLayout();
    JPanel cards = new JPanel(cl);
    JPanel intro = new JPanel();
    JPanel game = new JPanel();

    boolean ready = false;
    boolean over = false;

    public fastgsimpl() {

        // ----- INTRO SCREEN -----
        intro.add(new JLabel("Click to Start"));
        intro.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cl.show(cards, "game");
                new Thread(fastgsimpl.this).start();
            }
        });

        // ----- GAME SCREEN -----
        game.setBackground(Color.red);
        game.setPreferredSize(new Dimension(300, 200));

        cards.add(intro, "intro");
        cards.add(game, "game");

        add(cards);
        addKeyListener(this);
        setFocusable(true);

        setSize(400, 300);
        setTitle("Fastest Finger");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void run() {
        try {
            Thread.sleep(2000 + (int)(Math.random() * 3000));
            game.setBackground(Color.green);
            ready = true;
        } catch (Exception e) {}
    }

    public void keyPressed(KeyEvent e) {
        if (over) return;
        char c = Character.toUpperCase(e.getKeyChar());

        if (!ready) {
            if (c == 'A') JOptionPane.showMessageDialog(this, "Player 1 pressed early! Player 2 wins!");
            if (c == 'L') JOptionPane.showMessageDialog(this, "Player 2 pressed early! Player 1 wins!");
            finish();
            return;
        }

        if (c == 'A') { JOptionPane.showMessageDialog(this, "Player 1 wins!"); finish(); }
        if (c == 'L') { JOptionPane.showMessageDialog(this, "Player 2 wins!"); finish(); }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    void finish() {
        over = true;
        dispose();
    }

    public static void main(String[] args) {
        new fastgsimpl();
    }
}
