/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1userapplication;

import entiteti.Korisnik;
import entiteti.Mesto;
import entiteti.Obaveza;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import view.LogInFrame;
import view.MainFrame;
import view.SelectObligationDialog;

/**
 *
 * @author admin
 */
public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory conn;
    
    @Resource(lookup="Response")
    private static Topic response; 
    
    private static final String persistenceUnitName = "IS1.UserApplicationPU" ;
    
    private static EntityManagerFactory emf;
    
    private static EntityManager em;
    
    private static JMSContext context;
    private static JMSConsumer consumer;
    private static MainFrame frame;
    
    public static int getHomeIdFromUser(String username) {
        int ret = -1;
        try {
            ret = em.createQuery("SELECT k.idMesta.idMesta FROM Korisnik k WHERE k.korisnickoIme=:username", Integer.class).setParameter("username", username).getSingleResult();
        }
        catch (Exception e) {e.printStackTrace();}
        return ret;
    }
    
    public static void initJMSMainFrame(String username , String password) {
        context = conn.createContext();
        consumer = context.createConsumer(response , "username = '" + username + "'");
        frame = new MainFrame(username , password);
        new Thread() {
            @Override
            public void run() {
                EntityManager em2 = null;
                try {
                        
                        while (true) {
                            TextMessage msg = (TextMessage)consumer.receive();
                            em2 = emf.createEntityManager();
                            
                            try {
                               if (!("ObligationsFromUser".equals(msg.getText()))) continue;

                                int size = msg.getIntProperty("size");
                                List<Obaveza> lista = new ArrayList<Obaveza>();

                                for (int i = 0 ; i < size ; i++) {
                                   int idOba = msg.getIntProperty("obaveza" + i);
                                   Obaveza o = em2.createQuery("SELECT o FROM Obaveza o WHERE o.idObaveze = :idOba", Obaveza.class).setParameter("idOba", idOba).getSingleResult();
                                   em2.refresh(o);
                                   lista.add(o);
                                } 

                                new SelectObligationDialog(frame , lista , frame.getUsername() , frame.getPassword());
                            }
                            catch (Exception e) {e.printStackTrace();}
                            finally {
                                if (em2 != null) {
                                //    em2.clear();
                                    em2.close();  
                                }
                            }


                        }
                }
                catch (Exception e) {e.printStackTrace();}
                finally {
                    if (em2 != null) em2.close();
                    if (em != null) em.close();
                    if (emf != null) emf.close();
                }
                        
            }

        }.start();
    }
    
    public static boolean checkLogIn(String username , String password) {
        boolean ret = false;
        try {
            List<Korisnik> korisnici = em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnickoIme=:username AND k.lozinka=:password", Korisnik.class).setParameter("username", username).setParameter("password", password).getResultList();
            if (!korisnici.isEmpty()) ret = true;
        }
        catch (Exception e) {e.printStackTrace();}
         
        return ret;
    }
    
    public static int getIdMestoFromName(String mesto) {
        String[] split = mesto.split(", ");
        int ret = -1;
        try {
            ret = em.createQuery("SELECT m.idMesta FROM Mesto m WHERE m.nazivGrada = :grad AND m.adresa = :adresa" , Integer.class).setParameter("grad", split[0]).setParameter("adresa", split[1]).getSingleResult();
        }
        catch (Exception e) {e.printStackTrace();}
        return ret;
    }
    
    public static String[] getAllMesta() {
        List<Mesto> mesta = em.createQuery("SELECT m FROM Mesto m", Mesto.class).getResultList();
        String[] ret = new String[mesta.size()];
        int i = 0;
        for (Mesto m : mesta) {
            ret[i++] = m.getNazivGrada() + ", " + m.getAdresa();
        }
        
        return ret;
    }
    
    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        em = emf.createEntityManager();
        new LogInFrame();
        
    }
    
}
