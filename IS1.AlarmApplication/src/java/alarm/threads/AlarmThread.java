/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm.threads;

import is1alarmapplication.Main;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

/**
 *
 * @author admin
 */
public abstract class AlarmThread extends Thread{

    protected String username;
    protected int idAlarma;
    protected String songName;
    protected long timeToWait;
    protected JMSContext context;
    protected JMSProducer producer;
    
    public AlarmThread(JMSContext context, JMSProducer producer) {
        this.context = context;
        this.producer = producer;
    }
   
    @Override
    public void run() {
        try {
            precalculate();
            Thread.sleep(timeToWait);
            finish();
       
        } catch (InterruptedException ex) {
         //   Logger.getLogger(AlarmThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected abstract void precalculate() throws InterruptedException;
    
    protected abstract void finish();
    
    protected void sendMessageToPlaySong() {
        Main.createMessageToPlaySongs(context , producer , username , songName);
    }
    
    public int getIdAlarma() {
        return idAlarma;
    }
    
    public void setSongName(String songName) {
        this.songName = songName;
    }
}
