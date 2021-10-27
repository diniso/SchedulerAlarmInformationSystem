/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm.threads;

import entiteti.NeperiodicniAlarm;
import is1alarmapplication.Main;
import java.util.Date;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

/**
 *
 * @author admin
 */
public class NonPeriodicAlarmThread extends AlarmThread{

    private Date vremeZvonaAlarma;
    
    public NonPeriodicAlarmThread(NeperiodicniAlarm a , JMSContext context, JMSProducer producer) {
        super(context , producer);
        vremeZvonaAlarma = a.getDatumVreme();
        username = a.getKorisnik().getKorisnickoIme();
        songName = a.getPesma().getNazivPesme();
        idAlarma = a.getIdAlarma();
    }
    
    @Override
    protected void precalculate() throws InterruptedException{
        this.timeToWait = vremeZvonaAlarma.getTime() - (new Date()).getTime();
        if (timeToWait <= 0) throw new InterruptedException();
    }

    @Override
    protected void finish() {
        Main.removeAlarmThread(this);
        System.out.println("Send message to uredjaj to play music");
        sendMessageToPlaySong();
    }
    
}
