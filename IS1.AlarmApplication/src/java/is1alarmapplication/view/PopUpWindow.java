/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1alarmapplication.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author admin
 */
public class PopUpWindow extends JFrame{
    
    public PopUpWindow(String msg) {
        super("notification");
        this.add(new JLabel(msg));        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(300, 300, 150, 100);
        setVisible(true);
    }
}
