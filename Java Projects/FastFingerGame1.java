import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class DelayThread extends Thread
{
    int delay;
    FastFingerGame1 game;

    DelayThread(int delay, FastFingerGame1 game)
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

public class FastFingerGame1 extends JFrame implements KeyListener
{
    JPanel colorPanel = new JPanel();
    JTextArea display = new JTextArea();
    JTextField playerInput = new JTextField(5);
    JButton startBtn = new JButton("Start Game");

    int numPlayers;
    char keys[];
    String names[];
    boolean green = false;

    public FastFingerGame1()
    {
        setTitle("Fast Finger Game");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        display.setEditable(false);

        setLayout(new BorderLayout());
        add(new JScrollPane(display), BorderLayout.NORTH);
        add(colorPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(new JLabel("Number of Players:"));
        bottom.add(playerInput);
        bottom.add(startBtn);
        add(bottom, BorderLayout.SOUTH);

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setupGame();
            }
        });

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);

        display.setText("Enter number of players (min 2) and press Start.\n");
    }

    void setupGame()
    {
        try {
            numPlayers = Integer.parseInt(playerInput.getText().trim());

            if(numPlayers < 2) {
                display.setText("Please enter 2 or more players.\n");
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

            text += "\nWait for GREEN...\n";
            display.setText(text);

            startRound();
            requestFocusInWindow();

        } catch (Exception e) {
            display.setText("Invalid number.\n");
        }
    }

    void startRound()
    {
        green = false;
        colorPanel.setBackground(Color.RED);

        int delay = new Random().nextInt(2000) + 1000;

        DelayThread dt = new DelayThread(delay, this);
        dt.start();
    }

    void goGreen()
    {
        green = true;
        colorPanel.setBackground(Color.GREEN);
        display.append("\nGO! Press your key!\n");
    }

    public void keyPressed(KeyEvent e)
    {
        if(!green) return;

        char key = Character.toUpperCase(e.getKeyChar());

        for(int i = 0; i < numPlayers; i++)
        {
            if(keys[i] == key)
            {
                display.setText("Winner: " + names[i] + " (Key " + key + ")\n");
                green = false;
                return;
            }
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    public static void main(String[] args)
    {
        new FastFingerGame1();
    }
}
