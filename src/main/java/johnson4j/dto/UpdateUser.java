/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.dto;

/**
 *
 * @author johnson3yo
 */
public class UpdateUser extends User{
    
    private String  id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDob(String dob) {
        super.setDob(dob); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setScreen_name(String screen_name) {
        super.setScreen_name(screen_name); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPhone(String phone) {
        super.setPhone(phone); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFirst_name(String first_name) {
        super.setFirst_name(first_name); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLast_name(String last_name) {
        super.setLast_name(last_name); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEmail() {
        return super.getEmail(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPassword() {
        return super.getPassword(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDob() {
        return super.getDob(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getScreen_name() {
        return super.getScreen_name(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPhone() {
        return super.getPhone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getFirst_name() {
        return super.getFirst_name(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getLast_name() {
        return super.getLast_name(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
