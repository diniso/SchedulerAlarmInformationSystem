/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author admin
 */
@Path("alarm")
@Stateless
public class AlarmEndPoint {
    
    @PersistenceContext
    EntityManager em;
    
    private static final String conncetionName = "jms/__defaultConnectionFactory";
    private static final String alarmTopicName = "IS1AlarmTopic";
    
    @POST
    public Response createAlarm(@QueryParam("time") String vreme , @Context HttpHeaders headers) {
        if (vreme == null) return Response.status(Response.Status.BAD_REQUEST).entity("Posaljite vreme alarma").build();
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicAlarm = (Topic) ic.lookup(alarmTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            String songName = em.createQuery("SELECT p.pesma.nazivPesme FROM Planer p WHERE p.korisnik.korisnickoIme = :username" , String.class).setParameter("username", username).getSingleResult();
            TextMessage msg = context.createTextMessage("createAlarm");
            msg.setStringProperty("username", username);
            msg.setStringProperty("songName", songName);
            msg.setStringProperty("alarm0", vreme);
            producer.send(topicAlarm, msg);
            
            return Response.status(200).entity("Poruka za kreiranje alarma poslata").build();
            
            
        }
        catch(Exception e) {e.printStackTrace();}
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
    
    @POST
    @Path("periodic")
    public Response createPeriodicAlarm(@QueryParam("time") String vreme , @QueryParam("perioda") Integer periodaInSeconds ,  @Context HttpHeaders headers) {
        if (vreme == null || periodaInSeconds == null) return Response.status(Response.Status.BAD_REQUEST).entity("Posaljite vreme alarma i periodu").build();
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicAlarm = (Topic) ic.lookup(alarmTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            String songName = em.createQuery("SELECT p.pesma.nazivPesme FROM Planer p WHERE p.korisnik.korisnickoIme = :username" , String.class).setParameter("username", username).getSingleResult();
            TextMessage msg = context.createTextMessage("createPeriodicAlarm");
            msg.setStringProperty("username", username);
            msg.setStringProperty("songName", songName);
            msg.setStringProperty("alarm0", vreme);
            msg.setIntProperty("perioda", periodaInSeconds);
            producer.send(topicAlarm, msg);
            
            return Response.status(200).entity("Poruka za kreiranje periodicnog alarma poslata").build();
            
            
        }
        catch(Exception e) {e.printStackTrace();}
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
    
    @Path("{idAla}")
    @PUT
    public Response changeAlarmSong(@PathParam("idAla") int idAla , @QueryParam("songName") String songName ,  @Context HttpHeaders headers) {
        if (songName == null) return Response.status(Response.Status.BAD_REQUEST).entity("Posaljite naziv nove pesme za alarm").build();
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicAlarm = (Topic) ic.lookup(alarmTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("changeSong");
            msg.setStringProperty("username", username);
            msg.setStringProperty("songName", songName);
            msg.setIntProperty("idAlarma", idAla);
            producer.send(topicAlarm, msg);
            
            return Response.status(200).entity("Poruka za menjanje pesme alarma poslata").build();
            
            
        }
        catch(Exception e) {e.printStackTrace();}
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
    
    // promeniti 
    @POST
    @Path("random")
    public Response createAlarmFromList(@QueryParam("time") String vr , @QueryParam("perioda") Integer periodaInSeconds ,
            @QueryParam("number") Integer brojPonavljanja, @Context HttpHeaders headers) {
        if (vr == null || periodaInSeconds == null || brojPonavljanja == null) return Response.status(Response.Status.BAD_REQUEST).entity("Niste poslali sve parametre").build();
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicAlarm = (Topic) ic.lookup(alarmTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            String songName = em.createQuery("SELECT p.pesma.nazivPesme FROM Planer p WHERE p.korisnik.korisnickoIme = :username" , String.class).setParameter("username", username).getSingleResult();
            TextMessage msg = context.createTextMessage("createFromAlarms");
            msg.setStringProperty("username", username);
            msg.setStringProperty("songName", songName);
            msg.setIntProperty("size", brojPonavljanja);
            
            Date vreme = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(vr);
            
            for (int i = 0 ; i < brojPonavljanja; i++) {
                String datumVreme = "" + (vreme.getYear()+ 1900) + "-" + (vreme.getMonth() + 1) + "-" + vreme.getDate() + " " + vreme.getHours() + ":" + vreme.getMinutes() + ":"  + vreme.getSeconds();
                msg.setStringProperty("alarm" + i, datumVreme);
                vreme.setTime(vreme.getTime() + periodaInSeconds*1000);
            }
            
            
            
            producer.send(topicAlarm, msg);
            
            return Response.status(200).entity("Poruka za kreiranje alarma od liste poslata").build();
            
            
        }
        catch(Exception e) {e.printStackTrace();}
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
}
