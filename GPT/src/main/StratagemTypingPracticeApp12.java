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

public class StratagemTypingPracticeApp12 {
    /*
    Prompt:
    That was well implamented. I did notice that Keybinds dialog stopped working and the Jlabel timer text size does not update with other text sizes

    NOTES:
    loadSettings();
    changeColor();
     setSelectedStratagem();
     checkInput();
     updateTimer();
     generateNewStratagem();
     saveSettings();
     Changing keybinds is super unfriendly to users
     Codes no longer work again.

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

    public StratagemTypingPracticeApp12() {
        settings = new Properties();
        //loadSettings();

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

        keybindsItem.addActionListener(e -> openKeybindSettings());
        //colorItem.addActionListener(e -> changeColor());
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
       // stratagemDropdown.addActionListener(e -> setSelectedStratagem());
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
        //inputField.addActionListener(e -> checkInput());
        frame.add(inputField);

        timerLabel = new JLabel("Time: 0.0 seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(settings.getProperty("textSize", "14"))));
        frame.add(timerLabel);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(feedbackLabel);

        //timer = new Timer(100, e -> updateTimer());

        //generateNewStratagem();

        frame.setVisible(true);
    }

    private void openKeybindSettings() {
        String newBindings = JOptionPane.showInputDialog(frame, "Enter keybinds as w=↑,s=↓,a=←,d=→", "w=↑,s=↓,a=←,d=→");
        if (newBindings != null) {
            String[] mappings = newBindings.split(",");
            for (String mapping : mappings) {
                String[] parts = mapping.split("=");
                if (parts.length == 2 && parts[0].length() == 1) {
                    keyBindings.put(parts[0].charAt(0), parts[1]);
                }
            }
        }
    }

    private void changeTextSize() {
        String sizeStr = JOptionPane.showInputDialog(frame, "Enter text size:", settings.getProperty("textSize", "14"));
        if (sizeStr != null && sizeStr.matches("\\d+")) {
            settings.setProperty("textSize", sizeStr);
            int newSize = Integer.parseInt(sizeStr);
            promptLabel.setFont(new Font("Arial", Font.PLAIN, newSize));
            timerLabel.setFont(new Font("Arial", Font.PLAIN, newSize));
            //saveSettings();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StratagemTypingPracticeApp12::new);
    }
}

