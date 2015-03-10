/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package johnson4j.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "laf_user_token")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LafUserToken.findAll", query = "SELECT l FROM LafUserToken l"),
    @NamedQuery(name = "LafUserToken.findByLafId", query = "SELECT l FROM LafUserToken l WHERE l.lafId = :lafId"),
    @NamedQuery(name = "LafUserToken.findByTokenValue", query = "SELECT l FROM LafUserToken l WHERE l.tokenValue = :tokenValue"),
    @NamedQuery(name = "LafUserToken.findByCreationDate", query = "SELECT l FROM LafUserToken l WHERE l.creationDate = :creationDate"),
    @NamedQuery(name = "LafUserToken.findByExpiryDate", query = "SELECT l FROM LafUserToken l WHERE l.expiryDate = :expiryDate")})
public class LafUserToken implements Serializable {
    @Size(max = 32)
    @Column(name = "status")
    private String status;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "laf_id")
    private Integer lafId;
    @Size(max = 64)
    @Column(name = "token_value")
    private String tokenValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expiry_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;
   

    public LafUserToken() {
    }
    

    public LafUserToken(Integer lafId) {
        this.lafId = lafId;
    }

    public LafUserToken(Integer lafId, Date creationDate, Date expiryDate) {
        this.lafId = lafId;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
    }

    public Integer getLafId() {
        return lafId;
    }

    public void setLafId(Integer lafId) {
        this.lafId = lafId;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
        if (!(object instanceof LafUserToken)) {
            return false;
        }
        LafUserToken other = (LafUserToken) object;
        if ((this.lafId == null && other.lafId != null) || (this.lafId != null && !this.lafId.equals(other.lafId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "johnson4j.entity.LafUserToken[ lafId=" + lafId + " ]";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
