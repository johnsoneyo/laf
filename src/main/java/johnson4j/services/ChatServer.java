/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author johnson3yo
 *  laf instant chat 
 */
@ServerEndpoint(value = "/chat_serv/{room}/{screen_name}")
public class ChatServer {

    private final Logger log = Logger.getLogger(getClass().getName());

    public ChatServer() {
    }
 
    @OnOpen
    public void registerSession(@PathParam("room") final String room, final Session session, @PathParam("screen_name") String screen_name) throws IOException {

        session.getUserProperties().put("screen_name", screen_name);
        session.getUserProperties().put("room", room);
        session.getUserProperties().put("time_joined", new Date());

    }

   
    @OnMessage
    public void receiveMsg(final Session s,final String chatObject) {
     
        JsonElement dataElem = new JsonParser().parse(chatObject);
        String recipient = dataElem.getAsJsonObject().get("recipient").getAsString();
        String sender = dataElem.getAsJsonObject().get("sender").getAsString();
       
        
        try {
            for (Session se : s.getOpenSessions()) {
                 if (se.isOpen()) {
               
                     if(se.getUserProperties().get("screen_name").equals(sender) || se.getUserProperties().get("screen_name").equals(recipient))
                    se.getBasicRemote().sendText(chatObject);
                       
                }
 
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
}
