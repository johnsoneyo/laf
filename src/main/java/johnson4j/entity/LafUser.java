/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "laf_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LafUser.findAll", query = "SELECT l FROM LafUser l"),
    @NamedQuery(name = "LafUser.findByLafId", query = "SELECT l FROM LafUser l WHERE l.lafId = :lafId"),
    @NamedQuery(name = "LafUser.findByEmail", query = "SELECT l FROM LafUser l WHERE l.email = :email"),
    @NamedQuery(name = "LafUser.findByPassword", query = "SELECT l FROM LafUser l WHERE l.password = :password"),
    @NamedQuery(name = "LafUser.findByDob", query = "SELECT l FROM LafUser l WHERE l.dob = :dob"),
    @NamedQuery(name = "LafUser.findByDateCreated", query = "SELECT l FROM LafUser l WHERE l.dateCreated = :dateCreated"),
    @NamedQuery(name = "LafUser.findByDateModified", query = "SELECT l FROM LafUser l WHERE l.dateModified = :dateModified")})
public class LafUser implements Serializable {
    @Size(max = 32)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 32)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 32)
    @Column(name = "screen_name")
    private String screenName;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 32)
    @Column(name = "phone")
    private String phone;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name = "laf_id")
    private Integer lafId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 64)
    @Column(name = "email")
    private String email;
    @Size(max = 64)
    @Column(name = "password")
    private String password;
    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date dob;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @JoinColumn(name = "laf_id", referencedColumnName = "laf_id", insertable = false, updatable = false)
    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "lafUser")
    private LafUserMedia lafUserMedia;

    public LafUser() {
    }

    public LafUser(Integer lafId) {
        this.lafId = lafId;
    }

    public LafUser(Integer lafId, Date dateCreated, Date dateModified) {
        this.lafId = lafId;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public Integer getLafId() {
        return lafId;
    }

    public void setLafId(Integer lafId) {
        this.lafId = lafId;
    }

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    @XmlTransient
    public LafUserMedia getLafUserMedia() {
        return lafUserMedia;
    }

    public void setLafUserMedia(LafUserMedia lafUserMedia) {
        this.lafUserMedia = lafUserMedia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lafId != null ? lafId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LafUser)) {
            return false;
        }
        LafUser other = (LafUser) object;
        if ((this.lafId == null && other.lafId != null) || (this.lafId != null && !this.lafId.equals(other.lafId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "johnson4j.entity.LafUser[ lafId=" + lafId + " ]";
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}
