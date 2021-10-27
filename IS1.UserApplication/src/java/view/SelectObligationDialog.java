/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entiteti.Obaveza;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author admin
 */
public class SelectObligationDialog extends javax.swing.JDialog{
    
    private String username , password;
    
    public SelectObligationDialog(JFrame parent ,List<Obaveza> obaveze,String username ,String password ) {
        super(parent , true);
        this.username = username;
        this.password = password;
        myInit(obaveze);
        this.setBounds(250, 130, 800, 700);
        this.setVisible(true);
    }

    private void myInit(List<Obaveza> obaveze) {
        JPanel panel = new JPanel(new GridLayout(0 , 2 , 15 , 15));
        
        JScrollPane pane = new JScrollPane(panel); 
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        for (Obaveza o : obaveze) {
            ObavezaPanel pn = new ObavezaPanel(o , this , username , password);
            panel.add(pn);
        }
        
        this.add(pane);
    }
}
