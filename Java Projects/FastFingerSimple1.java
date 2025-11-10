import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class FastFingerSimple extends JFrame implements KeyListener {

    private JPanel colorPanel = new JPanel();
    private JLabel info = new JLabel("Enter number of players and press Start");
    private JTextField playerInput = new JTextField(5);
    private JButton startBtn = new JButton("Start");

    private boolean green = false;
    private int numPlayers;
    private char[] keys;

    public FastFingerSimple() {
        setTitle("Fast Finger Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(info, BorderLayout.NORTH);
        add(colorPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Players:"));
        bottom.add(playerInput);
        bottom.add(startButton);
        add(bottom, BorderLayout.SOUTH);

        startButton.addActionListener(e -> setupGame());

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    private void setupGame() {
        try {
            numPlayers = Integer.parseInt(playerInput.getText().trim());    //parsing input
            if (numPlayers <= 0) throw new Exception();

            keys = new char[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                keys[i] = (char) ('A' + i);
            }

            info.setText("Players keys: " + new String(keys) + " | Wait for Green...");

            startRound();
            
            // what dis?
            requestFocusInWindow();
            SwingUtilities.invokeLater(this::requestFocusInWindow);

        } catch (Exception e) {
            info.setText("Invalid number.");
        }
    }

    private void startRound() {
        green = false;
        colorPanel.setBackground(Color.RED);

        int delay = new Random().nextInt(2000) + 1000;
        // 1000 ms to 3000 ms

        Timer t = new Timer(delay, e -> {
            green = true;
            colorPanel.setBackground(Color.GREEN);
            info.setText("GO! Press your key!");
        });

        t.setRepeats(false);
        t.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!green) return;

        char key = Character.toUpperCase(e.getKeyChar());

        for (char k : keys) {
            if (k == key) {
                info.setText("Winner: Player " + key);
                green = false; 
                return;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new FastFingerSimple();
    }
}
