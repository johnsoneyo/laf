/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.util;

import java.util.ResourceBundle;

/**
 *
 * @author johnson3yo
 */
public class LafBundle {

    private static  ResourceBundle rb = ResourceBundle.getBundle("laf");
    
    public static String facebookGraph() {
 
        return rb.getString("fb.graph.api");

    }

    public static String youTubeV3() {
       
        return rb.getString("ytbe.api");
    }

    public static String getGoogleKey() {
        
      return rb.getString("api.key");
    }

    public static String getChannel() {
    return rb.getString("channel.id");
    }
}
