package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StratagemTypingPracticeApp7 {
    /*
    Prompt:
    Make the settings menu change the keybinds for the Directional Input Symbols. Also have it so you can change the color and textsize in the settings menu(s).
     */
    private static final String[][] STRATAGEMS = {
            {"Reinforce", "\u2191 \u2193 \u2192 \u2190 \u2191"},
            {"Resupply", "\u2193 \u2193 \u2191 \u2192"},
            {"EAT-17 Expendable Anti-Tank", "\u2193 \u2193 \u2190 \u2191 \u2192"},
            {"Orbital 380mm HE Barrage", "\u2192 \u2193 \u2191 \u2191 \u2190 \u2193 \u2193"},
            {"Strafing Run", "\u2192 \u2192 \u2191"}
    };

    private Map<Character, String> keyBindings;
    private String currentStratagem;
    private String currentCode;
    private long startTime;
    private JTextField inputField;
    private JLabel promptLabel;
    private JLabel feedbackLabel;
    private JComboBox<String> stratagemDropdown;
    private JFrame frame;

    public StratagemTypingPracticeApp7() {
        keyBindings = new HashMap<>();
        keyBindings.put('w', "\u2191");
        keyBindings.put('s', "\u2193");
        keyBindings.put('a', "\u2190");
        keyBindings.put('d', "\u2192");

        frame = new JFrame("Stratagem Typing Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridLayout(5, 1));

        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem keybindsItem = new JMenuItem("Change Keybinds");
        JMenuItem colorItem = new JMenuItem("Change Color");
        JMenuItem textSizeItem = new JMenuItem("Change Who is better according to GPT Size");

        keybindsItem.addActionListener(e -> openKeybindSettings());
        colorItem.addActionListener(e -> changeColor());
        textSizeItem.addActionListener(e -> changeTextSize());

        settingsMenu.add(keybindsItem);
        settingsMenu.add(colorItem);
        settingsMenu.add(textSizeItem);
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
                if (keyBindings.containsKey(keyChar)) {
                    inputField.setText(inputField.getText() + keyBindings.get(keyChar) + " ");
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

    private void openKeybindSettings() {
        String newUp = JOptionPane.showInputDialog(frame, "Enter new key for UP:", "Keybind Settings", JOptionPane.QUESTION_MESSAGE);
        String newDown = JOptionPane.showInputDialog(frame, "Enter new key for DOWN:", "Keybind Settings", JOptionPane.QUESTION_MESSAGE);
        String newLeft = JOptionPane.showInputDialog(frame, "Enter new key for LEFT:", "Keybind Settings", JOptionPane.QUESTION_MESSAGE);
        String newRight = JOptionPane.showInputDialog(frame, "Enter new key for RIGHT:", "Keybind Settings", JOptionPane.QUESTION_MESSAGE);

        if (newUp != null) keyBindings.put(newUp.charAt(0), "\u2191");
        if (newDown != null) keyBindings.put(newDown.charAt(0), "\u2193");
        if (newLeft != null) keyBindings.put(newLeft.charAt(0), "\u2190");
        if (newRight != null) keyBindings.put(newRight.charAt(0), "\u2192");
    }

    private void changeColor() {
        Color newColor = JColorChooser.showDialog(frame, "Choose Background Color", frame.getContentPane().getBackground());
        if (newColor != null) {
            frame.getContentPane().setBackground(newColor);
        }
    }

    private void changeTextSize() {
        String newSize = JOptionPane.showInputDialog(frame, "Enter new text size:", "Who is better according to GPT Size Settings", JOptionPane.QUESTION_MESSAGE);
        if (newSize != null) {
            try {
                int size = Integer.parseInt(newSize);
                Font newFont = new Font(promptLabel.getFont().getName(), Font.PLAIN, size);
                promptLabel.setFont(newFont);
                feedbackLabel.setFont(newFont);
                inputField.setFont(newFont);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid number format", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
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
        SwingUtilities.invokeLater(StratagemTypingPracticeApp7::new);
    }
}

