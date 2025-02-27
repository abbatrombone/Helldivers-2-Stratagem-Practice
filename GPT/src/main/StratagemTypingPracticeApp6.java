package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class StratagemTypingPracticeApp6 {
    /*
    Prompt: remove the submit button and replace it with when the enter key is hit. Add a settings menu, and a dropdown menu for the STRATAGEMS
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
    private JComboBox<String> stratagemDropdown;

    public StratagemTypingPracticeApp6() {
        JFrame frame = new JFrame("Stratagem Typing Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLayout(new GridLayout(5, 1));

        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        menuBar.add(settingsMenu);
        frame.setJMenuBar(menuBar);

        stratagemDropdown = new JComboBox<>();
        for (String[] stratagem : STRATAGEMS) {
            stratagemDropdown.addItem(stratagem[0]);
        }
        stratagemDropdown.addActionListener(e -> setSelectedStratagem());
        frame.add(stratagemDropdown);

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
        inputField.addActionListener(e -> checkInput());
        frame.add(inputField);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(feedbackLabel);

        generateNewStratagem();

        frame.setVisible(true);
    }

    private void setSelectedStratagem() {
        int index = stratagemDropdown.getSelectedIndex();
        if (index >= 0) {
            currentStratagem = STRATAGEMS[index][0];
            currentCode = STRATAGEMS[index][1];
            promptLabel.setText("Type this code for " + currentStratagem + ": " + currentCode);
            inputField.setText("");
            feedbackLabel.setText("");
            startTime = System.currentTimeMillis();
        }
    }

    private void generateNewStratagem() {
        Random random = new Random();
        int index = random.nextInt(STRATAGEMS.length);
        stratagemDropdown.setSelectedIndex(index);
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
        SwingUtilities.invokeLater(StratagemTypingPracticeApp6::new);
    }
}
