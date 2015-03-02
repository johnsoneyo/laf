/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author johnson3yo
 */
public class SessionToken implements Serializable {

    public SessionToken(Date startTime, Date endTime,String token) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.token = token;
    }
    
    private Date startTime;
    private Date endTime;
    private String token;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
    
}
