/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.exception;

/**
 *
 * @author johnson3yo
 */
public class LafException extends Exception{
    
    private String message;

    public LafException(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

  
    public String getMessage(){
        return this.message;
    }
    
    
    
}
