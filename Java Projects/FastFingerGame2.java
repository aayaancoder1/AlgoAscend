import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

// Thread for the red -> green delay
class DelayThread extends Thread
{
    int delay;
    FastFingerGame2 game;

    DelayThread(int delay, FastFingerGame2 game)
    {
        this.delay = delay;
        this.game = game;
    }

    public void run()
    {
        try {
            Thread.sleep(delay);
            game.goGreen();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

public class FastFingerGame2 extends JFrame implements KeyListener
{
    CardLayout card = new CardLayout();
    JPanel mainPanel = new JPanel(card);

    // ------ Screen 1 ------
    JPanel setupPanel = new JPanel(new FlowLayout());
    JTextField playerInput = new JTextField(5);
    JButton startBtn = new JButton("Start");

    // ------ Screen 2 ------
    JPanel assignPanel = new JPanel(new BorderLayout());
    JTextArea assignArea = new JTextArea();
    JButton readyBtn = new JButton("READY");

    // ------ Screen 3 ------
    JPanel gamePanel = new JPanel(new BorderLayout());
    JPanel colorPanel = new JPanel();
    JLabel gameLabel = new JLabel("Wait for GREEN...", SwingConstants.CENTER);

    // ------ Screen 4 ------
    JPanel resultPanel = new JPanel(new BorderLayout());
    JTextArea resultArea = new JTextArea();
    JButton restartBtn = new JButton("Restart");
    JButton quitBtn = new JButton("Quit");

    int numPlayers;
    char keys[];
    String names[];
    boolean green = false;

    public FastFingerGame2()
    {
        setTitle("Fast Finger Game");
        setSize(550, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupScreen1();
        setupScreen2();
        setupScreen3();
        setupScreen4();

        add(mainPanel);
        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

    // ---------------- SCREEN 1 ----------------
    void setupScreen1()
    {
        setupPanel.add(new JLabel("Number of Players:"));
        setupPanel.add(playerInput);
        setupPanel.add(startBtn);

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupGame();
            }
        });

        mainPanel.add(setupPanel, "setup");
    }

    // ---------------- SCREEN 2 ----------------
    void setupScreen2()
    {
        assignArea.setEditable(false);

        assignPanel.add(new JScrollPane(assignArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(readyBtn);
        assignPanel.add(bottom, BorderLayout.SOUTH);

        readyBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startRound();
                card.show(mainPanel, "game");
                requestFocusInWindow();
            }
        });

        mainPanel.add(assignPanel, "assign");
    }

    // ---------------- SCREEN 3 ----------------
    void setupScreen3()
    {
        gamePanel.add(gameLabel, BorderLayout.NORTH);
        gamePanel.add(colorPanel, BorderLayout.CENTER);

        colorPanel.setBackground(Color.RED);
        gameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(gamePanel, "game");
    }

    // ---------------- SCREEN 4 ----------------
    void setupScreen4()
    {
        resultArea.setEditable(false);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(restartBtn);
        bottom.add(quitBtn);

        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        resultPanel.add(bottom, BorderLayout.SOUTH);

        restartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                assignArea.append("\n\n--- New Round ---\n");
                card.show(mainPanel, "assign");
                requestFocusInWindow();
            }
        });

        quitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainPanel.add(resultPanel, "result");
    }

    // ---------------- SETUP LOGIC ----------------
    void setupGame()
    {
        try {
            numPlayers = Integer.parseInt(playerInput.getText().trim());

            if(numPlayers < 2) {
                JOptionPane.showMessageDialog(this, "Enter 2 or more players.");
                return;
            }

            keys = new char[numPlayers];
            names = new String[numPlayers];

            for(int i = 0; i < numPlayers; i++)
            {
                keys[i] = (char)('A' + i);

                String name = JOptionPane.showInputDialog(
                        this,
                        "Enter name for player using key " + keys[i]);

                if(name == null || name.trim().equals(""))
                    name = "Player" + (i+1);

                names[i] = name;
            }

            String text = "Assigned Keys:\n\n";
            for(int i = 0; i < numPlayers; i++)
                text += keys[i] + " - " + names[i] + "\n";

            assignArea.setText(text);
            card.show(mainPanel, "assign");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid number.");
        }
    }

    // ---------------- GAME START ----------------
    void startRound()
    {
        green = false;
        colorPanel.setBackground(Color.RED);
        gameLabel.setText("Wait for GREEN...");

        int delay = new Random().nextInt(2000) + 1000;

        DelayThread dt = new DelayThread(delay, this);
        dt.start();
    }

    void goGreen()
    {
        green = true;
        colorPanel.setBackground(Color.GREEN);
        gameLabel.setText("GO!");
    }

    // ---------------- KEY PRESS ----------------
    public void keyPressed(KeyEvent e)
    {
        if(!green) return;

        char key = Character.toUpperCase(e.getKeyChar());

        for(int i = 0; i < numPlayers; i++)
        {
            if(keys[i] == key)
            {
                resultArea.setText("Winner: " + names[i] + " (Key " + key + ")");
                green = false;
                card.show(mainPanel, "result");
                return;
            }
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    public static void main(String[] args)
    {
        new FastFingerGame2();
    }
}