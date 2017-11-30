/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Advertisement;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Useraccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lenovo
 */
public class AdvertisementJpaController implements Serializable {

    public AdvertisementJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Advertisement advertisement) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Useraccount userID = advertisement.getUserID();
            if (userID != null) {
                userID = em.getReference(userID.getClass(), userID.getId());
                advertisement.setUserID(userID);
            }
            em.persist(advertisement);
            if (userID != null) {
                userID.getAdvertisementCollection().add(advertisement);
                userID = em.merge(userID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdvertisement(advertisement.getId()) != null) {
                throw new PreexistingEntityException("Advertisement " + advertisement + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Advertisement advertisement) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advertisement persistentAdvertisement = em.find(Advertisement.class, advertisement.getId());
            Useraccount userIDOld = persistentAdvertisement.getUserID();
            Useraccount userIDNew = advertisement.getUserID();
            if (userIDNew != null) {
                userIDNew = em.getReference(userIDNew.getClass(), userIDNew.getId());
                advertisement.setUserID(userIDNew);
            }
            advertisement = em.merge(advertisement);
            if (userIDOld != null && !userIDOld.equals(userIDNew)) {
                userIDOld.getAdvertisementCollection().remove(advertisement);
                userIDOld = em.merge(userIDOld);
            }
            if (userIDNew != null && !userIDNew.equals(userIDOld)) {
                userIDNew.getAdvertisementCollection().add(advertisement);
                userIDNew = em.merge(userIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = advertisement.getId();
                if (findAdvertisement(id) == null) {
                    throw new NonexistentEntityException("The advertisement with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advertisement advertisement;
            try {
                advertisement = em.getReference(Advertisement.class, id);
                advertisement.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The advertisement with id " + id + " no longer exists.", enfe);
            }
            Useraccount userID = advertisement.getUserID();
            if (userID != null) {
                userID.getAdvertisementCollection().remove(advertisement);
                userID = em.merge(userID);
            }
            em.remove(advertisement);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Advertisement> findAdvertisementEntities() {
        return findAdvertisementEntities(true, -1, -1);
    }

    public List<Advertisement> findAdvertisementEntities(int maxResults, int firstResult) {
        return findAdvertisementEntities(false, maxResults, firstResult);
    }

    private List<Advertisement> findAdvertisementEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Advertisement.class));
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

    public Advertisement findAdvertisement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Advertisement.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdvertisementCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Advertisement> rt = cq.from(Advertisement.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
