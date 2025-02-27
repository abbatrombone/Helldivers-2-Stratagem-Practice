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

public class StratagemTypingPracticeApp14 {
    /*
    Prompt:
    You fixed most of the issues, which is great thank you ChatGPT. However,  Both methods still do not work
      openKeybindSettings()
      changeTextSize(). Also add a setting menu to change the text color like you did with the background. It was well implemented.

      NOTES:
      loadSettings();
      changeColor()
      setSelectedStratagem()
      checkInput()
      updateTimer()
      updateTimer()
      Change keybinds works but put the labels to the left and the textfeilds to the right with the save button at the bottom

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

    public StratagemTypingPracticeApp14() {
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
        JMenuItem colorItem = new JMenuItem("Change Background Color");
        JMenuItem textSizeItem = new JMenuItem("Change Who is better according to GPT Size");
        JMenuItem textColorItem = new JMenuItem("Change Who is better according to GPT Color");

        keybindsItem.addActionListener(e -> openKeybindSettings());
        //colorItem.addActionListener(e -> changeColor());
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
        //stratagemDropdown.addActionListener(e -> setSelectedStratagem());
        frame.add(stratagemDropdown);

        promptLabel = new JLabel("Type this code: ", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Arial", Font.PLAIN, Integer.parseInt(settings.getProperty("textSize", "14"))));
        frame.add(promptLabel);

        inputField = new JTextField();
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
        JDialog keybindDialog = new JDialog(frame, "Change Keybinds", true);
        keybindDialog.setLayout(new GridLayout(4, 2));
        keybindDialog.setSize(300, 200);

        JTextField upField = new JTextField(String.valueOf('w'));
        JTextField downField = new JTextField(String.valueOf('s'));
        JTextField leftField = new JTextField(String.valueOf('a'));
        JTextField rightField = new JTextField(String.valueOf('d'));

        keybindDialog.add(new JLabel("Up:"));
        keybindDialog.add(upField);
        keybindDialog.add(new JLabel("Down:"));
        keybindDialog.add(downField);
        keybindDialog.add(new JLabel("Left:"));
        keybindDialog.add(leftField);
        keybindDialog.add(new JLabel("Right:"));
        keybindDialog.add(rightField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            keyBindings.put(upField.getText().charAt(0), "\u2191");
            keyBindings.put(downField.getText().charAt(0), "\u2193");
            keyBindings.put(leftField.getText().charAt(0), "\u2190");
            keyBindings.put(rightField.getText().charAt(0), "\u2192");
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
            //saveSettings();
        }
    }

    private void changeTextColor() {
        Color newColor = JColorChooser.showDialog(frame, "Choose Who is better according to GPT Color", promptLabel.getForeground());
        if (newColor != null) {
            promptLabel.setForeground(newColor);
            timerLabel.setForeground(newColor);
            feedbackLabel.setForeground(newColor);
            settings.setProperty("textColor", String.format("#%06X", (0xFFFFFF & newColor.getRGB())));
            //saveSettings();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StratagemTypingPracticeApp14::new);
    }
}
