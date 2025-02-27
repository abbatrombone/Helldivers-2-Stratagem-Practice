package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class StratagemTypingAppSwing2 extends JFrame {

    // Array of Helldivers stratagems for practice (sample from Helldivers wiki)
    private static final String[] STRATAGEMS = {
            "Supply Drop",
            "Tank Destroyer",
            "Hover Tank",
            "Airstrike",
            "Turret",
            "Flamethrower",
            "Mines",
            "Artillery Strike",
            "Electromagnetic Pulse",
            "Drone Strike",
            "Proximity Mine",
            "Helldiver Drop",
            "Reinforcements",
            "Nuclear Strike",
            "Mortar Strike"
    };

    private JTextArea typingArea;
    private JLabel stratagemLabel;
    private JLabel timeLabel;
    private JLabel accuracyLabel;
    private JButton startButton;
    private long startTime;
    private String currentStratagem;

    public StratagemTypingAppSwing2() {
        // Frame settings
        setTitle("Stratagem Typing Practice");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        stratagemLabel = new JLabel("Click Start to begin typing practice.", JLabel.CENTER);
        stratagemLabel.setFont(new Font("Arial", Font.BOLD, 18));

        timeLabel = new JLabel("Time: 0.0 seconds", JLabel.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        accuracyLabel = new JLabel("Accuracy: 0%", JLabel.CENTER);
        accuracyLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        typingArea = new JTextArea();
        typingArea.setFont(new Font("Courier New", Font.PLAIN, 16));
        typingArea.setLineWrap(true);
        typingArea.setWrapStyleWord(true);
        typingArea.setEditable(false);
        typingArea.setBackground(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(typingArea);
        scrollPane.setPreferredSize(new Dimension(550, 100));

        startButton = new JButton("Start Practice");
        startButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add action listener to start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPractice();
            }
        });

        // Set up layout
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(stratagemLabel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1));
        bottomPanel.add(timeLabel);
        bottomPanel.add(accuracyLabel);
        bottomPanel.add(startButton);

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void startPractice() {
        // Generate a random stratagem
        Random random = new Random();
        currentStratagem = STRATAGEMS[random.nextInt(STRATAGEMS.length)];

        // Set the stratagem in the label
        stratagemLabel.setText("\"" + currentStratagem + "\"");

        // Reset the typing area
        typingArea.setEditable(true);
        typingArea.setText("");
        typingArea.requestFocusInWindow();

        // Start the timer
        startTime = System.currentTimeMillis();

        // Change the button text to "Submit"
        startButton.setText("Submit");
        startButton.removeActionListener(startButton.getActionListeners()[0]);  // Remove start practice listener
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkTyping();
            }
        });
    }

    private void checkTyping() {
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;
        double timeInSeconds = timeTaken / 1000.0;

        // Get the text the user typed
        String userText = typingArea.getText();

        // Calculate accuracy
        int correctCharacters = countCorrectCharacters(currentStratagem, userText);
        double accuracy = (double) correctCharacters / currentStratagem.length() * 100;

        // Update the labels with results
        timeLabel.setText(String.format("Time: %.2f seconds", timeInSeconds));
        accuracyLabel.setText(String.format("Accuracy: %.2f%%", accuracy));

        // Reset typing area and change button back to start
        typingArea.setEditable(false);
        startButton.setText("Start Practice");
        startButton.removeActionListener(startButton.getActionListeners()[0]);  // Remove submit listener
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPractice();
            }
        });
    }

    private int countCorrectCharacters(String stratagem, String userInput) {
        int correctCount = 0;
        for (int i = 0; i < Math.min(stratagem.length(), userInput.length()); i++) {
            if (stratagem.charAt(i) == userInput.charAt(i)) {
                correctCount++;
            }
        }
        return correctCount;
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                StratagemTypingAppSwing2 app = new StratagemTypingAppSwing2();
                app.setVisible(true);
            }
        });
    }
}
