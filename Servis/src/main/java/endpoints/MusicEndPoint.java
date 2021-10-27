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
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author admin
 */
@Path("music")
@Stateless
public class MusicEndPoint {
    
    @PersistenceContext
    EntityManager em;
   
    private static final String conncetionName = "jms/__defaultConnectionFactory";
//    private static final String responseTopicName = "Response" ;
    private static final String zvukTopicName = "IS1ZvukTopic" ;

    @GET
    @Path("play")
    public Response playSong(@QueryParam("songName") String songName ,@Context HttpHeaders headers ) {
        if (songName == null) return Response.status(Response.Status.NO_CONTENT).entity("Niste uneli ime pesme").build();
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicZvuk = (Topic) ic.lookup(zvukTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage msg = context.createTextMessage("play");
            msg.setStringProperty("username", username);
            msg.setStringProperty("songName", songName);
            producer.send(topicZvuk, msg);
            
            return Response.status(Response.Status.OK).entity("Poruka za pustanje pesme je poslata").build();
            
        } catch (Exception ex) {
            Logger.getLogger(MusicEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return Response.status(Response.Status.BAD_REQUEST).entity("Niste napravili poruku kako treba").build();
    }
    
    @GET
    public Response getSongs(@Context HttpHeaders headers) {
        
        try {
            InitialContext ic = new InitialContext(new Properties());
            ConnectionFactory conn = (ConnectionFactory) ic.lookup(conncetionName);
            Topic topicZvuk = (Topic) ic.lookup(zvukTopicName);
            
            List<String> res = headers.getRequestHeaders().get("Authorization");
            String header = res.get(0);
            String decoder = new String(Base64.getDecoder().decode(header.replace("Basic ", "")));
            String[] split = decoder.split(":");  String username = split[0];
            
            JMSContext context = conn.createContext();
            JMSProducer producer = context.createProducer();
            JMSConsumer consumer = context.createConsumer(topicZvuk , "username = '" + username + "'");
          
            TextMessage msg = context.createTextMessage("getSongs");
            msg.setStringProperty("username", username);
            producer.send(topicZvuk, msg);
            
            return Response.status(200).entity("Poruka za listanje pesama je poslata").build();
        } catch (Exception ex) {
            Logger.getLogger(MusicEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return Response.status(Response.Status.OK).entity("Niste pustili aplikaciju u pozadini").build();
    }
}
