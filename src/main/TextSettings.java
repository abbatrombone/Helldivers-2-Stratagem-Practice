package me.abbatrombone.traz;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.plaf.metal.MetalComboBoxButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

public class TextSettings extends JFrame{

    private JPanel textSettingPanel;

    private JComboBox textColor;
    private JTextPane textSize;
    private JTextPane textboxSize;
    private JComboBox textboxColor;

    private JLabel title;
    private JLabel textColor_label;
    private JLabel textSize_label;
    private JLabel textboxSize_label;
    private JLabel textboxColor_label;


    public TextSettings() {
        initComponents();
        File newDirectory = new File(systemFilePath());
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }

        //InputStream is = getClass().getClassLoader().getResourceAsStream("TextSettings.csv");
        File textSettingsFile = new File(systemFilePath() + "/TextSettings.csv");
        File logfile = new File(systemFilePath() + "/Logfile.log");
        if(!textSettingsFile.exists()){
            try {
                textSettingsFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(!logfile.exists()){
            try {
                logfile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void initComponents() {
        String[] colors = {"BLACK","BLUE","CYAN","DARK_GRAY","GRAY","GREEN","LIGHT_GRAY","MAGENTA","ORANGE","PINK","RED","WHITE","YELLOW"};
        final int maxNumberOfCharacters = 3;
        Color tcolor = customGetColors(readSettings("textColor"));
        Color bcolor = customGetColors(readSettings("textboxColor"));
        textSettingPanel = new JPanel();
        title = new JLabel();
        textColor = new JComboBox(colors);
        textSize = new JTextPane(new DefaultStyledDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if ((getLength() + str.length()) <= maxNumberOfCharacters) {
                    super.insertString(offs, str, a);
                }else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        textboxSize = new JTextPane(new DefaultStyledDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if ((getLength() + str.length()) <= maxNumberOfCharacters) {
                    super.insertString(offs, str, a);
                }else {
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        textboxColor = new JComboBox(colors);
        textColor_label = new JLabel();
        textSize_label = new JLabel();
        textboxSize_label = new JLabel();
        textboxColor_label = new JLabel();

        textSettingPanel.setBackground(new Color(51, 51, 51));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Text Settings");

        title.setBackground(new Color(255, 255, 255));
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(255, 255, 255));
        title.setText("Settings");
        title.setBounds(142,142,700,700);

        GroupLayout settingPanelLayout = new GroupLayout(textSettingPanel);
        textSettingPanel.setLayout(settingPanelLayout);
        settingPanelLayout.setHorizontalGroup(
                settingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(settingPanelLayout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(title)
                                .addContainerGap(161, Short.MAX_VALUE))
        );
        settingPanelLayout.setVerticalGroup(
                settingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(settingPanelLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(title)
                                .addContainerGap(23, Short.MAX_VALUE))
        );


        textColor_label.setBackground(new Color(255, 255, 255));
        textColor_label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        textColor_label.setForeground(new Color(0, 0, 0));
        textColor_label.setText("Text Color");
        textColor_label.setBounds(142,142,700,700);

        textSize_label.setBackground(new Color(255, 255, 255));
        textSize_label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        textSize_label.setForeground(new Color(0, 0, 0));
        textSize_label.setText("Text Size");
        textSize_label.setBounds(142,142,700,700);

        textboxSize_label.setBackground(new Color(255, 255, 255));
        textboxSize_label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        textboxSize_label.setForeground(new Color(0, 0, 0));
        textboxSize_label.setText("Textbox Size");
        textboxSize_label.setSize(textboxSize_label.getPreferredSize());
        //textboxSize_label.setBounds(142,142,700,700);

        textboxColor_label.setBackground(new Color(255, 255, 255));
        textboxColor_label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        textboxColor_label.setForeground(new Color(0, 0, 0));
        textboxColor_label.setText("Textbox Color");
        textboxColor_label.setSize(textboxColor_label.getPreferredSize());
        //textboxColor_label.setBounds(142,142,700,700);

        textColor.setEditable(true);
        textColor.setSize(300,30);
        textColor.setFont(new Font("Segoe UI", 3, Integer.parseInt(readSettings("textSize"))));
        textColor.setForeground(tcolor);
        textColor.setBackground(bcolor);
        textColor.setFocusable(true);
        textColor.setSelectedIndex(Arrays.asList(colors).indexOf(readSettings("textColor")));
        textColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTextColor = Objects.requireNonNull(textColor.getSelectedItem()).toString();
                String filepath = systemFilePath() + "/TextSettings.csv";
                java.util.List<TextSettingsPOJO> beans;
                try {
                    beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(filepath))
                            .withType(TextSettingsPOJO.class)
                            .build()
                            .parse();
                    for (TextSettingsPOJO TS : beans) {
                        if(!TS.getTextColor().equals(selectedTextColor)){
                            writeTextColor(TS,textColor,selectedTextColor);
                        }
                        if(TS.getTextColor().isEmpty()){
                            writeDefaults(TS);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Settings.writeLogFile(ex.toString());
                    throw new RuntimeException(ex);
                }
            }
        });
        textColor.getEditor().getEditorComponent().setBackground(bcolor);
        textColor.getEditor().getEditorComponent().setForeground(tcolor);

        textSize.setEditable(true);
        textSize.setSize(300,30);
        textSize.setFont(new Font("Segoe UI", 3, Integer.parseInt(readSettings("textSize"))));
        textSize.setForeground(tcolor);
        textSize.setBackground(bcolor);
        textSize.setText(readSettings("textSize"));
        textSize.setToolTipText("Hit Enter To Save!");
        textSize.setFocusable(true);
        textSize.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    e.consume(); //removes return charater
                    String filepath = systemFilePath() + "/TextSettings.csv";
                    String sizeOfText = textSize.getText();
                    java.util.List<TextSettingsPOJO> beans;
                    try {
                        beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(filepath))
                                .withType(TextSettingsPOJO.class)
                                .build()
                                .parse();
                        for (TextSettingsPOJO TS : beans) {
                            if(TS.getTextSize() != Integer.parseInt(textSize.getText())){
                                writeTextSize(TS,textSize, Integer.parseInt(sizeOfText));
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        Settings.writeLogFile(ex.toString());
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        textboxSize.setEditable(true);
        textboxSize.setSize(300,30);
        textboxSize.setFont(new Font("Segoe UI", 3, Integer.parseInt(readSettings("textSize"))));
        textboxSize.setText(readSettings("textboxSize"));
        textboxSize.setForeground(tcolor);
        textboxSize.setBackground(bcolor);
        textboxSize.setToolTipText("Hit Enter To Save!");
        textboxSize.setFocusable(true);
        textboxSize.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    e.consume(); //removes return charater
                    String filepath = systemFilePath() + "/TextSettings.csv";
                    String sizeOfText = textboxSize.getText();
                    java.util.List<TextSettingsPOJO> beans;
                    try {
                        beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(filepath))
                                .withType(TextSettingsPOJO.class)
                                .build()
                                .parse();
                        for (TextSettingsPOJO TS : beans) {
                            if(TS.getTextSize() != Integer.parseInt(textboxSize.getText())){
                                writeTextboxSize(TS,textboxSize, Integer.parseInt(sizeOfText));
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        Settings.writeLogFile(ex.toString());
                        throw new RuntimeException(ex);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        textboxColor.setEditable(true);
        textboxColor.setSize(300,30);
        textboxColor.setFont(new Font("Segoe UI", 3, Integer.parseInt(readSettings("textSize"))));
        textboxColor.setFocusable(true);
        textboxColor.setForeground(tcolor);
        textboxColor.setBackground(bcolor);
        textboxColor.setSelectedIndex(Arrays.asList(colors).indexOf(readSettings("textboxColor")));
        textboxColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String selectedTextboxColor = textboxColor.getSelectedItem().toString();
                    String filepath = systemFilePath() + "/TextSettings.csv";
                    java.util.List<TextSettingsPOJO> beans;
                    try {
                        beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(filepath))
                                .withType(TextSettingsPOJO.class)
                                .build()
                                .parse();
                        for (TextSettingsPOJO TS : beans) {
                            if(!TS.getTextColor().equals(selectedTextboxColor)){
                                writeTextboxColor(TS,textColor,selectedTextboxColor);
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        Settings.writeLogFile(ex.toString());
                        throw new RuntimeException(ex);
                    }
                }
        });
        textboxColor.getEditor().getEditorComponent().setBackground(bcolor);
        textboxColor.getEditor().getEditorComponent().setForeground(tcolor);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(textColor_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textSize_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textboxSize_label, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textboxColor_label, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(textColor)
                                        .addComponent(textSize)
                                        .addComponent(textboxSize)
                                        .addComponent(textboxColor)
                                )
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                )
                        )
                        .addComponent(textSettingPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(textSettingPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(textColor_label)
                                        .addComponent(textColor)
                                )
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(textSize_label)
                                        .addComponent(textSize)
                                )
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(textboxSize_label)
                                        .addComponent(textboxSize)
                                )
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(textboxColor_label)
                                        .addComponent(textboxColor)
                                )

                        )
        );
        pack();

    }
    public static String systemFilePath() {
        String filePath = System.getProperty("user.home");
        String os = SystemUtils.OS_NAME;
        if (os.equals("Linux")) {
            filePath = filePath + "/.local/share/StrategemPractice";
        } //
        if(SystemUtils.IS_OS_MAC){filePath = "/StrategemPractice";}
        if(SystemUtils.IS_OS_WINDOWS){filePath = "/Library/Applications Support/StrategemPractice";}

        return filePath;
    }
    public String keyBindText(String key) {
        java.util.List<String> list = new ArrayList<>();
        String filepath = systemFilePath() + "/keybinds.csv";

        CSVReader reader;
        {
            try {
                reader = new CSVReader(new FileReader(filepath));
                String[] line;
                while ((line = reader.readNext()) != null) {
                    if(Arrays.toString(line).replace("[", "").replace("]","").equals("key")){ //empty body?
                    }else{
                        list.add(Arrays.toString(line).replace("[", "").replace("]", ""));
                    }
                    if(line[0].equals(key)){
                        return line[1];
                    }
                }
                reader.close();
            } catch (IOException IOerr) {
                writeLogFile(IOerr.toString());
                throw new RuntimeException(IOerr);
            } catch (CsvValidationException e) {
                writeLogFile(e.toString());
                throw new RuntimeException(e);
            }
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

        return "";
    }
    public static void writeDefaults(TextSettingsPOJO TS){
        String file = systemFilePath() + "/TextSettings.csv";
        Path path = Paths.get(file);

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            List<TextSettingsPOJO> beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(file)).withType(TextSettingsPOJO.class)
                    .build()
                    .parse();

            try (BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                bw.write("textColor,textSize,textboxSize,textboxColor");
                bw.newLine();
                String line = String.format("%s,%d,%d,%s", "BLACK", 36,300,"WHITE");
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException x) {
            writeLogFile(x.toString());
            x.printStackTrace();
        }
    }
    public static void writeTextSize(TextSettingsPOJO TS, JTextPane textPane, int textSize ){
        String file = systemFilePath() + "/TextSettings.csv";
        Path path = Paths.get(file);

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            List<TextSettingsPOJO> beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(file)).withType(TextSettingsPOJO.class)
                    .build()
                    .parse();

