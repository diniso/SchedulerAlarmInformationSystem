/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1uredjaj.view;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author admin
 */
public class SongsShowFrame extends JFrame{
    
    
    public SongsShowFrame(List<String> str) {
        super("all songs");
        init(str);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(300, 300, 300, 300);
        setVisible(true);
    }

    private void init(List<String> str) {
        this.setLayout(new GridLayout(0 , 1));
        
        for (int i = 0 ; i < str.size(); i++) {
            this.add(new JLabel("" + (i+1) + ": " + str.get(i)));
        }
    }
}
