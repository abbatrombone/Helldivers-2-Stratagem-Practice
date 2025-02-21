package me.abbatrombone.traz;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Init extends javax.swing.JFrame{

        private javax.swing.JButton clear;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel2;
        private JTextPane intput;
        private JTextPane output;
        private javax.swing.JLabel timeLabel;
        private Timer swingtimer;
        private int start = 0;
        private JComboBox dropdown;
        private JMenuBar optionPane;

        private final ClockListener clock = new ClockListener();
        private final Timer timer = new Timer(53, clock);
        private final SimpleDateFormat date = new SimpleDateFormat("mm.ss.SSS");
        private long startTime;

        public Init() {
            File newDirectory = new File(systemFilePath());
            if (!newDirectory.exists()) {
                newDirectory.mkdir();
            }
            File textSettingsFile = new File(systemFilePath() + "/TextSettings.csv");
            File keybindsFile = new File(systemFilePath() + "/keybinds.csv");
            if(!textSettingsFile.exists()){
                try {
                    textSettingsFile.createNewFile();
                    TextSettings.makeFile(textSettingsFile.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(!keybindsFile.exists()){
                try {
                    keybindsFile.createNewFile();
                    makeFile(keybindsFile.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            initComponents();
        }
        private void initComponents() {
            jPanel2 = new JPanel();
            jLabel1 = new JLabel();
            timeLabel = new JLabel();
            clear = new JButton();
            output = new JTextPane();
            intput = new JTextPane();
            dropdown = new JComboBox<>(Stratagems.stratagemChoice());
            optionPane = new JMenuBar();
            output.setBounds(10,10,700,700);
            intput.setBounds(20,20,700,700);
            String left  = "\u21E6";
            String right = "\u21E8";
            String up    = "\u21E7";
            String down  = "\u21E9";
            StringBuilder s = new StringBuilder(intput.getText().length());

            JMenu settingsMenu = new JMenu("Settings");
            optionPane.setFont(new Font("Segoe UI", 1, 24));
            optionPane.add(settingsMenu);
            JPanel optionPanel = new JPanel();
            JMenu keyBinds = new JMenu("Key Binds");
            JMenu textMenu = new JMenu("Text Settings");
            settingsMenu.add(keyBinds);
            settingsMenu.add(textMenu);
            Color txtcolor = TextSettings.customGetColors(TextSettings.readSettings("textColor"));
            Color boxcolor = TextSettings.customGetColors(TextSettings.readSettings("textboxColor"));
            int textboxSize = Integer.parseInt(TextSettings.readSettings("textboxSize"));
            int textSize = Integer.parseInt(TextSettings.readSettings("textSize"));

            keyBinds.setPreferredSize(new Dimension(200, 30));
            keyBinds.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    timer.stop();
                    java.awt.EventQueue.invokeLater(() -> new Settings().setVisible(true));
                }});

            textMenu.setPreferredSize(new Dimension(200,30));
            textMenu.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    timer.stop();
                    java.awt.EventQueue.invokeLater(() -> new TextSettings().setVisible(true));
                }});

            dropdown.setSize(10,textboxSize);
            dropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.restart();
                    output.setText(Stratagems.stratagemArrows(Objects.requireNonNull(dropdown.getSelectedItem()).toString()));
                    intput.setText("");
                    s.setLength(0); //remove text from string builder
                }
            });
            dropdown.setForeground(txtcolor);
            dropdown.setBackground(boxcolor);
            dropdown.getEditor().getEditorComponent().setBackground(boxcolor);
            dropdown.getEditor().getEditorComponent().setForeground(txtcolor);

            output.setEditable(false);
            output.setSize(300,textboxSize);
            output.setFont(new java.awt.Font("Segoe UI", 3, textSize));
            output.setBackground(boxcolor);
            output.setForeground(txtcolor);
            output.setText(Stratagems.stratagemArrows(Objects.requireNonNull(dropdown.getSelectedItem()).toString()));
            output.setFocusable(false);

            intput.setEditable(true);
            intput.setCaretPosition(0);
            intput.setSize(300,textboxSize);
            intput.setBackground(boxcolor);
            intput.setForeground(txtcolor);
            intput.setToolTipText("Type in your stratagem Helldiver!");
            intput.requestFocus();
            intput.setCaretPosition(intput.getText().length());
            intput.setFont(new Font("Segoe UI", 3, textSize));
            intput.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if(s.isEmpty() && !timer.isRunning()){
                        timeLabel.setText(date.format(new Date(0)));
                        startTime = System.currentTimeMillis();
                        timer.start();
                        updateClock();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if(!timer.isRunning()){
                        start =0;
                        timer.start();
                    }

                    switch (settingsFileKeyCodes(e.getKeyCode())){
                        case "up"    -> intput.setText(String.valueOf(s.append(up)));
                        case "down"  -> intput.setText(String.valueOf(s.append(down)));
                        case "left"  -> intput.setText(String.valueOf(s.append(left)));
                        case "right" -> intput.setText(String.valueOf(s.append(right)));
                    }
                    settingsFileKeyCodes(e.getKeyCode());
                    int intputLength = intput.getText().length();

                if(!intput.getText().isEmpty() && !intput.getText().substring(intputLength - 1).equals(output.getText().substring(intputLength - 1, intputLength))){
                    intput.setText("");
                    s.setLength(0);
                    intputLength = 0;
                    PlaySound.playWrong();
                }
                if(intput.getText().length() == output.getText().length() && intput.getText().equals(output.getText())){
                    //stopWatch.reset();
                    intput.setText("");
                    s.setLength(0);
                    PlaySound.playRight();
                    intputLength = 0;
                    timer.stop();
                }

                }
            });
            intput.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    startTime = System.currentTimeMillis();
                    timer.start();
                    updateClock();
                }

                @Override
                public void focusLost(FocusEvent e) {}
            });

            timer.setInitialDelay(0);

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Helldivers 2 Stratagem Practice");


            jPanel2.setBackground(new java.awt.Color(51, 51, 51));
            jPanel2.setMaximumSize(new Dimension((3*textboxSize)+200,100));

            jLabel1.setBackground(new java.awt.Color(255, 255, 255));
            jLabel1.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24));
            jLabel1.setForeground(new java.awt.Color(255, 255, 255));
            jLabel1.setText("Stratagem Practice");
            jLabel1.setBounds(142,142,700,700);

            timeLabel.setBackground(boxcolor);
            timeLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, textboxSize));
            timeLabel.setForeground(txtcolor);
            timeLabel.setBounds(142,142,700,700);

            GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(optionPane)
                                    .addGap(142, 142, 142)
                                    .addComponent(jLabel1)
                                    .addContainerGap(161, Short.MAX_VALUE))
            );
            jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(optionPane)
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel1)
                                    .addContainerGap(23, Short.MAX_VALUE))
            );
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(output, GroupLayout.PREFERRED_SIZE, 450,GroupLayout.PREFERRED_SIZE)
                                            .addComponent(intput, GroupLayout.PREFERRED_SIZE, 450,GroupLayout.PREFERRED_SIZE)
                                    )
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(timeLabel)
                                            .addComponent(dropdown, GroupLayout.PREFERRED_SIZE, 650,GroupLayout.PREFERRED_SIZE)
                                    )
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            )
                            )
                            .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(output, GroupLayout.PREFERRED_SIZE, textboxSize, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(timeLabel)
                                    )
                                    .addGap(40)
                                    .addGroup(layout.createParallelGroup()
                                            .addComponent(intput, GroupLayout.PREFERRED_SIZE, textboxSize, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dropdown, GroupLayout.PREFERRED_SIZE, textboxSize, GroupLayout.PREFERRED_SIZE)
                                    )
                                    .addGap(50)


                            )
            );
            pack();
        }
    private void jTextPane1FocusGained(java.awt.event.FocusEvent evt) {
        intput.setSelectionStart(0);
        intput.setSelectionEnd(0);
    }
    public String settingsFileKeyCodes(int keycode){
        String filepath = systemFilePath() + "/keybinds.csv";
        java.util.List<keybindsPOJO> beans;
        String direction = "";
        try {
            beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(filepath))
                    .withType(keybindsPOJO.class)
                    .build()
                    .parse();
            for (keybindsPOJO kb : beans) {
              if(kb.getkeycode() == keycode){direction = kb.getKey();}
            }
        } catch (FileNotFoundException ex) {
            Settings.writeLogFile(ex.toString());
            throw new RuntimeException(ex);
        }
        return direction;
    }
    public static String systemFilePath() {
        String filePath = System.getProperty("user.home");
        String os = SystemUtils.OS_NAME;
        if (os.equals("Linux")) {
            filePath = filePath + "/.local/share/StrategemPractice";
        } //
        if(SystemUtils.IS_OS_MAC){filePath = "/StrategemPractice";}
        if(SystemUtils.IS_OS_WINDOWS){filePath += "/AppData/Roaming/StrategemPractice";}

        return filePath;
    }
    public static void makeFile(String filename){

        Path path = Paths.get(filename);

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            java.util.List<keybindsPOJO> beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(filename)).withType(keybindsPOJO.class)
                    .build()
                    .parse();

            try (BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                bw.write("key,bind,keycode");
                bw.newLine();
                bw.write("up,w,87");
                bw.newLine();
                bw.write("down,s,83");
                bw.newLine();
                bw.write("left,a,65");
                bw.newLine();
                bw.write("right,d,68");
            }
        } catch (IOException x) {
           TextSettings.writeLogFile(x.toString());
            x.printStackTrace();
        }
    }
    private void updateClock() {
        Date elapsed = new Date(System.currentTimeMillis() - startTime);
        timeLabel.setText(date.format(elapsed));
    }
    private class ClockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateClock();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Counter clock = new Counter();
                Init clock = new Init();
            }
        });
    }
}

