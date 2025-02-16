package me.abbatrombone.traz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.lang3.SystemUtils;

public class Settings extends JFrame {

    private JPanel settingPanel;

    private JTextPane up_key;
    private JTextPane down_key;
    private JTextPane left_key;
    private JTextPane right_key;

    private JLabel title;
    private JLabel up_label;
    private JLabel down_label;
    private JLabel left_label;
    private JLabel right_label;


    public Settings() {
        initComponents();
        File newDirectory = new File(systemFilePath());
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }
        File settingsFile = new File(systemFilePath() + "/keybinds.csv");
        File logfiile = new File(systemFilePath() + "/Logfile.log");
        if(!settingsFile.exists()){
            try {
                settingsFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(!logfiile.exists()){
            try {
                logfiile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void initComponents() {
        settingPanel = new JPanel();
        title = new JLabel();
        up_key = new JTextPane();
        down_key = new JTextPane();
        left_key = new JTextPane();
        right_key = new JTextPane();
        up_label = new JLabel();
        down_label = new JLabel();
        left_label = new JLabel();
        right_label = new JLabel();

        settingPanel.setBackground(new Color(51, 51, 51));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");

        String up    = "\u21E7";
        String down  = "\u21E9";
        String left  = "\u21E6";
        String right = "\u21E8";

        title.setBackground(new Color(255, 255, 255));
        title.setFont(new Font("Segoe UI", Font.BOLD, 24)); // NOI18N
        title.setForeground(new Color(255, 255, 255));
        title.setText("Settings");
        title.setBounds(142,142,700,700);

        GroupLayout settingPanelLayout = new GroupLayout(settingPanel);
        settingPanel.setLayout(settingPanelLayout);
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


        up_label.setBackground(new Color(255, 255, 255));
        up_label.setFont(new Font("Segoe UI", Font.BOLD, 24)); // NOI18N
        up_label.setForeground(new Color(0, 0, 0));
        up_label.setText(up+" Key Bind");
        up_label.setBounds(142,142,700,700);

        down_label.setBackground(new Color(255, 255, 255));
        down_label.setFont(new Font("Segoe UI", Font.BOLD, 24)); // NOI18N
        down_label.setForeground(new Color(0, 0, 0));
        down_label.setText(down+" Key Bind");
        down_label.setBounds(142,142,700,700);

        left_label.setBackground(new Color(255, 255, 255));
        left_label.setFont(new Font("Segoe UI", Font.BOLD, 24)); // NOI18N
        left_label.setForeground(new Color(0, 0, 0));
        left_label.setText(left+" Key Bind");
        left_label.setBounds(142,142,700,700);

        right_label.setBackground(new Color(255, 255, 255));
        right_label.setFont(new Font("Segoe UI", Font.BOLD, 24)); // NOI18N
        right_label.setForeground(new Color(0, 0, 0));
        right_label.setText(right+" Key Bind");
        right_label.setBounds(142,142,700,700);

        up_key.setEditable(true);
        up_key.setSize(300,30);
        up_key.setFont(new Font("Segoe UI", 3, 14));
        up_key.setText(keyBindText("up"));
        up_key.setFocusable(true);
        up_key.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(up_key.getText().length() == 1){up_key.setText("");}
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //this listens for all key changes and updates them. Not sure of a better way to do this, though there must be one
                String filepath = systemFilePath() + "/keybinds.csv";
                List<keybindsPOJO> beans;
                int keycode = e.getKeyCode();
                try {
                    beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(filepath))
                            .withType(keybindsPOJO.class)
                            .build()
                            .parse();
                    for (keybindsPOJO kb : beans) {

                        switch (kb.getKey()){
                            case "up"    -> {if(!kb.getBind().equals(up_key.getText())){writeKeyBinds(kb,up_key);}}
                            case "down"  -> {if(!kb.getBind().equals(down_key.getText())){writeKeyBinds(kb,down_key);}}
                            case "left"  -> {if(!kb.getBind().equals(left_key.getText())){writeKeyBinds(kb,left_key);}}
                            case "right" -> {if(!kb.getBind().equals(right_key.getText())){writeKeyBinds(kb,right_key);}}
                            default -> throw new IllegalStateException("Unexpected value: " + kb.getKey());
                        }
                        System.out.println(kb.getKey() +":"+kb.getBind());
                    }
                } catch (FileNotFoundException ex) {
                    Settings.writeLogFile(ex.toString());
                    throw new RuntimeException(ex);
                }
            }
        });

        down_key.setEditable(true);
        down_key.setSize(300,30);
        down_key.setFont(new Font("Segoe UI", 3, 14));
        down_key.setText(keyBindText("down"));
        down_key.setFocusable(true);
        down_key.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(down_key.getText().length() == 1){down_key.setText("");}
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String filepath = systemFilePath() + "/keybinds.csv";
                List<keybindsPOJO> beans;
                int keycode = e.getKeyCode();
                try {
                    beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(filepath))
                            .withType(keybindsPOJO.class)
                            .build()
                            .parse();
                    for (keybindsPOJO kb : beans) {
                        if(kb.getKey().equals("down")){
                            if(!kb.getBind().equals(down_key.getText())){writeKeyBinds(kb,down_key);}
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Settings.writeLogFile(ex.toString());
                    throw new RuntimeException(ex);
                }
            }
        });

        left_key.setEditable(true);
        left_key.setSize(300,30);
        left_key.setFont(new Font("Segoe UI", 3, 14));
        left_key.setText(keyBindText("left"));
        left_key.setFocusable(true);
        left_key.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(left_key.getText().length() == 1){left_key.setText("");}
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String filepath = systemFilePath() + "/keybinds.csv";
                List<keybindsPOJO> beans;
                int keycode = e.getKeyCode();
                try {
                    beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(filepath))
                            .withType(keybindsPOJO.class)
                            .build()
                            .parse();
                    for (keybindsPOJO kb : beans) {
                        if(kb.getKey().equals("left")){
                            if(!kb.getBind().equals(left_key.getText())){writeKeyBinds(kb,left_key);}
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Settings.writeLogFile(ex.toString());
                    throw new RuntimeException(ex);
                }

            }
        });

        right_key.setEditable(true);
        right_key.setSize(300,30);
        right_key.setFont(new Font("Segoe UI", 3, 14));
        right_key.setText(keyBindText("right"));
        right_key.setFocusable(true);
        right_key.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(right_key.getText().length() == 1){right_key.setText("");}
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String filepath = systemFilePath() + "/keybinds.csv";
                List<keybindsPOJO> beans;
                int keycode = e.getKeyCode();
                try {
                    beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(filepath))
                            .withType(keybindsPOJO.class)
                            .build()
                            .parse();
                    for (keybindsPOJO kb : beans) {
                        if(kb.getKey().equals("right")){
                            if(!kb.getBind().equals(right_key.getText())){writeKeyBinds(kb,right_key);}
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Settings.writeLogFile(ex.toString());
                    throw new RuntimeException(ex);
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                //.addGap(5, 5, 5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(up_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(down_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(left_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(right_label, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(up_key)
                                        .addComponent(down_key)
                                        .addComponent(left_key)
                                        .addComponent(right_key)
                                )
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                )
                        )
                        //.addGap(15, 5, 5))
                        .addComponent(settingPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(settingPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(up_label)
                                        .addComponent(up_key)
                                )
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(down_label)
                                        .addComponent(down_key)
                                )
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(left_label)
                                        .addComponent(left_key)
                                )
                                .addGap(10)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(right_label)
                                        .addComponent(right_key)
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
        if(SystemUtils.IS_OS_MAC){filePath = "/RandomStudent";}
        if(SystemUtils.IS_OS_WINDOWS){filePath = "/Library/Applications Support/StrategemPractice";}

        return filePath;
    }
    public String keyBindText(String key) {
        List<String> list = new ArrayList<>();
        String filepath = systemFilePath() + "/keybinds.csv";

        CSVReader reader;
        {
            try {
                reader = new CSVReader(new FileReader(filepath));
                String[] line;
                while ((line = reader.readNext()) != null) {
                    if(Arrays.toString(line).replace("[", "").replace("]","").equals("key")){
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
    public String[] settingsFile(){
        String filepath = systemFilePath() + "/keybinds.csv";
        List<keybindsPOJO> beans;
        try {
            beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(filepath))
                    .withType(keybindsPOJO.class)
                    .build()
                    .parse();
            for (keybindsPOJO kb : beans) {

                switch (kb.getKey()){
                    case "up"   ->  {if(!kb.getBind().equals(up_key.getText())){System.out.println("up");}}
                    case "down"  ->  {if(!kb.getBind().equals(down_key.getText())){System.out.println("down");}}
                    case "left" ->  {if(!kb.getBind().equals(left_key.getText())){System.out.println("left" );}}
                    case "right" ->  {if(!kb.getBind().equals(right_key.getText())){System.out.println("right");}}
                    default -> throw new IllegalStateException("Unexpected value: " + kb.getKey());
                }
                System.out.println(kb.getKey() +":"+kb.getBind());
            }
        } catch (FileNotFoundException ex) {
            Settings.writeLogFile(ex.toString());
            throw new RuntimeException(ex);
        }
        String[] splitArray = new String[0];

        return splitArray;
    }
    public static void writeKeyBinds(keybindsPOJO kb,JTextPane textPane){
        String file = systemFilePath() + "/keybinds.csv";

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        try {
            List<keybindsPOJO> beans = new CsvToBeanBuilder<keybindsPOJO>(new FileReader(file)).withType(keybindsPOJO.class)
                    .build()
                    .parse();
            for (keybindsPOJO k : beans) {
                if (k.getKey().equals(kb.getKey())) {
                    k.setBind(textPane.getText());
                }
            }
            Path path = Paths.get(file);
            try (BufferedWriter bw = Files.newBufferedWriter(path,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                bw.write("key,bind");
                bw.newLine();
                for (keybindsPOJO k : beans) {
                    if(kb.getKey().equals(k.getKey())){
                        String line = String.format("%s,%s", k.getKey(), k.getBind());
                        bw.write(line);
                        bw.newLine();
                    }else{
                       String line = String.format("%s,%s", k.getKey(), k.getBind());
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
    public static void clearCsv(String path) throws Exception {
        //might want to have it rewrite to defualts instead
        FileWriter fw = new FileWriter(path,false);
        PrintWriter pw = new PrintWriter(fw,false);
        pw.flush();
        pw.close();
        fw.close();
    }

}
