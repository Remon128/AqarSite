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
@Table(name = "adphoto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adphoto.findAll", query = "SELECT a FROM Adphoto a")
    , @NamedQuery(name = "Adphoto.findById", query = "SELECT a FROM Adphoto a WHERE a.id = :id")})
public class Adphoto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "photo")
    private byte[] photo;
    @JoinColumn(name = "adID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Advertisement adID;

    public Adphoto() {
    }

    public Adphoto(Integer id) {
        this.id = id;
    }

    public Adphoto(Integer id, byte[] photo) {
        this.id = id;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Advertisement getAdID() {
        return adID;
    }

    public void setAdID(Advertisement adID) {
        this.adID = adID;
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
        if (!(object instanceof Adphoto)) {
            return false;
        }
        Adphoto other = (Adphoto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Adphoto[ id=" + id + " ]";
    }
    
}
