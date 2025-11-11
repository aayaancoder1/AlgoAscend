import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*; 
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DigitalDiary {

    public static void main(String[] args) {
        new LoginFrame();
    }
}

// ---------------- LOGIN FRAME ----------------
class LoginFrame extends JFrame implements ActionListener {
    JTextField userField;
    JPasswordField passField;
    JButton loginBtn, clearBtn;
    JLabel msg;

    // You can change these credentials
    private final String USERNAME = "admin";
    private final String PASSWORD = "1234";

    public LoginFrame() {
        setTitle("🔐 Login - My Secret Digital Diary");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to My Secret Diary", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 2, 10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        center.add(new JLabel("Username:"));
        userField = new JTextField();
        center.add(userField);

        center.add(new JLabel("Password:"));
        passField = new JPasswordField();
        center.add(passField);

        loginBtn = new JButton("Login");
        clearBtn = new JButton("Clear");
        loginBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        center.add(loginBtn);
        center.add(clearBtn);
        add(center, BorderLayout.CENTER);

        msg = new JLabel("", JLabel.CENTER);
        msg.setForeground(Color.RED);
        add(msg, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginBtn) {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (user.equals(USERNAME) && pass.equals(PASSWORD)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                new DiaryFrame(user);
            } else {
                msg.setText("Invalid username or password!");
            }
        } else if (e.getSource() == clearBtn) {
            userField.setText("");
            passField.setText("");
        }
    }
}

// ---------------- DIARY FRAME ----------------
class DiaryFrame extends JFrame implements ActionListener {
    JTextArea diaryArea;
    JComboBox<String> moodBox, colorBox;
    JButton saveBtn, clearBtn;
    String username;

    public DiaryFrame(String user) {
        this.username = user;
        setTitle("📔 " + user + "'s Digital Diary");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🌼 Top Panel - Mood + Background Color
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Today's Mood:"));

        String[] moods = {"😊 Happy", "😢 Sad", "😤 Angry", "😴 Tired", "🤩 Excited", "😌 Calm"};
        moodBox = new JComboBox<>(moods);
        topPanel.add(moodBox);

        topPanel.add(new JLabel("Background Color:"));
        String[] colors = {"White", "Pink", "Sky Blue", "Lavender", "Mint", "Peach"};
        colorBox = new JComboBox<>(colors);
        colorBox.addActionListener(this);
        topPanel.add(colorBox);

        add(topPanel, BorderLayout.NORTH);

        // ✍ Center Text Area
        diaryArea = new JTextArea();
        diaryArea.setLineWrap(true);
        diaryArea.setWrapStyleWord(true);
        diaryArea.setFont(new Font("Serif", Font.PLAIN, 16));
        diaryArea.setBackground(Color.WHITE);
        add(new JScrollPane(diaryArea), BorderLayout.CENTER);

        // 🖱 Bottom Buttons - Save & Clear
        JPanel bottomPanel = new JPanel(new FlowLayout());
        saveBtn = new JButton("💾 Save Entry");
        clearBtn = new JButton("🧹 Clear");
        saveBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        bottomPanel.add(saveBtn);
        bottomPanel.add(clearBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveBtn) {
            saveDiary();
        } else if (e.getSource() == clearBtn) {
            diaryArea.setText("");
        } else if (e.getSource() == colorBox) {
            changeBackgroundColor((String) colorBox.getSelectedItem());
        }
    }

    private void saveDiary() {
        try {
            LocalDate date = LocalDate.now();
            String mood = (String) moodBox.getSelectedItem();
            String filename = username + "Diary" + date.format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) + ".txt";

            FileWriter fw = new FileWriter(filename);
            fw.write("User: " + username + "\n");
            fw.write("Date: " + date + "\n");
            fw.write("Mood: " + mood + "\n\n");
            fw.write(diaryArea.getText());
            fw.close();

            JOptionPane.showMessageDialog(this, "Your diary entry has been saved as: " + filename);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void changeBackgroundColor(String colorName) {
        Color chosenColor;
        switch (colorName) {
            case "Pink":
                chosenColor = new Color(255, 182, 193);
                break;
            case "Sky Blue":
                chosenColor = new Color(135, 206, 235);
                break;
            case "Lavender":
                chosenColor = new Color(230, 230, 250);
                break;
            case "Mint":
                chosenColor = new Color(189, 252, 201);
                break;
            case "Peach":
                chosenColor = new Color(255, 218, 185);
                break;
            default:
                chosenColor = Color.WHITE;
        }
        diaryArea.setBackground(chosenColor);
        getContentPane().setBackground(chosenColor);
    }
}