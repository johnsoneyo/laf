/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.util;

import javax.ejb.Stateless;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author johnson3yo
 */


@Stateless
public class TokenGenerator {
    
    public String generateToken(){
    return  "laf_"+RandomStringUtils.randomAlphabetic(16);
    }
}
