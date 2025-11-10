import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

class DelayThread extends Thread
{
    int delay;
    FastFingerSimple game;

    DelayThread(int delay, FastFingerSimple game)
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

public class FastFingerSimple extends JFrame implements KeyListener
{
    JPanel colorPanel = new JPanel();
    JLabel info = new JLabel("Enter number of players and press Start");
    JTextField playerInput = new JTextField(5);
    JButton startBtn = new JButton("Start");

    boolean green = false;
    int numPlayers;
    char keys[];

    public FastFingerSimple()
    {
        setTitle("Fast Finger Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);	// i think can be removed

        setLayout(new BorderLayout());
        add(info, BorderLayout.NORTH);
        add(colorPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.add(new JLabel("Players:"));
        bottom.add(playerInput);
        bottom.add(startBtn);
        add(bottom, BorderLayout.SOUTH);

        startBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                setupGame();
            }
        });

        addKeyListener(this);
        setFocusable(true);
        setVisible(true);
    }

void setupGame()
{
    try {
        numPlayers = Integer.parseInt(playerInput.getText().trim());

        if(numPlayers < 2) {
            info.setText("Need at least 2 players.");
            return;
        }

        keys = new char[numPlayers];

        for(int i = 0; i < numPlayers; i++)
            keys[i] = (char)('A' + i);

        info.setText("Keys: " + new String(keys) + " | Wait for Green...");
        startRound();

        requestFocusInWindow();

    } catch (Exception e) {
        info.setText("Invalid number.");
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
        info.setText("GO! Press your key!");
    }

    public void keyPressed(KeyEvent e)
    {
        if(!green) return;

        char key = Character.toUpperCase(e.getKeyChar());

        for(char k : keys)
        {
            if(k == key)
            {
                info.setText("Winner: Player " + key);
                green = false;
                return;
            }
        }
    }

    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}

    public static void main(String[] args)
    {
        new FastFingerSimple();
    }
}
