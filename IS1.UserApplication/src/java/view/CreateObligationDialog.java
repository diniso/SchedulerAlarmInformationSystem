/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import is1userapplication.Main;
import java.awt.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 *
 * @author admin
 */
public class CreateObligationDialog extends javax.swing.JDialog {
    
    private final String username , password;
    
    private javax.swing.JButton CreateButton;
    private javax.swing.JTextField DurationField;
    private javax.swing.JLabel DurationLabel;
    private javax.swing.JLabel LabelMesto;
    private javax.swing.JTextField TimeField;
    private datechooser.beans.DateChooserPanel dateChooserPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> mesta;
    private javax.swing.JScrollPane jScrollPane1;

    public CreateObligationDialog(JFrame parent , String[] mesta , String username , String password) { 
        super(parent, true);
        this.username = username;
        this.password = password;
        initComponents(mesta);
        myInit();
        this.setBounds(500, 300, 493, 402);
        this.setVisible(true);
    }
    
    private void myInit() {
        this.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
        
        CreateButton.addActionListener(e-> {
            int duration = -1;
            try {
                duration = Integer.parseInt(DurationField.getText());
                if (duration < 0) {
                   new PopUpWindow("Perioda treba da bude pozitivan broj.");
                    return; 
                }
            }
            catch(Exception ex) {
                new PopUpWindow("Perioda treba da bude broj.");
                return;
            }
            
            String mesto = mesta.getSelectedValue();
            if (mesto == null) {
                new PopUpWindow("Niste izbrali mesto.");
                return;
            }
            
            String time = TimeField.getText();
            boolean check = checkTimeFormat(time);
            if (!check) {
                new PopUpWindow("Vreme nije u dobro formatu.");
                return;
            }
    
            Date datum = dateChooserPanel.getCurrent().getTime();
            String date = "" + (datum.getYear() + 1900) + "-" + (datum.getMonth() + 1) + "-" + datum.getDate();
            date += " " + time;
            
            int idMes = Main.getIdMestoFromName(mesto); // date idMes naziv duation
            
            new Thread() {
                String vreme , path = "http://localhost:8080/Servis/resources/planer";
                int idMes , duration;
                
                public Thread setParam(String vreme , int idMes , int duration) {
                    this.vreme = vreme;
                    this.idMes = idMes;
                    this.duration = duration;
                    return this;
                }
                
                public void run() {
                    
                    try {
                    String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                    path = path + "?time=" + (vreme.replace(" ", "%20")) + "&idMes=" + idMes + "&name=Nova%20obaveza&duration=" + duration;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Authorization", decoding);
                    conn.setRequestMethod("POST");
                    
                    System.out.println("Response status: " + conn.getResponseCode());
            
                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                   
                }
                
            }.setParam(date , idMes , duration).start();
                  
            dispose();
        });
    }
                          
    private void initComponents(String[] mesta) {

        CreateButton = new javax.swing.JButton();
        DurationLabel = new javax.swing.JLabel();
        DurationField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        this.mesta = new JList<String>(mesta);
        LabelMesto = new javax.swing.JLabel();
        dateChooserPanel = new datechooser.beans.DateChooserPanel();
        jLabel1 = new javax.swing.JLabel();
        TimeField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        CreateButton.setText("create obligation");

        DurationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        DurationLabel.setText("Duration [seconds]");

        DurationField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        
        jScrollPane1.setViewportView(this.mesta);

        LabelMesto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelMesto.setText("Izaberite mesto");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Time[hh:mm:ss]");

        TimeField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(dateChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(LabelMesto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DurationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DurationField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TimeField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DurationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DurationField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TimeField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LabelMesto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateChooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CreateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
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
                 
}
