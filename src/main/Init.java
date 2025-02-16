import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        private JOptionPane test;

        private final ClockListener clock = new ClockListener();
        private final Timer timer = new Timer(53, clock);
        private final SimpleDateFormat date = new SimpleDateFormat("mm.ss.SSS");
        private long startTime;

        public Init() {
            initComponents();
          //compareTextFields();
        }
        private void initComponents() {
            jPanel2 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            timeLabel = new javax.swing.JLabel();
            clear = new javax.swing.JButton();
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
            settingsMenu.add(keyBinds);

            keyBinds.setPreferredSize(new Dimension(200, 30));
            keyBinds.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    java.awt.EventQueue.invokeLater(() -> new Settings().setVisible(true));
                }});

            dropdown.setSize(10,30);
            dropdown.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.restart();
                    output.setText(Stratagems.stratagemArrows(Objects.requireNonNull(dropdown.getSelectedItem()).toString()));
                }
            });

            output.setEditable(false);
            output.setSize(300,30);
            output.setFont(new java.awt.Font("Segoe UI", 3, 14));
            output.setText(Stratagems.stratagemArrows(Objects.requireNonNull(dropdown.getSelectedItem()).toString()));
            output.setFocusable(false);

            intput.setEditable(true);
            intput.setCaretPosition(0);
            intput.setSize(300,30);
            intput.setToolTipText("Type in your stratagem Helldiver!");
            intput.requestFocus();
            intput.setCaretPosition(intput.getText().length());
            intput.setFont(new java.awt.Font("Segoe UI", 3, 14));
            StopWatch stopWatch = new StopWatch();
            keybindsPOJO pojo = new keybindsPOJO();
            intput.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    if(s.toString().length() == 1){stopWatch.start();}
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if(!timer.isRunning()){
                        start =0;
                        timer.start();
                    }
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_W -> intput.setText(String.valueOf(s.append(up)));
                        case KeyEvent.VK_A -> intput.setText(String.valueOf(s.append(left)));
                        case KeyEvent.VK_S -> intput.setText(String.valueOf(s.append(down)));
                        case KeyEvent.VK_D -> intput.setText(String.valueOf(s.append(right)));
                    }
                    int intputLength = intput.getText().length();
                    System.out.println(intput.getText().charAt(intputLength -1));

                if(!intput.getText().isEmpty() && !intput.getText().substring(intputLength - 1).equals(output.getText().substring(intputLength - 1, intputLength))){
                    intput.setText("");
                    s.setLength(0);
                    System.out.println("Wrong");
                    intputLength = 0;
                    PlaySound.playWrong();
                }
                if(intput.getText().length() == output.getText().length() && intput.getText().equals(output.getText())){
                    //play sound
                    stopWatch.reset();
                    intput.setText("");
                    s.setLength(0);
                    System.out.println("Correct");
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

            jLabel1.setBackground(new java.awt.Color(255, 255, 255));
            jLabel1.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24));
            jLabel1.setForeground(new java.awt.Color(255, 255, 255));
            jLabel1.setText("Stratagem Practice");
            jLabel1.setBounds(142,142,700,700);

            timeLabel.setBackground(new java.awt.Color(0, 0, 0));
            timeLabel.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24));
            timeLabel.setForeground(new java.awt.Color(0,0,0));
            timeLabel.setBounds(142,142,700,700);

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(optionPane)
                                    .addGap(142, 142, 142)
                                    .addComponent(jLabel1)
                                    .addContainerGap(161, Short.MAX_VALUE))
            );
            jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(optionPane)
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel1)
                                    .addContainerGap(23, Short.MAX_VALUE))
            );


            clear.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
            //clear.setBounds(10, 10, 700, 700);
            clear.setText("Clear");
            clear.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    s.setLength(0);
                    jButton2ActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    //.addGap(5, 5, 5)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(intput, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    )
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(timeLabel)
                                            .addComponent(dropdown)
                                    )
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            )
                            )
                            //.addGap(15, 5, 5))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(timeLabel)
                                    )
                                    .addGap(40)
                                    .addGroup(layout.createParallelGroup()
                                            .addComponent(intput, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    )

                            )
            );

            pack();

        }//
        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
            output.setText("");
            intput.setText("");
        }
    private void jTextPane1FocusGained(java.awt.event.FocusEvent evt) {
        intput.setSelectionStart(0);
        intput.setSelectionEnd(0);
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
