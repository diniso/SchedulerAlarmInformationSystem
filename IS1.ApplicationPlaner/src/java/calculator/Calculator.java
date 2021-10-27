/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import entiteti.Korisnik;
import entiteti.Mesto;
import entiteti.Obaveza;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin
 */
public class Calculator {
    
    private static double walkSpeed = 0.005;
    private static double driverSpeed = 0.1;
    
    private static Obaveza getPreviousObligation(EntityManagerFactory emf ,int idKor) {
        EntityManager em = null;
        Obaveza ret = null;
        try {
            em = emf.createEntityManager();
            
            TypedQuery<Obaveza> q1 = em.createQuery("SELECT o FROM Obaveza o WHERE o.planer.korisnik.idKorisnika = :idKor AND o.datumVremeKraja < :vreme ORDER BY o.datumVremePocetka DESC", Obaveza.class);
            q1.setParameter("idKor", idKor);
            Date datum = new Date();
            q1.setParameter("vreme", datum);
       
            List<Obaveza> lista = q1.getResultList();
            if (lista != null && !lista.isEmpty()) ret = lista.get(0);
         
        }catch (Exception e) {}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return ret;
    }
    
    private static Obaveza getCurrentObligation(EntityManagerFactory emf ,int idKor) {
        EntityManager em = null;
        Obaveza ret = null;
        try {
            em = emf.createEntityManager();
            
            TypedQuery<Obaveza> q1 = em.createQuery("SELECT o FROM Obaveza o WHERE o.planer.korisnik.idKorisnika = :idKor AND o.datumVremePocetka <= :vreme AND o.datumVremeKraja > :vreme", Obaveza.class);
            q1.setParameter("idKor", idKor);
            Date datum = new Date();
            q1.setParameter("vreme", datum);
       
            ret = q1.getSingleResult();
         
        }catch (Exception e) {}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return ret;
    }
    
    public static long getDistanceBetweenPlaces(EntityManagerFactory emf ,int idMes1 , int idMes2) {
        EntityManager em = null;
        long rez = -1;
        try {
            em = emf.createEntityManager();
            Mesto m1 = em.createQuery("SELECT m FROM Mesto m WHERE m.idMesta = :idMes", Mesto.class).setParameter("idMes", idMes1).getSingleResult();
            if (m1 == null) throw new Exception();
            Mesto m2 = em.createQuery("SELECT m FROM Mesto m WHERE m.idMesta = :idMes", Mesto.class).setParameter("idMes", idMes2).getSingleResult();
            if (m2 == null) throw new Exception();
           
            rez = Math.round(Math.sqrt(Math.pow(m1.getX() - m2.getX() , 2) + Math.pow(m1.getY() - m2.getY() , 2)) / driverSpeed);
            
        }catch (Exception e){e.printStackTrace();}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return rez;
    }
    
    public static long getDistance(EntityManagerFactory emf ,int idKor , int idMes) {
        EntityManager em = null;
        long rez = -1;
        try {
            em = emf.createEntityManager();
            Korisnik k = em.createQuery("SELECT k FROM Korisnik k WHERE k.idKorisnika = :idKor", Korisnik.class).setParameter("idKor", idKor).getSingleResult();
            if (k == null) throw new Exception();
            Mesto m2 = em.createQuery("SELECT m FROM Mesto m WHERE m.idMesta = :idMes", Mesto.class).setParameter("idMes", idMes).getSingleResult();
            if (m2 == null) throw new Exception();
            
            Mesto m1 = null;
            Obaveza obaveza = getCurrentObligation(emf, idKor);
            if (obaveza != null) m1 = obaveza.getMesto();
            else  {
                obaveza = getPreviousObligation(emf, idKor);
                if (obaveza != null) m1 = obaveza.getMesto();
                else m1 = k.getIdMesta();
            }
           
            rez = Math.round(Math.sqrt(Math.pow(m1.getX() - m2.getX() , 2) + Math.pow(m1.getY() - m2.getY() , 2)) / driverSpeed);
            
        }catch (Exception e){e.printStackTrace();}
        finally {
            if (em != null) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
            }
        }
        
        return rez;
    }
}
