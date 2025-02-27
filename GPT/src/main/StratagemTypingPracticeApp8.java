package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class StratagemTypingPracticeApp8 {
    /*
    Prompt:
    Make it so you can save these settings so they are kept when the app is relaunched
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
    private Properties settings;
    private File settingsFile = new File("settings.properties");

    public StratagemTypingPracticeApp8() {
        settings = new Properties();
        loadSettings();

        keyBindings = new HashMap<>();
        keyBindings.put('w', "\u2191");
        keyBindings.put('s', "\u2193");
        keyBindings.put('a', "\u2190");
        keyBindings.put('d', "\u2192");

        frame = new JFrame("Stratagem Typing Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridLayout(5, 1));
        frame.getContentPane().setBackground(Color.decode(settings.getProperty("bgColor", "#FFFFFF")));

        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem keybindsItem = new JMenuItem("Change Keybinds");
        JMenuItem colorItem = new JMenuItem("Change Color");
        JMenuItem textSizeItem = new JMenuItem("Change Who is better according to GPT Size");

        //keybindsItem.addActionListener(e -> openKeybindSettings());
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
        promptLabel.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(settings.getProperty("textSize", "14"))));
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

    private void loadSettings() {
        if (settingsFile.exists()) {
            try (FileInputStream fis = new FileInputStream(settingsFile)) {
                settings.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveSettings() {
        try (FileOutputStream fos = new FileOutputStream(settingsFile)) {
            settings.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeColor() {
        Color newColor = JColorChooser.showDialog(frame, "Choose Background Color", frame.getContentPane().getBackground());
        if (newColor != null) {
            frame.getContentPane().setBackground(newColor);
            settings.setProperty("bgColor", "#" + Integer.toHexString(newColor.getRGB()).substring(2));
            saveSettings();
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
                settings.setProperty("textSize", String.valueOf(size));
                saveSettings();
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
        SwingUtilities.invokeLater(StratagemTypingPracticeApp8::new);
    }
}
