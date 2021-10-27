/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entiteti.Korisnik;
import entiteti.NeperiodicniAlarm;
import entiteti.Obaveza;
import entiteti.PeriodicniAlarm;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author admin
 */
@Provider
public class BasicFilter implements ContainerRequestFilter{

    @PersistenceContext()
    EntityManager em;
    
    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        List<String> hed = context.getHeaders().get("Authorization");
        if (hed == null || hed.isEmpty()) {
            Response res = Response.status(Response.Status.PROXY_AUTHENTICATION_REQUIRED).entity("Potrebno korisnicko ime i lozinka").build();
            context.abortWith(res);
            return;
        }
        
        String header = hed.get(0);
        String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
        String[] split = decoder.split(":");  
        String username = split[0];
        String password = null;
        if (split.length > 1) password = split[1];
        
        List<Korisnik> korisnici = em.createQuery("SELECT k FROM Korisnik k WHERE k.korisnickoIme = :username", Korisnik.class).setParameter("username", username).getResultList();
        
        
        if (korisnici.isEmpty() || korisnici.size() > 1 || (!korisnici.get(0).getLozinka().equals(password))) {
            Response res = Response.status(Response.Status.UNAUTHORIZED).entity("Lose korisnicko ime i lozinka").build();
            context.abortWith(res);
            return;
        }
        
        List<PathSegment> segments = context.getUriInfo().getPathSegments();
        String endpoint = segments.get(0).getPath();
        
        if ("music" .equals(endpoint)) return;
        
        String method = context.getMethod();
        if ("alarm".equals(endpoint) && "POST".equals(method)) return;   
        if ("alarm".equals(endpoint) && "PUT".equals(method)) {
            if (segments.size() > 1) {
                try {
                    int idAlarma = Integer.parseInt(segments.get(1).getPath());
                    List<PeriodicniAlarm> periodicni = em.createQuery("SELECT a FROM PeriodicniAlarm a WHERE a.idAlarma = :idAla AND a.korisnik.korisnickoIme = :username   ", PeriodicniAlarm.class).setParameter("idAla", idAlarma).setParameter("username", username).getResultList();
                    List<NeperiodicniAlarm> neperiodicni = em.createQuery("SELECT a FROM NeperiodicniAlarm a WHERE a.idAlarma = :idAla AND a.korisnik.korisnickoIme = :username   ", NeperiodicniAlarm.class).setParameter("idAla", idAlarma).setParameter("username", username).getResultList();  
                    
                    if (periodicni.size() > 0 || neperiodicni.size() > 0) return;
                }
                catch(Exception e) {e.printStackTrace();}
            }
            
            Response res = Response.status(Response.Status.UNAUTHORIZED).entity("Nemate pristup ovom URI").build();
            context.abortWith(res);
            return;
        }
        
        
        if ("planer".equals(endpoint) && segments.size() == 1) return;
        if ("planer".equals(endpoint)) {
  
            try {
                int idOba = Integer.parseInt(segments.get(1).getPath());
                List<Obaveza> obaveze = em.createQuery("SELECT o FROM Obaveza o WHERE o.idObaveze = :idOba AND o.planer.korisnik.korisnickoIme = :username   ", Obaveza.class).setParameter("idOba", idOba).setParameter("username", username).getResultList();
                
                    
                if (obaveze.size() > 0) return;
            }
            catch(Exception e) {e.printStackTrace();}
            
            Response res = Response.status(Response.Status.UNAUTHORIZED).entity("Nemate pristup ovom URI").build();
            context.abortWith(res);
            return;
        }
       
        Response res = Response.status(Response.Status.UNAUTHORIZED).entity("Nemate pristup ovom URI").build();
        context.abortWith(res);
        
    }
    
}
