/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.dto;

import com.crowninteractive.anote.NullValidator;

/**
 *
 * @author johnson3yo
 */
public class User {
    
   
    private String first_name;
    private String last_name;
    @NullValidator
    private String email;
    private String dob;
    @NullValidator
    private String password;
    @NullValidator
    private String screen_name;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    
    
    
}
