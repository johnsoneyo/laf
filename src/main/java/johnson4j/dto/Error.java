/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.dto;

/**
 *
 * @author johnson3yo
 */
public class Error {
    
    private String message;
    private int code;

    public Error() {
    }

    public Error(String message, int code) {
        this.message = message;
        this.code = code;
    }
    
    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
    
    
}
