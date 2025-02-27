package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class StratagemTypingPracticeApp5 {
    /*
    Prompt:
    while typing use a keyboard listener to change keyboard press to be one of the Directional Input Symbols. Have w be up and s be down, a be left and d be right
     */

    private static final String[][] STRATAGEMS = {
            {"Reinforce", "\u2191 \u2193 \u2192 \u2190 \u2191"},
            {"Resupply", "\u2193 \u2193 \u2191 \u2192"},
            {"EAT-17 Expendable Anti-Tank", "\u2193 \u2193 \u2190 \u2191 \u2192"},
            {"Orbital 380mm HE Barrage", "\u2192 \u2193 \u2191 \u2191 \u2190 \u2193 \u2193"},
            {"Strafing Run", "\u2192 \u2192 \u2191"}
    };

    private String currentStratagem;
    private String currentCode;
    private long startTime;
    private JTextField inputField;
    private JLabel promptLabel;
    private JLabel feedbackLabel;
    private JButton submitButton;

    public StratagemTypingPracticeApp5() {
        JFrame frame = new JFrame("Stratagem Typing Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLayout(new GridLayout(4, 1));

        promptLabel = new JLabel("Type this code: ", SwingConstants.CENTER);
        frame.add(promptLabel);

        inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                String replacement = "";
                switch (keyChar) {
                    case 'w': replacement = "\u2191"; break;
                    case 's': replacement = "\u2193"; break;
                    case 'a': replacement = "\u2190"; break;
                    case 'd': replacement = "\u2192"; break;
                }
                if (!replacement.isEmpty()) {
                    inputField.setText(inputField.getText() + replacement + " ");
                    e.consume();
                }
            }
        });
        frame.add(inputField);

        submitButton = new JButton("Submit");
        frame.add(submitButton);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(feedbackLabel);

        submitButton.addActionListener(e -> checkInput());

        generateNewStratagem();

        frame.setVisible(true);
    }

    private void generateNewStratagem() {
        Random random = new Random();
        int index = random.nextInt(STRATAGEMS.length);
        currentStratagem = STRATAGEMS[index][0];
        currentCode = STRATAGEMS[index][1];
        promptLabel.setText("Type this code for " + currentStratagem + ": " + currentCode);
        inputField.setText("");
        feedbackLabel.setText("");
        startTime = System.currentTimeMillis();
    }

    private void checkInput() {
        String userInput = inputField.getText().trim();
        if (userInput.equals(currentCode)) {
            long timeTaken = System.currentTimeMillis() - startTime;
            feedbackLabel.setText("Correct! Time: " + (timeTaken / 1000.0) + " seconds");
            generateNewStratagem();
        } else {
            feedbackLabel.setText("Incorrect! Try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StratagemTypingPracticeApp5::new);
    }
}
