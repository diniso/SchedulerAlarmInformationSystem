/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entiteti.Mesto;
import entiteti.Obaveza;
import is1userapplication.Main;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class MainFrame extends javax.swing.JFrame {
    
    private final String username;
    private final String password;
    
    private datechooser.beans.DateChooserPanel dateChooserPanel;
    
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton bacisAlarmRadioButton;
    private javax.swing.JRadioButton listAlarmRadioButton;
    private javax.swing.JRadioButton periodicAlarmRadioButton;
    
    private javax.swing.JLabel brojAlarmLabel;
    private javax.swing.JLabel periodaLabel;
    private javax.swing.JLabel songLabel;
    private javax.swing.JLabel timeLabel;
    
    private javax.swing.JTextField brojAlarmaField;
    private javax.swing.JTextField songField; 
    private javax.swing.JTextField timeField;
    private javax.swing.JTextField periodaField;
    
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    
    private javax.swing.JButton createAlarmButton;
    private javax.swing.JButton listAllSongsButton;
    private javax.swing.JButton playSongButtom;
    
    private javax.swing.JMenu menu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem createItem;
    private javax.swing.JMenuItem showItem;
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

     public MainFrame(String username , String password) {
        this.username = username;
        this.password = password;
        initComponents();
        myInit();
        
        this.setBounds(260, 190, 588, 563);
        setVisible(true);
    }
     
     private void myInit() {
        bacisAlarmRadioButton.doClick();
        brojAlarmaField.setEnabled(false);
        periodaField.setEnabled(false);
        
        bacisAlarmRadioButton.addActionListener(e-> {
            brojAlarmaField.setEnabled(false);
            periodaField.setEnabled(false);
        });
        
        listAlarmRadioButton.addActionListener(e-> {
            brojAlarmaField.setEnabled(true);
            periodaField.setEnabled(true);
        });
        
        periodicAlarmRadioButton.addActionListener(e-> {
            brojAlarmaField.setEnabled(false);
            periodaField.setEnabled(true);
        });
        
    }
     
    
    private void initComponents() {


        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        songLabel = new javax.swing.JLabel();
        songField = new javax.swing.JTextField();
        playSongButtom = new javax.swing.JButton();
        listAllSongsButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        createAlarmButton = new javax.swing.JButton();
        periodicAlarmRadioButton = new javax.swing.JRadioButton();
        listAlarmRadioButton = new javax.swing.JRadioButton();
        bacisAlarmRadioButton = new javax.swing.JRadioButton();
        brojAlarmLabel = new javax.swing.JLabel();
        brojAlarmaField = new javax.swing.JTextField();
        periodaLabel = new javax.swing.JLabel();
        periodaField = new javax.swing.JTextField();
        dateChooserPanel = new datechooser.beans.DateChooserPanel();
        timeLabel = new javax.swing.JLabel();
        timeField = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        showItem = new javax.swing.JMenuItem();
        createItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
 
        songLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        songLabel.setText("song name:");
        songLabel.setOpaque(true);

        songField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        playSongButtom.setText("play song");
        playSongButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playSongButtomActionPerformed(evt);
            }
        });

        listAllSongsButton.setText("list all played songs");
        listAllSongsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listAllSongsButtonActionPerformed(evt);
            }
        });


        createAlarmButton.setText("create alarm");
        createAlarmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createAlarmButtonActionPerformed(evt);
            }
        });

        buttonGroup1.add(periodicAlarmRadioButton);
        periodicAlarmRadioButton.setText("periodic alarm");
        periodicAlarmRadioButton.setActionCommand("periodicAlarmRadioButton");

        buttonGroup1.add(listAlarmRadioButton);
        listAlarmRadioButton.setText("alarm from list");
        listAlarmRadioButton.setActionCommand("listAlarmRadioButton");

        buttonGroup1.add(bacisAlarmRadioButton);
        bacisAlarmRadioButton.setText("alarm");
        bacisAlarmRadioButton.setActionCommand("bacisAlarmRadioButton");

        brojAlarmLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        brojAlarmLabel.setText("broj alarma");
        brojAlarmLabel.setOpaque(true);

        brojAlarmaField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        periodaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        periodaLabel.setText("perioda [seconds]");
        periodaLabel.setOpaque(true);

        periodaField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText("time [hh:mm:ss]");
        timeLabel.setOpaque(true);

        timeField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(bacisAlarmRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(periodicAlarmRadioButton)))
                .addGap(18, 105, Short.MAX_VALUE)
                .addComponent(listAlarmRadioButton)
                .addGap(55, 55, 55))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(createAlarmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(periodaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(brojAlarmLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(timeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(periodaField, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brojAlarmaField, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(timeField))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bacisAlarmRadioButton)
                    .addComponent(periodicAlarmRadioButton)
                    .addComponent(listAlarmRadioButton))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(periodaField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(periodaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(brojAlarmLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(brojAlarmaField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(createAlarmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dateChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(songLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)
                        .addComponent(songField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(playSongButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(listAllSongsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(songLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(songField, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(playSongButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(listAllSongsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        menu.setText("Obligations");

        showItem.setText("show");
        showItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showItemActionPerformed(evt);
            }
        });
        menu.add(showItem);

        createItem.setText("new");
        createItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createItemActionPerformed(evt);
            }
        });
        menu.add(createItem);

        menuBar.add(menu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }                                                             

    private void listAllSongsButtonActionPerformed(java.awt.event.ActionEvent evt) {   
        new Thread() {
            private String path = "http://localhost:8080/Servis/resources/music";
            @Override
            public void run() {

                try {
                    String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Authorization", decoding);
                    conn.setRequestMethod("GET");
                    
                    System.out.println("Response status: " + conn.getResponseCode());
                    
            
                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
        
    }                                                  

    private void showItemActionPerformed(java.awt.event.ActionEvent evt) {                                         
        new Thread() {
            private String path = "http://localhost:8080/Servis/resources/planer";
          
            
            @Override
            public void run() {
                try {
                    String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Authorization", decoding);
                    conn.setRequestMethod("GET");
                    
                    System.out.println("Response status: " + conn.getResponseCode());
            
                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }.start();
        
    }                                        

    private void playSongButtomActionPerformed(java.awt.event.ActionEvent evt) { 
        String songName = songField.getText();
        new Thread() {
            private String path = "http://localhost:8080/Servis/resources/music/play";
            private String songName ;
            
            public Thread setSong(String songName) {
                this.songName = songName;
                return this;
            }
            @Override
            public void run() {

                try {
                    String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                    path = path + "?songName=" + (songName.replace(" ", "%20"));
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Authorization", decoding);
                    conn.setRequestMethod("GET");
                    
                    System.out.println("Response status: " + conn.getResponseCode());
            
                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.setSong(songName).start();
    }                                              

    private void createItemActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String[] mesta = Main.getAllMesta();
        new CreateObligationDialog(this, mesta , username , password);
    }                                          
       
    private boolean checkTimeFormat(String time) {
        String[] split = time.split(":");
        if (split.length != 3) return false;
        try {
            int sati = Integer.parseInt(split[0]);
            if (sati < 0 || sati > 23) return false;
            
            int minuti = Integer.parseInt(split[1]);
            if (minuti < 0 || minuti > 59) return false;
            
            int sekundi = Integer.parseInt(split[2]);
            if (sekundi < 0 || sekundi > 59) return false;
            
            return true;
            
        }catch (Exception e) {e.printStackTrace();}
        return false;
    }

    private void createAlarmButtonActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        String command = buttonGroup1.getSelection().getActionCommand();
        
        String time = timeField.getText();
        boolean check = checkTimeFormat(time);
            if (!check) {
                new PopUpWindow("Vreme nije u dobro formatu.");
                return;
            }
        
        Date datum = dateChooserPanel.getCurrent().getTime();
        String date = "" + (datum.getYear() + 1900) + "-" + (datum.getMonth() + 1) + "-" + datum.getDate();
        date += " " + time;
  
        
        
        if (command.equals("bacisAlarmRadioButton")) {
            
            new Thread() {
                private String path = "http://localhost:8080/Servis/resources/alarm";
                private String time;
                
                public Thread setTime(String time) {
                    this.time = time;
                    return this;
                }
                
                @Override
                public void run() {
                    try {
                        String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                        path = path + "?time=" + time.replace(" ", "%20");
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setRequestProperty("Authorization", decoding);
                        conn.setRequestMethod("POST");
                        
                        System.out.println("Response status: " + conn.getResponseCode());
            
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
            }.setTime(date).start();
            
        }else if (command.equals("periodicAlarmRadioButton")) {
            int perioda = -1;
            try {
                perioda = Integer.parseInt(periodaField.getText());
                if (perioda < 0) {
                    new PopUpWindow("Perioda treba da bude pozitivan broj.");
                    return;
                }
            }
            catch (Exception ex) {
                new PopUpWindow("Perioda treba da bude broj.");
                return;
            }
            
            new Thread() {
                private String path = "http://localhost:8080/Servis/resources/alarm/periodic";
                private String time;
                private int perioda;
                
                public Thread setParams(String time , int perioda) {
                    this.time = time;
                    this.perioda = perioda;
                    return this;
                }
                
                @Override
                public void run() {
                    try {
                        String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                        path = path + "?time=" + time.replace(" ", "%20") + "&perioda=" + perioda;
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setRequestProperty("Authorization", decoding);
                        conn.setRequestMethod("POST");
                        
                        System.out.println("Response status: " + conn.getResponseCode());
            
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
            }.setParams(date , perioda).start();

            
        }else if (command.equals("listAlarmRadioButton")){
            int perioda = -1 , numOfAlarm = -1;
            try {
                perioda = Integer.parseInt(periodaField.getText());
                numOfAlarm = Integer.parseInt(brojAlarmaField.getText());
                if (perioda < 0 || numOfAlarm < 0) {
                    new PopUpWindow("Perioda i broj alarm treba da budu pozitivni brojevi.");
                    return;
                }
            }
            catch (Exception ex) {
                new PopUpWindow("Perioda i broj alarm treba da budu brojevi.");
                return;
            }
            
            new Thread() {
                private String path = "http://localhost:8080/Servis/resources/alarm/random";
                private String time;
                private int perioda;
                private int brAlarma;
                
                public Thread setParams(String time , int perioda , int brojAlarma) {
                    this.time = time;
                    this.perioda = perioda;
                    brAlarma = brojAlarma;
                    return this;
                }
                
                @Override
                public void run() {
                    try {
                        String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                        path = path + "?time=" + time.replace(" ", "%20") + "&perioda=" + perioda + "&number=" + brAlarma;
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setRequestProperty("Authorization", decoding);
                        conn.setRequestMethod("POST");
                        
                        System.out.println("Response status: " + conn.getResponseCode());
            
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
            }.setParams(date , perioda, numOfAlarm).start();
            
        }
    }                                                 
                  
}
