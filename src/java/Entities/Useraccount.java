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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lenovo
 */
@Entity
@Table(name = "useraccount")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Useraccount.findAll", query = "SELECT u FROM Useraccount u")
    , @NamedQuery(name = "Useraccount.findById", query = "SELECT u FROM Useraccount u WHERE u.id = :id")})
public class Useraccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "userName")
    private String userName;
    @Basic(optional = false)
    @Lob
    @Column(name = "userPassword")
    private String userPassword;
    @Lob
    @Column(name = "fullName")
    private String fullName;
    @Basic(optional = false)
    @Lob
    @Column(name = "email")
    private String email;
    @Lob
    @Column(name = "picture")
    private byte[] picture;
    @Lob
    @Column(name = "phone")
    private String phone;
    @OneToMany(mappedBy = "accountID")
    private Collection<Notification> notificationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountID")
    private Collection<Adcomment> adcommentCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "useraccount")
    private Preference preference;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountID")
    private Collection<Advertisement> advertisementCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "useraccount")
    private Collection<Rating> ratingCollection;

    public Useraccount() {
    }

    public Useraccount(Integer id) {
        this.id = id;
    }

    public Useraccount(Integer id, String userName, String userPassword, String email) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }

    @XmlTransient
    public Collection<Adcomment> getAdcommentCollection() {
        return adcommentCollection;
    }

    public void setAdcommentCollection(Collection<Adcomment> adcommentCollection) {
        this.adcommentCollection = adcommentCollection;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    @XmlTransient
    public Collection<Advertisement> getAdvertisementCollection() {
        return advertisementCollection;
    }

    public void setAdvertisementCollection(Collection<Advertisement> advertisementCollection) {
        this.advertisementCollection = advertisementCollection;
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
        if (!(object instanceof Useraccount)) {
            return false;
        }
        Useraccount other = (Useraccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Useraccount[ id=" + id + " ]";
    }
    
}
