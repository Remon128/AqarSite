/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "advertisement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Advertisement.findAll", query = "SELECT a FROM Advertisement a")
    , @NamedQuery(name = "Advertisement.findById", query = "SELECT a FROM Advertisement a WHERE a.id = :id")
    , @NamedQuery(name = "Advertisement.findBySize", query = "SELECT a FROM Advertisement a WHERE a.size = :size")
    , @NamedQuery(name = "Advertisement.findByFloor", query = "SELECT a FROM Advertisement a WHERE a.floor = :floor")
    , @NamedQuery(name = "Advertisement.findByAdType", query = "SELECT a FROM Advertisement a WHERE a.adType = :adType")})
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "size")
    private Integer size;
    @Basic(optional = false)
    @Lob
    @Column(name = "title")
    private String title;
    @Lob
    @Column(name = "Description")
    private String description;
    @Column(name = "floor")
    private Integer floor;
    @Lob
    @Column(name = "propStatus")
    private String propStatus;
    @Lob
    @Column(name = "propType")
    private String propType;
    @Column(name = "adType")
    private Integer adType;
    @OneToMany(mappedBy = "adID")
    private Collection<Notification> notificationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adID")
    private Collection<Adphoto> adphotoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "adID")
    private Collection<Adcomment> adcommentCollection;
    @JoinColumn(name = "accountID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Useraccount accountID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "advertisement")
    private Collection<Rating> ratingCollection;

    public Advertisement() {
    }

    public Advertisement(Integer id) {
        this.id = id;
    }

    public Advertisement(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getAdType() {
        return adType;
    }

    public void setAdType(Integer adType) {
        this.adType = adType;
    }

    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }

    @XmlTransient
    public Collection<Adphoto> getAdphotoCollection() {
        return adphotoCollection;
    }

    public void setAdphotoCollection(Collection<Adphoto> adphotoCollection) {
        this.adphotoCollection = adphotoCollection;
    }

    @XmlTransient
    public Collection<Adcomment> getAdcommentCollection() {
        return adcommentCollection;
    }

    public void setAdcommentCollection(Collection<Adcomment> adcommentCollection) {
        this.adcommentCollection = adcommentCollection;
    }

    public Useraccount getAccountID() {
        return accountID;
    }

    public void setAccountID(Useraccount accountID) {
        this.accountID = accountID;
    }

    @XmlTransient
    public Collection<Rating> getRatingCollection() {
        return ratingCollection;
    }

    public void setRatingCollection(Collection<Rating> ratingCollection) {
        this.ratingCollection = ratingCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Advertisement)) {
            return false;
        }
        Advertisement other = (Advertisement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Advertisement[ id=" + id + " ]";
    }
    
}
