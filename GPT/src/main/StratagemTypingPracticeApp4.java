package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class StratagemTypingPracticeApp4 {
    // Comprehensive list of stratagems and their corresponding codes
    private static final String[][] STRATAGEMS = {
            {"MG-43 Machine Gun", "\u2193 \u2190 \u2193 \u2191 \u2192"},
            {"APW-1 Anti-Materiel Rifle", "\u2193 \u2190 \u2192 \u2191 \u2193"},
            {"M-105 Stalwart", "\u2193 \u2190 \u2193 \u2191 \u2191 \u2190"},
            {"EAT-17 Expendable Anti-Tank", "\u2193 \u2193 \u2190 \u2191 \u2192"},
            {"GR-8 Recoilless Rifle", "\u2193 \u2190 \u2192 \u2192 \u2190"},
            {"FLAM-40 Flamethrower", "\u2193 \u2190 \u2191 \u2193 \u2191"},
            {"AC-8 Autocannon", "\u2193 \u2190 \u2193 \u2191 \u2191 \u2192"},
            {"Resupply", "\u2193 \u2193 \u2191 \u2192"},
            {"Reinforce", "\u2191 \u2193 \u2192 \u2190 \u2191"},
            {"Orbital 380mm HE Barrage", "\u2192 \u2193 \u2191 \u2191 \u2190 \u2193 \u2193"},
            {"Strafing Run", "\u2192 \u2192 \u2191"},
            {"Orbital Napalm Barrage", "\u2192 \u2193 \u2191 \u2190 \u2191 \u2193 \u2192"},
            {"NUX-223 Hellbomb", "\u2193 \u2191 \u2193 \u2190 \u2192 \u2191"}
    };

    private String currentStratagem;
    private String currentCode;
    private long startTime;
    private JTextField inputField;
    private JLabel promptLabel;
    private JLabel feedbackLabel;
    private JButton submitButton;

    public StratagemTypingPracticeApp4() {
        JFrame frame = new JFrame("Stratagem Typing Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
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

    //This is an error from GPT
//    public static void main(String[] args
//::contentReference[oaicite:0]{index=0}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StratagemTypingPracticeApp4::new);
    }
}

