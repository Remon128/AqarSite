/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "preference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preference.findAll", query = "SELECT p FROM Preference p")
    , @NamedQuery(name = "Preference.findByAccountID", query = "SELECT p FROM Preference p WHERE p.accountID = :accountID")
    , @NamedQuery(name = "Preference.findBySize", query = "SELECT p FROM Preference p WHERE p.size = :size")
    , @NamedQuery(name = "Preference.findByFloor", query = "SELECT p FROM Preference p WHERE p.floor = :floor")})
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "accountID")
    private Integer accountID;
    @Column(name = "size")
    private Integer size;
    @Column(name = "floor")
    private Integer floor;
    @Lob
    @Column(name = "propStatus")
    private String propStatus;
    @Lob
    @Column(name = "propType")
    private String propType;
    @JoinColumn(name = "accountID", referencedColumnName = "ID", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Useraccount useraccount;

    public Preference() {
    }

    public Preference(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(String propStatus) {
        this.propStatus = propStatus;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public Useraccount getUseraccount() {
        return useraccount;
    }

    public void setUseraccount(Useraccount useraccount) {
        this.useraccount = useraccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountID != null ? accountID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preference)) {
            return false;
        }
        Preference other = (Preference) object;
        if ((this.accountID == null && other.accountID != null) || (this.accountID != null && !this.accountID.equals(other.accountID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Preference[ accountID=" + accountID + " ]";
    }
    
}
