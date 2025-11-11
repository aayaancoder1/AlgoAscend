import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class fastg2 extends JFrame implements KeyListener, Runnable {

    CardLayout cl;
    JPanel mainPanel;
    JPanel introPanel;
    JPanel gamePanel;

    boolean ready = false;
    boolean gameOver = false;

    public fastg2() {
        cl = new CardLayout();
        mainPanel = new JPanel(cl);

        introPanel = new JPanel();
        introPanel.setLayout(new FlowLayout());

        // this is the first screen ie. the intro screen with instructions
        JTextArea introText = new JTextArea(
            "FASTEST FINGER GAME\n\n" +
            "Player 1 key: A\n" +
            "Player 2 key: L\n\n" +
            "Screen will turn GREEN after a random delay.\n" +
            "First player to hit their key wins.\n" +
            "Pressing early = instant loss.\n\n" +
            "Click anywhere to begin..."
        );
        introText.setEditable(false);
        introText.setFocusable(false);
        introText.setBackground(new Color(240,240,240));

        introPanel.add(introText);

        // waiting for the mouse click to change to another card
        introPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cl.show(mainPanel, "game");
                startGameThread();
            }
        });

        // this is the game screen
        gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(300, 200));
        gamePanel.setBackground(Color.red);

        // adding panels to card layout
        mainPanel.add(introPanel, "intro");
        mainPanel.add(gamePanel, "game");

        add(mainPanel);
        addKeyListener(this);
        setFocusable(true);

        setTitle("Fastest Finger");
        setSize(400, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void startGameThread() {
        Thread t = new Thread(this);
        t.start();
    }

// this is used for the randomness for the color change
    public void run() {
        try {
            int t = 2000 + (int)(Math.random() * 3000);
            Thread.sleep(t);
            gamePanel.setBackground(Color.green);
            ready = true;
        } catch (Exception e) {}
    }

    public void keyPressed(KeyEvent e) {
        if (gameOver) return;

        char c = Character.toUpperCase(e.getKeyChar());

        // cxEarly press
        if (!ready) {
            if (c == 'A') {
                JOptionPane.showMessageDialog(this, "Player 1 pressed early!\nPlayer 2 wins!");
            } else if (c == 'L') {
                JOptionPane.showMessageDialog(this, "Player 2 pressed early!\nPlayer 1 wins!");
            }
            finish();
            return;
        }

        // Correct press
        if (c == 'A') {
            JOptionPane.showMessageDialog(this, "Player 1 wins!");
            finish();
        } 
        else if (c == 'L') {
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
        new fastg2();
    }
}