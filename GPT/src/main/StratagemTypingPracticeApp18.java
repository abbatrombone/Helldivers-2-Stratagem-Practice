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

public class StratagemTypingPracticeApp18 {
    /*
    Prompt:
    Cannot invoke "javax.swing.Timer.restart()" because "this.timer" is null

       Cannot resolve methods:
       changeColor
       setSelectedStratagem
       checkInput
       updateTimer

       Notes:
       I will manually add the missing methods, as it's stuck in a loop.
       I will use the methods from differnt versions noted below:
       Load -> Verion 8
       openKeybindSettings -> 15
       changeTextSize -> 14
       changeTextColor -> 14

       I am going to ask it to add one last feature: to play a sound when correct and when in correct

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

    public StratagemTypingPracticeApp18() {
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
        JMenuItem colorItem = new JMenuItem("Change Background Color");
        JMenuItem textSizeItem = new JMenuItem("Change Who is better according to GPT Size");
        JMenuItem textColorItem = new JMenuItem("Change Who is better according to GPT Color");

        keybindsItem.addActionListener(e -> openKeybindSettings());
        colorItem.addActionListener(e -> changeColor());
        textSizeItem.addActionListener(e -> changeTextSize());
        textColorItem.addActionListener(e -> changeTextColor());

        settingsMenu.add(keybindsItem);
        settingsMenu.add(colorItem);
        settingsMenu.add(textSizeItem);
        settingsMenu.add(textColorItem);
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

    private void changeColor() {
        Color newColor = JColorChooser.showDialog(frame, "Choose Background Color", frame.getContentPane().getBackground());
        if (newColor != null) {
            frame.getContentPane().setBackground(newColor);
            settings.setProperty("bgColor", "#" + Integer.toHexString(newColor.getRGB()).substring(2));
            saveSettings();
        }
    }
    private void openKeybindSettings() {
        JDialog keybindDialog = new JDialog(frame, "Change Keybinds", true);
        keybindDialog.setLayout(new GridLayout(5, 2));
        keybindDialog.setSize(300, 200);

        JLabel upLabel = new JLabel("Up:");
        JTextField upField = new JTextField(String.valueOf('w'));
        JLabel downLabel = new JLabel("Down:");
        JTextField downField = new JTextField(String.valueOf('s'));
        JLabel leftLabel = new JLabel("Left:");
        JTextField leftField = new JTextField(String.valueOf('a'));
        JLabel rightLabel = new JLabel("Right:");
        JTextField rightField = new JTextField(String.valueOf('d'));

        keybindDialog.add(upLabel);
        keybindDialog.add(upField);
        keybindDialog.add(downLabel);
        keybindDialog.add(downField);
        keybindDialog.add(leftLabel);
        keybindDialog.add(leftField);
        keybindDialog.add(rightLabel);
        keybindDialog.add(rightField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            keyBindings.put(upField.getText().charAt(0), "\u2191");
            keyBindings.put(downField.getText().charAt(0), "\u2193");
            keyBindings.put(leftField.getText().charAt(0), "\u2190");
            keyBindings.put(rightField.getText().charAt(0), "\u2192");
            saveSettings();
            keybindDialog.dispose();
        });
        keybindDialog.add(saveButton);

        keybindDialog.setVisible(true);
    }
    private void changeTextSize() {
        String size = JOptionPane.showInputDialog(frame, "Enter text size:", settings.getProperty("textSize", "14"));
        if (size != null) {
            settings.setProperty("textSize", size);
            promptLabel.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(size)));
            timerLabel.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(size)));
            saveSettings();
        }
    }
    private void changeTextColor() {
        Color newColor = JColorChooser.showDialog(frame, "Choose Who is better according to GPT Color", promptLabel.getForeground());
        if (newColor != null) {
            promptLabel.setForeground(newColor);
            timerLabel.setForeground(newColor);
            feedbackLabel.setForeground(newColor);
            settings.setProperty("textColor", String.format("#%06X", (0xFFFFFF & newColor.getRGB())));
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
            feedbackLabel.setText("Correct!");
            generateNewStratagem();
        } else {
            feedbackLabel.setText("Incorrect, try again.");
        }
    }

    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        timerLabel.setText("Time: " + (elapsedTime / 1000.0) + " seconds");
    }

    private void generateNewStratagem() {
        Random rand = new Random();
        int index = rand.nextInt(STRATAGEMS.length);
        currentStratagem = STRATAGEMS[index][0];
        currentCode = STRATAGEMS[index][1];
        promptLabel.setText("Type this code: " + currentCode);
        inputField.setText("");
        startTime = System.currentTimeMillis();
        timer.restart();
    }

    private void saveSettings() {
        try (FileOutputStream out = new FileOutputStream(settingsFile)) {
            settings.store(out, "User Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StratagemTypingPracticeApp18::new);
    }
}