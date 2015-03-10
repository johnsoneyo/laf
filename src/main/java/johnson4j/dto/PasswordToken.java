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
public class PasswordToken implements Serializable{
    
    private int laf_id;
    private Date created_time;
    private Date expiry_time;

    public PasswordToken(int laf_id, Date created_time, Date expiry_time) {
        this.laf_id = laf_id;
        this.created_time = created_time;
        this.expiry_time = expiry_time;
    }

    public int getLaf_id() {
        return laf_id;
    }

    public void setLaf_id(int laf_id) {
        this.laf_id = laf_id;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getExpiry_time() {
        return expiry_time;
    }

    public void setExpiry_time(Date expiry_time) {
        this.expiry_time = expiry_time;
    }
    
    
    
    
}
