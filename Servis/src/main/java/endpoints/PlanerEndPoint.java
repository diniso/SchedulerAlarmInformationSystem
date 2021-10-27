/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import sun.applet.Main;

/**
 *
 * @author admin
 */
@Path("planer")
@Stateless
public class PlanerEndPoint {
    
    @PersistenceContext
    EntityManager em;
    
    private static final String conncetionName = "jms/__defaultConnectionFactory";
    private static final String planerTopicName = "IS1PlanerTopic";
    
    @DELETE
    @Path("{idOba}")
    public Response deleteObligation(@PathParam("idOba") int idOba,  @Context HttpHeaders headers) {
        
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicPlaner = (Topic) ic.lookup(planerTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("deleteObligation");
            msg.setStringProperty("username", username);
            msg.setIntProperty("idOba", idOba);
       
            producer.send(topicPlaner , msg);
            
            return Response.status(Response.Status.OK).entity("Poruka za brisanje obaveze poslata").build();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
    
    @PUT
    @Path("{idOba}")
    public Response changeObligation(@PathParam("idOba") int idOba,  @Context HttpHeaders headers, @QueryParam("name") String naziv, 
            @QueryParam("time") String vreme, @QueryParam("duration")Integer trajanje , @QueryParam("idMes")Integer idMes) {
        
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicPlaner = (Topic) ic.lookup(planerTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("changeObligation");
            msg.setStringProperty("username", username);
            msg.setIntProperty("idOba", idOba);
            if (naziv != null) msg.setStringProperty("name", naziv);
            if (trajanje != null) msg.setIntProperty("duration", trajanje);
            if (idMes != null) msg.setIntProperty("idMes", idMes);
            if (vreme != null)msg.setStringProperty("time", vreme);
       
            producer.send(topicPlaner , msg);
            
            return Response.status(Response.Status.OK).entity("Poruka za menjanje obaveze poslata").build();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
    
    @POST
    public Response createObligation(@Context HttpHeaders headers, @QueryParam("name") String naziv, 
            @QueryParam("time") String vreme, @QueryParam("duration")Integer trajanje , @QueryParam("idMes")Integer idMes) {
        
        if (naziv == null || vreme == null || trajanje == null || idMes == null ) return Response.status(Response.Status.BAD_REQUEST).entity("Niste uneli sve parametre za kreiranje obaveze").build();
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicPlaner = (Topic) ic.lookup(planerTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("createObligation");
            msg.setStringProperty("username", username);
            msg.setStringProperty("name", naziv);
            msg.setIntProperty("duration", trajanje);
            msg.setIntProperty("idMes", idMes);
            msg.setStringProperty("time", vreme);
       
            producer.send(topicPlaner , msg);
            
            return Response.status(Response.Status.OK).entity("Poruka za kreiranje obaveze poslata").build();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
    
    
    @POST
    @Path("{idOba}")
    public Response setAlarm(@PathParam("idOba") int idOba,  @Context HttpHeaders headers, @QueryParam("idMes") Integer idMes) {
        if (idMes == null) return Response.status(Response.Status.BAD_REQUEST).entity("Niste uneli sa kojeg mesta krecete").build();
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicPlaner = (Topic) ic.lookup(planerTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("setAlarm");
            msg.setStringProperty("username", username);
            msg.setIntProperty("idOba", idOba);
            msg.setIntProperty("idMes", idMes);
       
            producer.send(topicPlaner , msg);
            
            return Response.status(Response.Status.OK).entity("Poruka za kreiranje podsetnika poslata").build();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
    
    
    @GET
    public Response getObligation(@Context HttpHeaders headers) {
        
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicPlaner = (Topic) ic.lookup(planerTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("getObligations");
            msg.setStringProperty("username", username);
       
            producer.send(topicPlaner , msg);
            
            return Response.status(Response.Status.OK).entity("Poruka za listanje obaveza poslata").build();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
}
