/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1alarmapplication;

import alarm.threads.AlarmThread;
import alarm.threads.NonPeriodicAlarmThread;
import alarm.threads.PeriodicAlarmThread;
import entiteti.Alarm;
import entiteti.Korisnik;
import entiteti.NeperiodicniAlarm;
import entiteti.Obaveza;
import entiteti.PeriodicniAlarm;
import entiteti.Pesma;
import is1alarmapplication.view.PopUpWindow;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory conn;
    
    @Resource(lookup="IS1AlarmTopic")
    private static Topic topicAlarm;
    
    @Resource(lookup="IS1ZvukTopic")
    private static Topic topicZvuk;
    
    private static final String persistenceUnitName = "IS1.AlarmApplicationPU" ;
    
    private static EntityManagerFactory emf;
    
    private static final String username = "vlade";
  
    private static NeperiodicniAlarm createAlarm(Integer idKor , Date vreme ,Integer idPes) {
        EntityManager em = null ;
        NeperiodicniAlarm alarm = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            alarm = new NeperiodicniAlarm();
            
            TypedQuery<Korisnik> q1 = em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika = :idKor", Korisnik.class);
            q1.setParameter("idKor", idKor);
            
            List<Korisnik> korisnici = q1.getResultList();
            if (korisnici.isEmpty()) throw new Exception();
            
            alarm.setKorisnik(korisnici.get(0));
            
            alarm.setDatumVreme(vreme);
            
            TypedQuery<Pesma> q2 = em.createQuery("SELECT p FROM Pesma p WHERE p.idPesme = :idPes", Pesma.class);
            q2.setParameter("idPes", idPes);
            
            List<Pesma> pesme = q2.getResultList();
            if (pesme.isEmpty()) throw new Exception();
            
            alarm.setPesma(pesme.get(0));

            em.persist(alarm);
           
            em.getTransaction().commit();
            
            
            em.getTransaction().begin();
            
            em.refresh(alarm);
            
            em.getTransaction().commit();
            
        }    
        catch(Exception e) {}
        finally {
                if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }       
            
        }
        return alarm;
    }

    private static PeriodicniAlarm createPeriodicAlarm(Integer idKor , Date vreme , int perioda , Integer idPes) {
        EntityManager em = null ;
        PeriodicniAlarm alarm = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            alarm = new PeriodicniAlarm();
            
            TypedQuery<Korisnik> q1 = em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika = :idKor", Korisnik.class);
            q1.setParameter("idKor", idKor);
            
            List<Korisnik> korisnici = q1.getResultList();
            if (korisnici.isEmpty()) throw new Exception();
            
            alarm.setKorisnik(korisnici.get(0));
            
            alarm.setDatumVreme(vreme);
            alarm.setPerioda(perioda);
            
            TypedQuery<Pesma> q2 = em.createQuery("SELECT p FROM Pesma p WHERE p.idPesme = :idPes", Pesma.class);
            q2.setParameter("idPes", idPes);
            
            List<Pesma> pesme = q2.getResultList();
            if (pesme.isEmpty()) throw new Exception();
            
            alarm.setPesma(pesme.get(0));
                 
            em.persist(alarm);
           
            em.getTransaction().commit();
            
            
            em.getTransaction().begin();
            
            em.refresh(alarm);
            
            em.getTransaction().commit();
            
        }    
        catch(Exception e) {}
        finally {
                if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }       
            
        }
        
        return alarm;
    }
    
    private static NeperiodicniAlarm createAlarmFromList(Integer idKor , List<Date> vremena ,Integer idPes) {
        EntityManager em = null ;
        NeperiodicniAlarm alarm = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            alarm = new NeperiodicniAlarm();
            
            TypedQuery<Korisnik> q1 = em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika = :idKor", Korisnik.class);
            q1.setParameter("idKor", idKor);
            
            List<Korisnik> korisnici = q1.getResultList();
            if (korisnici.isEmpty()) throw new Exception();
            
            alarm.setKorisnik(korisnici.get(0));
            
            Random rd = new Random();
            
            alarm.setDatumVreme(vremena.get(rd.nextInt(vremena.size())));
            
            TypedQuery<Pesma> q2 = em.createQuery("SELECT p FROM Pesma p WHERE p.idPesme = :idPes", Pesma.class);
            q2.setParameter("idPes", idPes);
            
            List<Pesma> pesme = q2.getResultList();
            if (pesme.isEmpty()) throw new Exception();
            
            alarm.setPesma(pesme.get(0));
                  
            em.persist(alarm);
           
            em.getTransaction().commit();
            
            
            em.getTransaction().begin();
            
            em.refresh(alarm);
            
            em.getTransaction().commit();
            
        }    
        catch(Exception e) {}
        finally {
                if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }       
            
        }
        
        return alarm;
    }
    
    private static boolean changeAlarmSong(Integer IdAla , Integer idPes) {
        EntityManager em = null;
        boolean ret = true;
        try {
            em = emf.createEntityManager();
            
            TypedQuery<Pesma> q1 = em.createQuery("SELECT p FROM Pesma p WHERE p.idPesme = :idPes", Pesma.class);
            q1.setParameter("idPes", idPes);
            Pesma pesma = q1.getSingleResult();
            
            Query q2 = em.createQuery("UPDATE Alarm a SET a.pesma = :pesma WHERE a.idAlarma = :idAla");
            q2.setParameter("pesma", pesma);
            q2.setParameter("idAla", IdAla);

            em.getTransaction().begin();
            
            q2.executeUpdate();
            
            em.getTransaction().commit();
        }
        catch(Exception e) {ret = false;}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return ret;
    }
    
    public static PeriodicniAlarm updatePeriodicAlarm(Integer IdAla) {
        EntityManager em = null;
        PeriodicniAlarm p = null;
        try {
            em = emf.createEntityManager();
            
            TypedQuery<Alarm> q1 = em.createQuery("SELECT a FROM Alarm a WHERE a.idAlarma = :idAla ", Alarm.class);
            q1.setParameter("idAla", IdAla);
            Alarm alarm = q1.getSingleResult();
            
            if (!(alarm instanceof PeriodicniAlarm)) throw new Exception();
            
            p = (PeriodicniAlarm) alarm;
            
            int perioda = p.getPerioda();
            Date poslednjeZvono = p.getDatumVremePoslednjegZvona();
            
            if (poslednjeZvono == null) poslednjeZvono = p.getDatumVreme();
            else poslednjeZvono = new Date(poslednjeZvono.getTime()  + perioda*1000 + (new Date(0)).getTime());
            
            
            Query q = em.createQuery("UPDATE PeriodicniAlarm a SET a.datumVremePoslednjegZvona = :vreme WHERE a.idAlarma = :idAla");
            q.setParameter("idAla", IdAla);
            q.setParameter("vreme", poslednjeZvono);
            
            em.getTransaction().begin();
            
            q.executeUpdate();
            
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            
            em.refresh(p);
            
            em.getTransaction().commit();
                
        }
        catch(Exception e) {}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return p;
    }
    
    private static List<AlarmThread> activeAlarms = new ArrayList<>();
    
    public synchronized static void removeAlarmThread(AlarmThread alarm) {
        activeAlarms.remove(alarm);
    }
    
    public synchronized static void addAlarmThread(AlarmThread alarm) {
        activeAlarms.add(alarm);
    }
    
    public static void createMessageToPlaySongs(JMSContext context ,JMSProducer producer, String username , String songName) {
        try {
            TextMessage msg = context.createTextMessage("play");
            msg.setStringProperty("username", username);
            msg.setStringProperty("songName", songName);
            producer.send(topicZvuk, msg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void startAlarm(String username, JMSContext context, JMSProducer producer) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            
            Korisnik korisnik = em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnickoIme = :username" , Korisnik.class).setParameter("username", username).getSingleResult();
            
            List<NeperiodicniAlarm> alarmi = em.createQuery("SELECT a FROM NeperiodicniAlarm a WHERE a.datumVreme > :vreme AND a.korisnik = :korisnik", NeperiodicniAlarm.class).setParameter("vreme", new Date()).setParameter("korisnik", korisnik).getResultList();
            for (NeperiodicniAlarm alarm : alarmi) {
                NonPeriodicAlarmThread nit = new NonPeriodicAlarmThread(alarm , context , producer);
                addAlarmThread(nit);
                nit.start();
            }
            //dodati da se periodicni alarmi pokrenu
            
        }
        catch(Exception e) {}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
    }
    
    private static void parseAndResponse(TextMessage msg, JMSContext context, JMSProducer producer) {
        EntityManager em = null;
        try {
            String text = msg.getText();
            em = emf.createEntityManager();
            String username = msg.getStringProperty("username");
            int idKor = em.createQuery("SELECT k.idKorisnika FROM Korisnik k WHERE k.korisnickoIme = :username", Integer.class).setParameter("username", username).getSingleResult();
            String songName = msg.getStringProperty("songName");
            int idPes = em.createQuery("SELECT p.idPesme FROM Pesma p WHERE p.nazivPesme = :pesma", Integer.class).setParameter("pesma", songName).getSingleResult();
            
            if ("createAlarm".equals(text)) {
                
                Date vreme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getStringProperty("alarm0"));
                NeperiodicniAlarm alarm = createAlarm(idKor , vreme , idPes);
                if (msg.propertyExists("idOba")) {
                    try {
                        int idOba = msg.getIntProperty("idOba");
                        Obaveza obaveza = em.createQuery("SELECT o FROM Obaveza o WHERE o.idObaveze = :idOba" , Obaveza.class).setParameter("idOba", idOba).getSingleResult();
                    
                        em.getTransaction().begin();
                    
                        obaveza.setAlarma(alarm);
                    
                        em.getTransaction().commit(); 
                    }catch (Exception e) {e.printStackTrace();}
                    
                }
                NonPeriodicAlarmThread nit = new NonPeriodicAlarmThread(alarm , context , producer);
                addAlarmThread(nit);
                nit.start();
                new PopUpWindow("Alarm created");
                
            }else if ("createPeriodicAlarm".equals(text)) {
                Date vreme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getStringProperty("alarm0"));
                int perioda = msg.getIntProperty("perioda");
                PeriodicniAlarm alarm = createPeriodicAlarm(idKor , vreme ,perioda, idPes);
                PeriodicAlarmThread nit = new PeriodicAlarmThread(alarm , context , producer);
                addAlarmThread(nit);
                nit.start();
                new PopUpWindow("Periodic alarm created");
                
            }else if ("createFromAlarms".equals(text)) {
                List<Date> vremena = new ArrayList<Date>();
                int size = msg.getIntProperty("size");
                for (int i = 0 ; i < size ; i++) {
                   vremena.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getStringProperty("alarm" + i)));
                }
                
                NeperiodicniAlarm alarm = createAlarmFromList(idKor, vremena, idPes);
                NonPeriodicAlarmThread nit = new NonPeriodicAlarmThread(alarm , context , producer);
                addAlarmThread(nit);
                nit.start();
                
                new PopUpWindow("Alarm from list created");
 
            }else if ("changeSong".equals(text)) {
                int idAla = msg.getIntProperty("idAlarma");
                for (AlarmThread t : activeAlarms) {
                    if (t.getIdAlarma() == idAla) {
                        t.setSongName(songName);
                        break;
                    }
                }
                
                changeAlarmSong(idAla, idPes);
                new PopUpWindow("Alarm songs changed");
            }
            
    
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex); 
            new PopUpWindow("Error!!! Operation failed.");
        }
        finally {
            if (em != null) em.close();
        }
    }
    
    
    
    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
            JMSContext context = conn.createContext();
            JMSConsumer consumer = context.createConsumer(topicAlarm , "username = '" + username + "'");
            JMSProducer producer = context.createProducer();
            
            System.out.println("Aplikacija pocela sa radom");
            
            while (true) {
                TextMessage msg = (TextMessage) consumer.receive();
                parseAndResponse(msg, context, producer);
            } 
            
            
        }
        catch (Exception e) {e.printStackTrace();}
        finally {
            if (emf != null) emf.close();
            for (AlarmThread a : activeAlarms)
                a.interrupt();
        }
    }

    
    
}
