/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alarm.threads;

import entiteti.NeperiodicniAlarm;
import entiteti.PeriodicniAlarm;
import is1alarmapplication.Main;
import java.util.Date;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;

/**
 *
 * @author admin
 */
public class PeriodicAlarmThread extends AlarmThread{
    
    private Date vremeZvonaAlarma;
    private Date vremePoslednjegZvona;
    private int perioda;
    
    public PeriodicAlarmThread(PeriodicniAlarm a , JMSContext context, JMSProducer producer) {
        super(context , producer);
        vremeZvonaAlarma = a.getDatumVreme();
        username = a.getKorisnik().getKorisnickoIme();
        idAlarma = a.getIdAlarma();
        songName = a.getPesma().getNazivPesme();
        perioda = a.getPerioda();
        vremePoslednjegZvona = a.getDatumVremePoslednjegZvona();
    }
    
    @Override
    protected void precalculate() throws InterruptedException{
       
        if (vremePoslednjegZvona == null) vremePoslednjegZvona = vremeZvonaAlarma;
        else vremePoslednjegZvona = new Date(vremePoslednjegZvona.getTime()  + perioda*1000 + (new Date(0)).getTime());
            
        timeToWait = vremePoslednjegZvona.getTime() - (new Date()).getTime();
  
        if (timeToWait <= 0) throw new InterruptedException();
    }

    @Override
    protected void finish() {
        Main.removeAlarmThread(this);
        System.out.println("Send message to uredjaj to play music: " + new Date());
        sendMessageToPlaySong();
        
        PeriodicniAlarm novi = Main.updatePeriodicAlarm(idAlarma);
        PeriodicAlarmThread novaNit = new PeriodicAlarmThread(novi , context , producer);
        Main.addAlarmThread(novaNit);
        novaNit.start();
        
    }
    
}
