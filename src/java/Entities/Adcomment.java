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
@Table(name = "adcomment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adcomment.findAll", query = "SELECT a FROM Adcomment a")
    , @NamedQuery(name = "Adcomment.findById", query = "SELECT a FROM Adcomment a WHERE a.id = :id")})
public class Adcomment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "AdComment")
    private String adComment;
    @JoinColumn(name = "adID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Advertisement adID;
    @JoinColumn(name = "accountID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Useraccount accountID;

    public Adcomment() {
    }

    public Adcomment(Integer id) {
        this.id = id;
    }

    public Adcomment(Integer id, String adComment) {
        this.id = id;
        this.adComment = adComment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdComment() {
        return adComment;
    }

    public void setAdComment(String adComment) {
        this.adComment = adComment;
    }

    public Advertisement getAdID() {
        return adID;
    }

    public void setAdID(Advertisement adID) {
        this.adID = adID;
    }

    public Useraccount getAccountID() {
        return accountID;
    }

    public void setAccountID(Useraccount accountID) {
        this.accountID = accountID;
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
        if (!(object instanceof Adcomment)) {
            return false;
        }
        Adcomment other = (Adcomment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Adcomment[ id=" + id + " ]";
    }
    
}
