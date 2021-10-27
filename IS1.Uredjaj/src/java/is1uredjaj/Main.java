/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1uredjaj;

import entiteti.Korisnik;
import entiteti.Pesma;
import entiteti.Pustena;
import is1uredjaj.view.SongsShowFrame;
import java.awt.Desktop;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
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
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import view.PopUpWindow;

/**
 *
 * @author admin
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory conn;
    
    @Resource(lookup="IS1ZvukTopic")
    private static Topic topicZvuk;
    
    private static final String persistenceUnitName = "IS1.UredjajPU" ;
    
    private static EntityManagerFactory emf;
    
    
    /**
     * @param args the command line arguments
     */
    
    private static List<String> getSongsFromUser(String username) {
        EntityManager em = null;
        List<String> rez = null;
        try {
            em = emf.createEntityManager();
             TypedQuery<String> q1 = em.createQuery("SELECT p.nazivPesme FROM Pesma p, Korisnik k, Pustena pu "
            + "WHERE pu MEMBER OF k.pustenaList AND pu MEMBER OF p.pustenaList AND k.korisnickoIme = :name" , String.class); 
            q1.setParameter("name", username);
            rez = q1.getResultList();
        }
        catch (Exception e) {}
        finally {
            if (em != null) em.close();
        }
        
        return rez;
    }
    
    private static String getURLFromSong(String songName) {
        EntityManager em = null;
        String rez = null;
        songName = songName.toLowerCase();
        try {
            em = emf.createEntityManager();
            TypedQuery<String> q1 = em.createQuery("SELECT p.uRLLokacija FROM Pesma p WHERE p.nazivPesme = :nazivPesme", String.class );
                    
            q1.setParameter("nazivPesme", songName);
            List<String> lista = q1.getResultList();
            
            if (lista.size() > 0)   rez = lista.get(0);
        }
        catch (Exception e) {}
        finally {
            if (em != null) em.close();
        }
        
        return rez;
    }
 /*   
    private static void insertSong(String songName , String URL) {
        EntityManager em = null;
        songName = songName.toLowerCase();
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            
            Pesma p = new Pesma();
            p.setNazivPesme(songName);
            p.setURLLokacija(URL);
            
            em.persist(p);
            
            em.getTransaction().commit();
            
        }
        catch(Exception e){}
        finally {
           if (em != null) {
                if (em.getTransaction().isActive())em.getTransaction().rollback();
                em.close();
            }
        }
    } */
    
    private static boolean isSongPlayed(String songName , String username) {
        EntityManager em = null;
        long ret=0;
        try {
            em = emf.createEntityManager();
            TypedQuery<Long> q1 = em.createQuery("SELECT COUNT(pu) FROM Pesma p, Korisnik k, Pustena pu "
                    + "WHERE pu MEMBER OF k.pustenaList AND pu MEMBER OF p.pustenaList AND k.korisnickoIme = :name AND p.nazivPesme = :pesma"  , Long.class); 
            q1.setParameter("name", username);
            q1.setParameter("pesma", songName);
            ret = q1.getSingleResult();
            
        }
        catch (Exception e) {}
        finally {
            if (em != null) em.close();
        }
        
        if (ret == 0) return false;
        
        return true;
    }
    
    private static void insertIntoPustena(String songName , String username) {
        EntityManager em = null;
        songName = songName.toLowerCase();
        try {
            em = emf.createEntityManager();
            Korisnik korisnik = em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnickoIme = :username", Korisnik.class).setParameter("username", username).getSingleResult();
            Pesma pesma = em.createQuery("SELECT p FROM Pesma p WHERE p.nazivPesme = :pesma", Pesma.class).setParameter("pesma", songName).getSingleResult();
            
            em.getTransaction().begin();
            Pustena pu = new Pustena();
            pu.setIdKorisnika(korisnik);
            pu.setIdPesme(pesma);
            
            em.persist(pu);
            em.getTransaction().commit();
            
        }
        catch(Exception e){e.printStackTrace();}
        finally {
           if (em != null) {
               if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            } 
        }
    }
    
    private static void playSong(String URL) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(URL));
            }
        }
        catch (Exception e) {}
        
    }
    
    private static void parseAndResponse(TextMessage msg ,JMSContext context,  JMSProducer producer) {
        try {
            String text = msg.getText();
            String username = msg.getStringProperty("username");
            
            if ("play".equals(text)) {
                String song = msg.getStringProperty("songName");
                String URL = getURLFromSong(song);
                if (URL == null)  {             
                    new PopUpWindow("Ne moze se naci pesma.");
                    return;
                }
                
                if (!isSongPlayed(song, username)) insertIntoPustena(song, username);
                playSong(URL);
                
                
            }else if ("getSongs".equals(text)) {
                List<String> songs = getSongsFromUser(username);
                new SongsShowFrame(songs);
            }
            
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static final String username = "vlade";
    
    public static void main(String[] args) {
        
        try {
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
            JMSContext context = conn.createContext();
            JMSConsumer consumer = context.createConsumer(topicZvuk , "username = '" + username + "'");
            JMSProducer producer = context.createProducer();
            
            System.out.println("Aplikacija pocela da radi");

            while (true) {

                TextMessage msg = (TextMessage) consumer.receive();
                parseAndResponse(msg, context, producer);

            } 
            
            
        }
        catch (Exception e) {e.printStackTrace();}
        finally {
            if (emf != null) emf.close();
        }
            
        
        
    }
    
}
