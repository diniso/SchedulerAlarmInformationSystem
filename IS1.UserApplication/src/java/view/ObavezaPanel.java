/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entiteti.Obaveza;
import is1userapplication.Main;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author admin
 */
public class ObavezaPanel extends javax.swing.JPanel {
    
    private int idOba;
    private JDialog parent;
    private String username , password;

    private javax.swing.JTextField krajField;
    private javax.swing.JButton alarmButtom;
    private javax.swing.JButton changeButton;
    private javax.swing.JLabel krajLabel;
    private javax.swing.JTextField mestoField;
    private javax.swing.JLabel mestoLabel;
    private javax.swing.JTextField nazivField;
    private javax.swing.JLabel nazivLabel;
    private javax.swing.JTextField pocetakField;
    private javax.swing.JLabel pocetakLabel;
    
    private static class PopUp extends JPopupMenu {
        private JMenuItem item;
        private int idOba;
        private String username , password;
        private JDialog parent;
        
        public PopUp(int idOba , String username , String password , JDialog parent) {
            item = new JMenuItem("delete");
            this.idOba = idOba;
            this.username = username;
            this.password = password;
            this.parent = parent;
            this.add(item);
            dodajOsluskivac();
        }

        private void dodajOsluskivac() {
            item.addActionListener(e-> {
                new Thread() {
                    private String username , password , path = "http://localhost:8080/Servis/resources/planer";
                    private int idOba;

                    public Thread setParam(int idOba , String username , String password) {
                        this.idOba = idOba;
                        this.username = username;
                        this.password = password;
                        return this;
                    }

                    @Override
                    public void run() {
                        try {
                            String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                            path = path + "/" + idOba;
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                            conn.setRequestProperty("Authorization", decoding);
                            conn.setRequestMethod("DELETE");

                            System.out.println("Response status: " + conn.getResponseCode());

                        } catch (Exception ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }.setParam(idOba, username, password).start();
            
            parent.dispose();
            });
        }
    }
    
    private static class PopUpClickListener extends MouseAdapter{
        private int idOba;
        private String username , password;
        private JDialog parent;
        
        public PopUpClickListener(int idOba, String username , String password , JDialog parent) {
            this.idOba = idOba;
            this.username = username;
            this.password = password;
            this.parent = parent;
        }
       
        public void mousePressed(MouseEvent evt) {
            if (evt.isPopupTrigger()) doPop(evt);
        }
        
        public void mouseReleased(MouseEvent evt) {
            if (evt.isPopupTrigger()) doPop(evt);
        }

        private void doPop(MouseEvent evt) {
             PopUp menu = new PopUp(idOba , username , password , parent);
             menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }
    
    public ObavezaPanel(Obaveza o , JDialog parent , String username , String password) {
        this.username = username;
        this.password = password;
        this.parent = parent;
        initComponents();
        myInit(o);
    }
    
    private void myInit(Obaveza o) {
        idOba = o.getIdObaveze();
        nazivField.setText(o.getNaziv());
        mestoField.setText(o.getMesto().getNazivGrada() + ", " + o.getMesto().getAdresa());
        Date vreme = o.getDatumVremePocetka();
        String vremePocetak = "" + (vreme.getYear()+ 1900) + "-" + (vreme.getMonth() + 1) + "-" + vreme.getDate() + " " + vreme.getHours() + ":" + vreme.getMinutes() + ":"  + vreme.getSeconds();
        vreme = o.getDatumVremeKraja();
        String vremeKraj = "" + (vreme.getYear()+ 1900) + "-" + (vreme.getMonth() + 1) + "-" + vreme.getDate() + " " + vreme.getHours() + ":" + vreme.getMinutes() + ":"  + vreme.getSeconds();
        pocetakField.setText(vremePocetak);
        krajField.setText(vremeKraj);
        if (o.getAlarm() != null) alarmButtom.setEnabled(false);
        
        changeButton.addActionListener(e-> {
    
            String timePocetak = pocetakField.getText();
            String timeKraj = krajField.getText();
            try {
              boolean check = checkTimeFormat(timePocetak.split(" ")[1]) &  checkTimeFormat(timeKraj.split(" ")[1]);
                if (!check) {
                    new PopUpWindow("Vreme nije u dobro formatu.");
                    return;
                }  
            }
            catch (Exception ex) {
                    new PopUpWindow("Vreme nije u dobro formatu.");
                    return;
            }
            
    
            int duration = -1;
            try {
                duration = (int)(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeKraj).getTime() - new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timePocetak).getTime()) / 1000;
            } catch (ParseException ex) {
                Logger.getLogger(ObavezaPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int idMes = Main.getIdMestoFromName(mestoField.getText()); 
            
            String naziv = nazivField.getText();
            
            System.out.println(timePocetak + " " + duration + " " + naziv + " " + idMes + " idOba=" + idOba);
            
            new Thread() {
                String vreme ,username , password , naziv, path = "http://localhost:8080/Servis/resources/planer";
                int idMes , duration , idOba;
                
                public Thread setParam(String vreme , int idMes , int duration , String naziv , int idOba, String username ,String password) {
                    this.vreme = vreme;
                    this.idMes = idMes;
                    this.duration = duration;
                    this.naziv = naziv;
                    this.idOba = idOba;
                    this.username = username;
                    this.password = password;
                    
                    return this;
                }
                
                public void run() {
                    
                    try {
                    String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                    path = path + "/" + idOba + "?time=" + (vreme.replace(" ", "%20")) + "&idMes=" + idMes + "&name=" + naziv.replace(" ", "%20") + "&duration=" + duration;
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Authorization", decoding);
                    conn.setRequestMethod("PUT");
                    
                    System.out.println("Response status: " + conn.getResponseCode());
            
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
                
            }.setParam(timePocetak , idMes , duration , naziv , idOba , username , password).start();
                  
            parent.dispose();
        });
        
        alarmButtom.addActionListener(e-> {
            int idMes = Main.getHomeIdFromUser(username);
            
            new Thread() {
                private String username , password , path = "http://localhost:8080/Servis/resources/planer";
                private int idOba , idMes;
                
                public Thread setParam(int idOba , int idMes , String username , String password) {
                    this.idOba = idOba;
                    this.idMes = idMes;
                    this.username = username;
                    this.password = password;
                    return this;
                }
                
                @Override
                public void run() {
                    try {
                        String decoding = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                        path = path + "/" + idOba + "?idMes=" + idMes;
                        URL url = new URL(path);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setRequestProperty("Authorization", decoding);
                        conn.setRequestMethod("POST");

                        System.out.println("Response status: " + conn.getResponseCode());
            
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.setParam(idOba, idMes, username, password).start();
            
            parent.dispose();
        });
        
        this.addMouseListener(new PopUpClickListener(idOba , username , password , parent) );
    }
                         
    private void initComponents() {

        nazivLabel = new javax.swing.JLabel();
        pocetakLabel = new javax.swing.JLabel();
        krajLabel = new javax.swing.JLabel();
        mestoLabel = new javax.swing.JLabel();
        nazivField = new javax.swing.JTextField();
        pocetakField = new javax.swing.JTextField();
        krajField = new javax.swing.JTextField();
        mestoField = new javax.swing.JTextField();
        alarmButtom = new javax.swing.JButton();
        changeButton = new javax.swing.JButton();

        nazivLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nazivLabel.setText("naziv:");

        pocetakLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pocetakLabel.setText("vremePocekta:");

        krajLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        krajLabel.setText("vremeKraj: ");

        mestoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mestoLabel.setText("mesto: ");

        nazivField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        pocetakField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        krajField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        mestoField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        alarmButtom.setText("create reminder");

        changeButton.setText("change");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(alarmButtom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(krajLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pocetakLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(nazivLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mestoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nazivField)
                    .addComponent(pocetakField)
                    .addComponent(krajField)
                    .addComponent(mestoField)
                    .addComponent(changeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nazivLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nazivField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pocetakLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pocetakField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(krajLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(krajField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mestoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mestoField, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(alarmButtom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
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
