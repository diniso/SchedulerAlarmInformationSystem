/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is1applicationplaner;

import calculator.Calculator;
import entiteti.Korisnik;
import entiteti.Mesto;
import entiteti.NeperiodicniAlarm;
import entiteti.Obaveza;
import entiteti.Pesma;
import entiteti.Planer;
import is1applicationplaner.view.PopUpWindow;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    
    @Resource(lookup="IS1PlanerTopic")
    private static Topic topicPlaner;
    
    @Resource(lookup="IS1AlarmTopic")
    private static Topic topicAlarm;
    
    @Resource(lookup="Response")
    private static Topic response; 
    
    private static final String persistenceUnitName = "IS1.ApplicationPlanerPU" ;
    
    private static EntityManagerFactory emf;
    
    private static final String username = "vlade";
 
    private static boolean checkIsPossibleObligation(int idKor , Date vreme ,int trajanje, int idMes , Integer idOba) {
        EntityManager em = null;
        boolean ret = true;
        try {
            em = emf.createEntityManager();
            Date vremeKraj = new Date(vreme.getTime() + trajanje*1000 + (new Date(0)).getTime());
            List<Obaveza> listaObaveza = em.createQuery("SELECT o FROM Obaveza o WHERE o.planer.korisnik.idKorisnika = :idKor", Obaveza.class).setParameter("idKor", idKor).getResultList();
            for (Obaveza o : listaObaveza) {
                if (idOba != null && ((int)idOba) == o.getIdObaveze()) continue;
                
                long timePocetak = o.getDatumVremePocetka().getTime(), timeKraj = o.getDatumVremeKraja().getTime();
                
                if (timeKraj < vreme.getTime() || timePocetak > vremeKraj.getTime()) continue;
                ret = false;
                throw new Exception();

            }
            
            TypedQuery<Obaveza> q1 = em.createQuery("SELECT o FROM Obaveza o WHERE o.planer.korisnik.idKorisnika = :idKor AND o.datumVremeKraja < :vreme ORDER BY o.datumVremeKraja DESC", Obaveza.class);
            List<Obaveza> listaPre = q1.setParameter("idKor", idKor).setParameter("vreme", vreme).getResultList();
            if (listaPre != null && !listaPre.isEmpty()) {
                long timeRequired = Calculator.getDistanceBetweenPlaces(emf, listaPre.get(0).getMesto().getIdMesta(), idMes) *60000;
                long time = -1;
                if (idOba != null && ((int)idOba) == listaPre.get(0).getIdObaveze()) {
                    if (listaPre.size() > 1) time = vreme.getTime() - listaPre.get(1).getDatumVremeKraja().getTime();
                }
                else time = vreme.getTime() - listaPre.get(0).getDatumVremeKraja().getTime();
                if (time != -1 && time < timeRequired) {
                    ret = false;
                    throw new Exception();
                }
            }
            
             
            TypedQuery<Obaveza> q2 = em.createQuery("SELECT o FROM Obaveza o WHERE o.planer.korisnik.idKorisnika = :idKor AND o.datumVremePocetka > :vreme ORDER BY o.datumVremePocetka", Obaveza.class);
            List<Obaveza> listaPosle = q2.setParameter("idKor", idKor).setParameter("vreme", vremeKraj).getResultList();
            if (listaPosle != null && !listaPosle.isEmpty()) {
                long timeRequired = Calculator.getDistanceBetweenPlaces(emf, listaPosle.get(0).getMesto().getIdMesta(), idMes) *60000;
                long time = -1;
                if (idOba != null && ((int)idOba) == listaPosle.get(0).getIdObaveze()) {
                    if (listaPosle.size() > 1) time = listaPosle.get(1).getDatumVremePocetka().getTime() - vremeKraj.getTime();
                }
                else time = listaPosle.get(0).getDatumVremePocetka().getTime() - vremeKraj.getTime();
                if (time != -1 && time< timeRequired) {
                    ret = false;
                    throw new Exception();
                }
            }
            
            
           
           
        }catch(Exception e){}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        
        return ret;
    }
    
    private static boolean createObligation(Integer idKor , String naziv, Date vreme , Integer trajanje , Integer idMes) {
        EntityManager em = null;
        boolean ret = true;
        try {
            em = emf.createEntityManager();

            if (!checkIsPossibleObligation(idKor, vreme,trajanje, idMes , null)) {
                ret = false;
                throw new Exception();
            }
            
            em.getTransaction().begin();
            
            Obaveza o = new Obaveza();
            
            TypedQuery<Planer> q1 = em.createQuery("SELECT p FROM Planer p WHERE p.korisnik.idKorisnika = :idKor", Planer.class);
            q1.setParameter("idKor", idKor);
            Planer planer = q1.getSingleResult();
            o.setPlaner(planer);
            
            Mesto m = null;
            
            if (idMes == null) {
                TypedQuery<Mesto> q2 = em.createQuery("SELECT k.mesto FROM Korisnik k WHERE k.idKorisnika = :idKor", Mesto.class);
                q2.setParameter("idKor", idKor);
                m = q2.getSingleResult();
            }
            else {
                TypedQuery<Mesto> q3 = em.createQuery("SELECT m FROM Mesto m WHERE m.idMesta = :idMes", Mesto.class);
                q3.setParameter("idMes", idMes);
                m = q3.getSingleResult();
            }
            
            o.setMesto(m);
            o.setNaziv(naziv);
            
            o.setDatumVremePocetka(vreme);
            o.setDatumVremeKraja(new Date(vreme.getTime() + trajanje*1000 + (new Date(0)).getTime()));
            
            em.persist(o);
            
            em.getTransaction().commit();
        }
        catch(Exception e){ret = false;}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return ret;
    }
    
    private static void deleteObligation(int idOba) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            
            Query q = em.createQuery("DELETE FROM Obaveza o WHERE o.idObaveze = :idOba");
            q.setParameter("idOba", idOba);
            
            q.executeUpdate();

            em.getTransaction().commit();
         
        }catch (Exception e) {}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
    }
    
    private static boolean changeObligation(int idOba , String naziv , Date vreme , Integer trajanje , Integer idMes) {
        EntityManager em = null;
        boolean ret = true;
        try {
            em = emf.createEntityManager();
            
            Obaveza obaveza = em.createQuery("SELECT o FROM Obaveza o WHERE o.idObaveze = :idOba" , Obaveza.class).setParameter("idOba", idOba).getSingleResult();
            
            if (vreme == null) vreme = obaveza.getDatumVremePocetka();
            if (naziv == null) naziv = obaveza.getNaziv();
            if (trajanje == null) trajanje = (int)((obaveza.getDatumVremeKraja().getTime() - obaveza.getDatumVremePocetka().getTime()) / 1000);
            if (idMes == null) idMes = obaveza.getMesto().getIdMesta();
            
            if (!checkIsPossibleObligation(obaveza.getPlaner().getKorisnika().getIdKorisnika(),vreme , trajanje , idMes , obaveza.getIdObaveze() )) {
                ret = false;
                throw new Exception();
            }
            
            em.getTransaction().begin();
            
            obaveza.setMesto(em.createQuery("SELECT m FROM Mesto m WHERE m.idMesta = :idMes", Mesto.class).setParameter("idMes", idMes).getSingleResult());
            obaveza.setDatumVremePocetka(vreme);
            obaveza.setDatumVremeKraja(new Date(vreme.getTime() + trajanje*1000 + (new Date(0)).getTime()));
            obaveza.setNaziv(naziv);
            
            em.getTransaction().commit();
            
        }
        catch(Exception e){}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return ret; 
    }
    
    private static List<Obaveza> getObligationsInWeek(int idKor, Date vreme) {
        EntityManager em = null;
        List<Obaveza> ret = new ArrayList<Obaveza>();
        try {
            em = emf.createEntityManager();
            
            Date vremePocetak = new Date(vreme.getYear() , vreme.getMonth() , vreme.getDate(), 0 , 0 , 0);
            Date vremeKraj = new Date(vremePocetak.getTime() + 604800*1000 + (new Date(0)).getTime());
            TypedQuery<Obaveza> q = em.createQuery("SELECT o FROM Obaveza o WHERE o.planer.korisnik.idKorisnika = :idKor AND o.datumVremePocetka >= :pocetak AND o.datumVremeKraja < :kraj ORDER BY o.datumVremePocetka", Obaveza.class);
            q.setParameter("idKor", idKor);
            q.setParameter("pocetak", vremePocetak);
            q.setParameter("kraj", vremeKraj);
            
            ret = q.getResultList();
            
            
        }catch(Exception e) {e.printStackTrace();}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return ret;
    }
    
    private static List<Obaveza> getObligations(int idKor) {
        EntityManager em = null;
        List<Obaveza> ret = new ArrayList<Obaveza>();
        try {
            em = emf.createEntityManager();
            
            TypedQuery<Obaveza> q = em.createQuery("SELECT o FROM Obaveza o WHERE o.planer.korisnik.idKorisnika = :idKor AND (o.datumVremePocetka > :vreme OR :vreme < o.datumVremeKraja ) ORDER BY o.datumVremePocetka", Obaveza.class);
            q.setParameter("idKor", idKor);
            q.setParameter("vreme", new Date());
            
            ret = q.getResultList();
            
            
        }catch(Exception e) {e.printStackTrace();}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return ret;
    }

    private static void createMessageForAlarm(JMSContext context ,JMSProducer producer, String username , String songName, Date vreme , int idOba) throws JMSException {
            TextMessage msg = context.createTextMessage("createAlarm");
            msg.setStringProperty("username", username);
            msg.setStringProperty("songName", songName);
            String datumVreme = "" + (vreme.getYear()+ 1900) + "-" + (vreme.getMonth() + 1) + "-" + vreme.getDate() + " " + vreme.getHours() + ":" + vreme.getMinutes() + ":"  + vreme.getSeconds();
            msg.setStringProperty("alarm0", datumVreme);
            msg.setIntProperty("idOba", idOba);
            producer.send(topicAlarm, msg);
      
    }
    
    private static void parseAndResponde(TextMessage msg, JMSContext context, JMSProducer producer) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            String text = msg.getText();
            String username = msg.getStringProperty("username");
            int idKor = em.createQuery("SELECT k.idKorisnika FROM Korisnik k WHERE k.korisnickoIme = :username", Integer.class).setParameter("username", username).getSingleResult();
            
            if ("createObligation".equals(text)) {
                Date vreme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getStringProperty("time"));
                String naziv = msg.getStringProperty("name");
                int trajanje = msg.getIntProperty("duration");
                int idMes = msg.getIntProperty("idMes");
                
                boolean ret = createObligation(idKor , naziv , vreme , trajanje , idMes);

                if (ret) new PopUpWindow("Uspesno ste kreirali obavezu"); 
                else new PopUpWindow("Obaveza se nije mogla kreirati"); 
                
            }else if ("changeObligation".equals(text)) {
                String naziv = null;
                Integer trajanje = null , idMes = null;
                Date vreme = null;
                int idOba = msg.getIntProperty("idOba");
                if (msg.propertyExists("name")) naziv = msg.getStringProperty("name");
                if (msg.propertyExists("duration")) trajanje = msg.getIntProperty("duration");
                if (msg.propertyExists("idMes")) idMes = msg.getIntProperty("idMes");
                if (msg.propertyExists("time")) vreme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getStringProperty("time"));
                
                boolean ret = changeObligation(idOba, naziv, vreme, trajanje, idMes);
                
                if (ret) new PopUpWindow("Uspesno ste izmenili obavezu"); 
                else new PopUpWindow("Obaveza se nije mogla izmeniti");
                
                
            }else if ("deleteObligation".equals(text)) {
                
                int idOba = msg.getIntProperty("idOba");
                deleteObligation(idOba);
                
                new PopUpWindow("Obaveza obrisana");
                
            }else if ("getObligations".equals(text)) {
                
                List<Obaveza> obaveze = getObligations(idKor);
                
                TextMessage poruka = context.createTextMessage("ObligationsFromUser");
                poruka.setIntProperty("size", obaveze.size());
                poruka.setStringProperty("username", username);
                
                int i = 0;
                
                for (Obaveza o : obaveze) {
                    poruka.setIntProperty("obaveza" + i, o.getIdObaveze());
                    i++;
                }
                
                producer.send(response, poruka); 
                
            }else if ("setAlarm".equals(text)) {
                int idOba = msg.getIntProperty("idOba");
                int idMes = msg.getIntProperty("idMes");
                Obaveza obaveza = em.createQuery("SELECT o FROM Obaveza o WHERE o.idObaveze = :idOba" , Obaveza.class).setParameter("idOba", idOba).getSingleResult();
                long time = Calculator.getDistanceBetweenPlaces(emf, idMes, obaveza.getMesto().getIdMesta())*60000; // in miliseconds
                Date vremeZvona = new Date(obaveza.getDatumVremePocetka().getTime() - time);
                
                createMessageForAlarm(context, producer, username, obaveza.getPlaner().getPesme().getNazivPesme(), vremeZvona , obaveza.getIdObaveze());
                
            }
   
            
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (em != null) em.close();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
            JMSContext context = conn.createContext();
            JMSConsumer consumer = context.createConsumer(topicPlaner , "username = '" + username + "'");
            JMSProducer producer = context.createProducer();
            
            System.out.println("Aplikacija je pocela sa radom");
           
            
            while (true) {
                TextMessage msg = (TextMessage)consumer.receive();
                parseAndResponde(msg , context , producer);
            }
            
            
            
        }
        catch (Exception e) {e.printStackTrace();}
        finally {
            if (emf != null) emf.close() ;
        }
    }

    
    
}
