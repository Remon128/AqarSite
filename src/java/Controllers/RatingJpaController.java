/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Advertisement;
import Entities.Rating;
import Entities.RatingPK;
import Entities.Useraccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lenovo
 */
public class RatingJpaController implements Serializable {

    public RatingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rating rating) throws PreexistingEntityException, Exception {
        if (rating.getRatingPK() == null) {
            rating.setRatingPK(new RatingPK());
        }
        rating.getRatingPK().setAccountID(rating.getUseraccount().getId());
        rating.getRatingPK().setAdID(rating.getAdvertisement().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advertisement advertisement = rating.getAdvertisement();
            if (advertisement != null) {
                advertisement = em.getReference(advertisement.getClass(), advertisement.getId());
                rating.setAdvertisement(advertisement);
            }
            Useraccount useraccount = rating.getUseraccount();
            if (useraccount != null) {
                useraccount = em.getReference(useraccount.getClass(), useraccount.getId());
                rating.setUseraccount(useraccount);
            }
            em.persist(rating);
            if (advertisement != null) {
                advertisement.getRatingCollection().add(rating);
                advertisement = em.merge(advertisement);
            }
            if (useraccount != null) {
                useraccount.getRatingCollection().add(rating);
                useraccount = em.merge(useraccount);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRating(rating.getRatingPK()) != null) {
                throw new PreexistingEntityException("Rating " + rating + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rating rating) throws NonexistentEntityException, Exception {
        rating.getRatingPK().setAccountID(rating.getUseraccount().getId());
        rating.getRatingPK().setAdID(rating.getAdvertisement().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rating persistentRating = em.find(Rating.class, rating.getRatingPK());
            Advertisement advertisementOld = persistentRating.getAdvertisement();
            Advertisement advertisementNew = rating.getAdvertisement();
            Useraccount useraccountOld = persistentRating.getUseraccount();
            Useraccount useraccountNew = rating.getUseraccount();
            if (advertisementNew != null) {
                advertisementNew = em.getReference(advertisementNew.getClass(), advertisementNew.getId());
                rating.setAdvertisement(advertisementNew);
            }
            if (useraccountNew != null) {
                useraccountNew = em.getReference(useraccountNew.getClass(), useraccountNew.getId());
                rating.setUseraccount(useraccountNew);
            }
            rating = em.merge(rating);
            if (advertisementOld != null && !advertisementOld.equals(advertisementNew)) {
                advertisementOld.getRatingCollection().remove(rating);
                advertisementOld = em.merge(advertisementOld);
            }
            if (advertisementNew != null && !advertisementNew.equals(advertisementOld)) {
                advertisementNew.getRatingCollection().add(rating);
                advertisementNew = em.merge(advertisementNew);
            }
            if (useraccountOld != null && !useraccountOld.equals(useraccountNew)) {
                useraccountOld.getRatingCollection().remove(rating);
                useraccountOld = em.merge(useraccountOld);
            }
            if (useraccountNew != null && !useraccountNew.equals(useraccountOld)) {
                useraccountNew.getRatingCollection().add(rating);
                useraccountNew = em.merge(useraccountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RatingPK id = rating.getRatingPK();
                if (findRating(id) == null) {
                    throw new NonexistentEntityException("The rating with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RatingPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rating rating;
            try {
                rating = em.getReference(Rating.class, id);
                rating.getRatingPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rating with id " + id + " no longer exists.", enfe);
            }
            Advertisement advertisement = rating.getAdvertisement();
            if (advertisement != null) {
                advertisement.getRatingCollection().remove(rating);
                advertisement = em.merge(advertisement);
            }
            Useraccount useraccount = rating.getUseraccount();
            if (useraccount != null) {
                useraccount.getRatingCollection().remove(rating);
                useraccount = em.merge(useraccount);
            }
            em.remove(rating);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rating> findRatingEntities() {
        return findRatingEntities(true, -1, -1);
    }

    public List<Rating> findRatingEntities(int maxResults, int firstResult) {
        return findRatingEntities(false, maxResults, firstResult);
    }

    private List<Rating> findRatingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rating.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rating findRating(RatingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rating.class, id);
        } finally {
            em.close();
        }
    }

    public int getRatingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rating> rt = cq.from(Rating.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
