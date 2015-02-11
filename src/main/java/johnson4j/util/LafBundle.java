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

    public static String facebookGraph() {

        ResourceBundle rb = ResourceBundle.getBundle("laf");
        return rb.getString("fb.graph.api");

    }
}
