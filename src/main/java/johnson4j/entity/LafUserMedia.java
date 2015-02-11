/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.entity;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author johnson3yo
 */
@Entity
@Table(name = "laf_user_media")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LafUserMedia.findAll", query = "SELECT l FROM LafUserMedia l"),
    @NamedQuery(name = "LafUserMedia.findByLafId", query = "SELECT l FROM LafUserMedia l WHERE l.lafId = :lafId"),
    @NamedQuery(name = "LafUserMedia.findByFacebookId", query = "SELECT l FROM LafUserMedia l WHERE l.facebookId = :facebookId"),
    @NamedQuery(name = "LafUserMedia.findByLinkedin", query = "SELECT l FROM LafUserMedia l WHERE l.linkedin = :linkedin"),
    @NamedQuery(name = "LafUserMedia.findByTwitter", query = "SELECT l FROM LafUserMedia l WHERE l.twitter = :twitter"),
    @NamedQuery(name = "LafUserMedia.findByFoursquare", query = "SELECT l FROM LafUserMedia l WHERE l.foursquare = :foursquare")})
public class LafUserMedia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name = "laf_id")
    private Integer lafId;
    @Size(max = 64)
    @Column(name = "facebook_id")
    private String facebookId;
    @Size(max = 64)
    @Column(name = "linkedin")
    private String linkedin;
    @Size(max = 64)
    @Column(name = "twitter")
    private String twitter;
    @Size(max = 64)
    @Column(name = "foursquare")
    private String foursquare;
    @JoinColumn(name = "laf_id", referencedColumnName = "laf_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private LafUser lafUser;

    public LafUserMedia() {
    }

    public LafUserMedia(Integer lafId) {
        this.lafId = lafId;
    }

    public Integer getLafId() {
        return lafId;
    }

    public void setLafId(Integer lafId) {
        this.lafId = lafId;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFoursquare() {
        return foursquare;
    }

    public void setFoursquare(String foursquare) {
        this.foursquare = foursquare;
    }

    @XmlTransient
    public LafUser getLafUser() {
        return lafUser;
    }

    public void setLafUser(LafUser lafUser) {
        this.lafUser = lafUser;
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
        if (!(object instanceof LafUserMedia)) {
            return false;
        }
        LafUserMedia other = (LafUserMedia) object;
        if ((this.lafId == null && other.lafId != null) || (this.lafId != null && !this.lafId.equals(other.lafId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "johnson4j.entity.LafUserMedia[ lafId=" + lafId + " ]";
    }
    
}
