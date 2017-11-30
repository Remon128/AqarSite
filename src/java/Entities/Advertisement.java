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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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
    , @NamedQuery(name = "Advertisement.findByPropstatus", query = "SELECT a FROM Advertisement a WHERE a.propstatus = :propstatus")
    , @NamedQuery(name = "Advertisement.findByProptype", query = "SELECT a FROM Advertisement a WHERE a.proptype = :proptype")})
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Size")
    private Integer size;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "floor")
    private Integer floor;
    @Column(name = "propstatus")
    private String propstatus;
    @Column(name = "proptype")
    private String proptype;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @JoinColumn(name = "userID", referencedColumnName = "ID")
    @ManyToOne
    private Useraccount userID;

    public Advertisement() {
    }

    public Advertisement(Integer id) {
        this.id = id;
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

    public String getPropstatus() {
        return propstatus;
    }

    public void setPropstatus(String propstatus) {
        this.propstatus = propstatus;
    }

    public String getProptype() {
        return proptype;
    }

    public void setProptype(String proptype) {
        this.proptype = proptype;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Useraccount getUserID() {
        return userID;
    }

    public void setUserID(Useraccount userID) {
        this.userID = userID;
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
