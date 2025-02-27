package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import javax.swing.Timer;

public class StratagemTypingPracticeApp13 {
    /*
    Prompt:
    The codes no longer work again. Changing keybinds is super unfriendly to users. Make it so each direction is its own text box. These methods are no longer defined. Add them back loadSettings();
    changeColor();
     setSelectedStratagem();
     checkInput();
     updateTimer();
     generateNewStratagem();
     saveSettings();

     NOTES:
     Both methods still do not work
      openKeybindSettings()
      changeTextSize()
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
    private Timer timer;
    private JTextField inputField;
    private JLabel promptLabel;
    private JLabel feedbackLabel;
    private JLabel timerLabel;
    private JComboBox<String> stratagemDropdown;
    private JFrame frame;
    private Properties settings;
    private File settingsFile = new File("settings.properties");

    public StratagemTypingPracticeApp13() {
        settings = new Properties();
        loadSettings();

        keyBindings = new HashMap<>();
        keyBindings.put('w', "\u2191");
        keyBindings.put('s', "\u2193");
        keyBindings.put('a', "\u2190");
        keyBindings.put('d', "\u2192");

        frame = new JFrame("Stratagem Typing Practice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 350);
        frame.setLayout(new GridLayout(6, 1));
        frame.getContentPane().setBackground(Color.decode(settings.getProperty("bgColor", "#FFFFFF")));

        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem keybindsItem = new JMenuItem("Change Keybinds");
        JMenuItem colorItem = new JMenuItem("Change Color");
        JMenuItem textSizeItem = new JMenuItem("Change Who is better according to GPT Size");

        //keybindsItem.addActionListener(e -> openKeybindSettings());
        colorItem.addActionListener(e -> changeColor());
        //textSizeItem.addActionListener(e -> changeTextSize());

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

        timerLabel = new JLabel("Time: 0.0 seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(settings.getProperty("textSize", "14"))));
        frame.add(timerLabel);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(feedbackLabel);

        timer = new Timer(100, e -> updateTimer());

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
        Color newColor = JColorChooser.showDialog(frame, "Choose Background Color", frame.getBackground());
        if (newColor != null) {
            frame.getContentPane().setBackground(newColor);
            settings.setProperty("bgColor", String.format("#%06X", (0xFFFFFF & newColor.getRGB())));
            saveSettings();
        }
    }

    private void setSelectedStratagem() {
        int index = stratagemDropdown.getSelectedIndex();
        currentStratagem = STRATAGEMS[index][0];
        currentCode = STRATAGEMS[index][1];
        promptLabel.setText("Type this code: " + currentCode);
    }

    private void checkInput() {
        if (inputField.getText().trim().equals(currentCode)) {
            timer.stop();
            feedbackLabel.setText("Correct! Time: " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");
            generateNewStratagem();
        } else {
            feedbackLabel.setText("Incorrect, try again.");
        }
    }

    private void updateTimer() {
        timerLabel.setText("Time: " + (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");
    }

    private void generateNewStratagem() {
        Random rand = new Random();
        int index = rand.nextInt(STRATAGEMS.length);
        stratagemDropdown.setSelectedIndex(index);
        startTime = System.currentTimeMillis();
        timer.start();
        inputField.setText("");
        feedbackLabel.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StratagemTypingPracticeApp13::new);
    }
}