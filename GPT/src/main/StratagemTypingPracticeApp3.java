package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class StratagemTypingPracticeApp3 {
    /*
    I had to make a new account so i did the same first prompt and it gave me a swing app so i moved to the following Prompt:
    For the Stratagems Array use https://helldivers.wiki.gg/wiki/Stratagems the Stratagems codes from the wiki. It will be the 4th column and will be characters like "\u21E7"
     */
    // Stratagems and their corresponding codes
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

    public StratagemTypingPracticeApp3() {
        JFrame frame = new JFrame("Stratagem Typing Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLayout(new GridLayout(4, 1));

        promptLabel = new JLabel("Type this code: ", SwingConstants.CENTER);
        frame.add(promptLabel);

        inputField = new JTextField();
        frame.add(inputField);

        submitButton = new JButton("Submit");
        frame.add(submitButton);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(feedbackLabel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInput();
            }
        });

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
        SwingUtilities.invokeLater(StratagemTypingPracticeApp3::new);
    }
}
