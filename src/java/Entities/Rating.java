/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "rating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rating.findAll", query = "SELECT r FROM Rating r")
    , @NamedQuery(name = "Rating.findByAdID", query = "SELECT r FROM Rating r WHERE r.ratingPK.adID = :adID")
    , @NamedQuery(name = "Rating.findByAccountID", query = "SELECT r FROM Rating r WHERE r.ratingPK.accountID = :accountID")
    , @NamedQuery(name = "Rating.findByRateValue", query = "SELECT r FROM Rating r WHERE r.rateValue = :rateValue")})
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RatingPK ratingPK;
    @Basic(optional = false)
    @Column(name = "rateValue")
    private int rateValue;
    @JoinColumn(name = "adID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Advertisement advertisement;
    @JoinColumn(name = "accountID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Useraccount useraccount;

    public Rating() {
    }

    public Rating(RatingPK ratingPK) {
        this.ratingPK = ratingPK;
    }

    public Rating(RatingPK ratingPK, int rateValue) {
        this.ratingPK = ratingPK;
        this.rateValue = rateValue;
    }

    public Rating(int adID, int accountID) {
        this.ratingPK = new RatingPK(adID, accountID);
    }

    public RatingPK getRatingPK() {
        return ratingPK;
    }

    public void setRatingPK(RatingPK ratingPK) {
        this.ratingPK = ratingPK;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
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
        hash += (ratingPK != null ? ratingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.ratingPK == null && other.ratingPK != null) || (this.ratingPK != null && !this.ratingPK.equals(other.ratingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Rating[ ratingPK=" + ratingPK + " ]";
    }
    
}