            try (BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                bw.write("textColor,textSize,textboxSize,textboxColor");
                bw.newLine();
                for (TextSettingsPOJO t : beans) {
                    if(TS.getTextSize() == t.getTextSize()){
                        String line = String.format("%s,%d,%d,%s", t.getTextColor(), textSize,t.getTextboxSize(),t.getTextboxColor());
                        bw.write(line);
                        bw.newLine();
                    }else{
                        String line = String.format("%s,%d,%d,%s", t.getTextColor(), t.getTextSize(),t.getTextboxSize(),t.getTextboxColor());
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException x) {
            writeLogFile(x.toString());
            x.printStackTrace();
        }
    }
    public static void writeTextboxSize(TextSettingsPOJO TS, JTextPane textPane, int textboxSize ){
        String file = systemFilePath() + "/TextSettings.csv";
        Path path = Paths.get(file);

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            List<TextSettingsPOJO> beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(file)).withType(TextSettingsPOJO.class)
                    .build()
                    .parse();

            try (BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                bw.write("textColor,textSize,textboxSize,textboxColor");
                bw.newLine();
                for (TextSettingsPOJO t : beans) {
                    if(TS.getTextboxSize() == t.getTextboxSize()){
                        String line = String.format("%s,%d,%d,%s", t.getTextColor(), t.getTextSize(),textboxSize,t.getTextboxColor());
                        bw.write(line);
                        bw.newLine();
                    }else{
                        String line = String.format("%s,%d,%d,%s", t.getTextColor(), t.getTextSize(),t.getTextboxSize(),t.getTextboxColor());
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException x) {
            writeLogFile(x.toString());
            x.printStackTrace();
        }
    }
    public static void writeTextColor(TextSettingsPOJO TS, JComboBox comboBox, String selectedTextColor ){
        String file = systemFilePath() + "/TextSettings.csv";
        Path path = Paths.get(file);

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            List<TextSettingsPOJO> beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(file)).withType(TextSettingsPOJO.class)
                    .build()
                    .parse();

            try (BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                bw.write("textColor,textSize,textboxSize,textboxColor");
                bw.newLine();
                for (TextSettingsPOJO t : beans) {
                    if(TS.getTextColor().equals(t.getTextColor())){
                        String line = String.format("%s,%d,%d,%s", selectedTextColor, t.getTextSize(),t.getTextboxSize(),t.getTextboxColor());
                        bw.write(line);
                        bw.newLine();
                    }else{
                        String line = String.format("%s,%d,%d,%s", t.getTextColor(), t.getTextSize(),t.getTextboxSize(),t.getTextboxColor());
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException x) {
            writeLogFile(x.toString());
            x.printStackTrace();
        }
    }
    public static void writeTextboxColor(TextSettingsPOJO TS, JComboBox comboBox, String selectedTextboxColor ){
        String file = systemFilePath() + "/TextSettings.csv";
        Path path = Paths.get(file);

          /*
  BufferedWriter bw = Files.newBufferedWriter(Paths.get(Objects.requireNonNull(TextSettings.class.getResource("TextSettings.csv")).toURI()),
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)){}
  */

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            List<TextSettingsPOJO> beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(file)).withType(TextSettingsPOJO.class)
                    .build()
                    .parse();

            try (BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                bw.write("textColor,textSize,textboxSize,textboxColor");
                bw.newLine();
                for (TextSettingsPOJO t : beans) {
                    if(TS.getTextboxColor().equals(t.getTextboxColor())){
                        String line = String.format("%s,%d,%d,%s", t.getTextColor(), t.getTextSize(),t.getTextboxSize(),selectedTextboxColor);
                        bw.write(line);
                        bw.newLine();
                    }else{
                        String line = String.format("%s,%d,%d,%s", t.getTextColor(), t.getTextSize(),t.getTextboxSize(),t.getTextboxColor());
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException x) {
            writeLogFile(x.toString());
            x.printStackTrace();
        }
    }
    public static String readSettings(String header){
        InputStream is = TextSettings.class.getClassLoader().getResourceAsStream("TextSettings.csv");
        String file = systemFilePath() + "/TextSettings.csv";
        CSVReader reader = new CSVReader(new InputStreamReader(is));
        Path path = Paths.get(file);

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            List<TextSettingsPOJO> beans = new CsvToBeanBuilder<TextSettingsPOJO>(new FileReader(file)).withType(TextSettingsPOJO.class)
                    .build()
                    .parse();

            try (BufferedReader br = Files.newBufferedReader(path)) {
                for (TextSettingsPOJO t : beans) {
                    switch(header){
                        case "textColor" -> {return t.getTextColor();}
                        case "textboxColor" -> {return t.getTextboxColor();}
                        case "textSize" -> {return String.format("%d",t.getTextSize());}
                        case "textboxSize" -> {return String.format("%d",t.getTextboxSize());}
                        default -> System.out.println("Wrong header name dingo");
                    }
                }
            }
        } catch (IOException x) {
            writeLogFile(x.toString());
            x.printStackTrace();
        }
        return "";
    }
    public static void writeLogFile(String error) {
        CSVWriter writer;
        {
            try {
                writer = new CSVWriter(new FileWriter(systemFilePath() + "/Logfile.log", true), ',', '"', '\\', CSVWriter.DEFAULT_LINE_END);
                writer.writeNext(new String[]{error});
                writer.close();
            } catch (IOException IOerr) {
                throw new RuntimeException(IOerr);
            }
        }
    }
    public static Color customGetColors(String color){
        Color returnedColor;
        switch (color){
            case "BLACK"      -> returnedColor = Color.BLACK;
            case "BLUE"       -> returnedColor = Color.BLUE;
            case "CYAN"       -> returnedColor = Color.CYAN;
            case "DARK_GRAY"  -> returnedColor = Color.DARK_GRAY;
            case "GRAY"       -> returnedColor = Color.GRAY;
            case "GREEN"      -> returnedColor = Color.GREEN;
            case "LIGHT_GRAY" -> returnedColor = Color.LIGHT_GRAY;
            case "MAGENTA"    -> returnedColor = Color.MAGENTA;
            case "ORANGE"     -> returnedColor = Color.ORANGE;
            case "PINK"       -> returnedColor = Color.PINK;
            case "RED"        -> returnedColor = Color.RED;
            case "WHITE"      -> returnedColor = Color.WHITE;
            case "YELLOW"     -> returnedColor = Color.YELLOW;
            default           -> returnedColor = Color.GRAY;
        }

        return returnedColor;
    }
    public static void illegalKey(){
        JOptionPane.showMessageDialog(
                new JFrame(),
                "Illegal Key Bind!",
                "Illegal Key Bind!",
                JOptionPane.ERROR_MESSAGE);
    }
    class Renderer extends DefaultListCellRenderer {
        @Override
        public void setOpaque(boolean makeBackGroundVisible) {
            super.setOpaque(true);     // THIS DOES THE TRICK
        }
        @Override
        public Component getListCellRendererComponent(JList<?> list,
                                                      Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText((String)value);
            setBackground(Color.cyan);
            return this;
        }
    }
}
