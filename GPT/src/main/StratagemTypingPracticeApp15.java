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

public class StratagemTypingPracticeApp15 {
    /*
    Prompt:
    Change keybinds works but put the labels to the left and the textfeilds to the right with the save button at the bottom. These methods got removed again: loadSettings();
      changeColor()
      setSelectedStratagem()
      checkInput()
      updateTimer()
      updateTimer()

      NOTES:
      It did do the keybinds changes correctly
      addKeyListener was removed in 15 and 14

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

    public StratagemTypingPracticeApp15() {
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
        //colorItem.addActionListener(e -> changeColor());
        //textSizeItem.addActionListener(e -> changeTextSize());
        //textColorItem.addActionListener(e -> changeTextColor());

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
        inputField.addActionListener(e -> checkInput());
        frame.add(inputField);

        timerLabel = new JLabel("Time: 0.0 seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(settings.getProperty("textSize", "14"))));
        frame.add(timerLabel);

        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        frame.add(feedbackLabel);

        timer = new Timer(100, e -> updateTimer());

        //generateNewStratagem();

        frame.setVisible(true);
    }

    private void loadSettings() {
        try (FileInputStream fis = new FileInputStream(settingsFile)) {
            settings.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveSettings() {
        try (FileOutputStream fos = new FileOutputStream(settingsFile)) {
            settings.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
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

    private void setSelectedStratagem() {
        int index = stratagemDropdown.getSelectedIndex();
        currentStratagem = STRATAGEMS[index][0];
        currentCode = STRATAGEMS[index][1];
        promptLabel.setText("Type this code: " + currentCode);
    }

    private void checkInput() {
        if (inputField.getText().equals(currentCode)) {
            feedbackLabel.setText("Correct!");
            //generateNewStratagem();
        } else {
            feedbackLabel.setText("Try again!");
        }
    }

    private void updateTimer() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        timerLabel.setText("Time: " + elapsed + " seconds");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StratagemTypingPracticeApp15::new);
    }
}

