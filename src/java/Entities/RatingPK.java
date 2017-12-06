/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author lenovo
 */
@Embeddable
public class RatingPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "adID")
    private int adID;
    @Basic(optional = false)
    @Column(name = "accountID")
    private int accountID;

    public RatingPK() {
    }

    public RatingPK(int adID, int accountID) {
        this.adID = adID;
        this.accountID = accountID;
    }

    public int getAdID() {
        return adID;
    }

    public void setAdID(int adID) {
        this.adID = adID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) adID;
        hash += (int) accountID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RatingPK)) {
            return false;
        }
        RatingPK other = (RatingPK) object;
        if (this.adID != other.adID) {
            return false;
        }
        if (this.accountID != other.accountID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.RatingPK[ adID=" + adID + ", accountID=" + accountID + " ]";
    }
    
}
