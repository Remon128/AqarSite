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
import Entities.Useraccount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lenovo
 */
public class UseraccountJpaController implements Serializable {

    public UseraccountJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Useraccount useraccount) throws PreexistingEntityException, Exception {
        if (useraccount.getAdvertisementCollection() == null) {
            useraccount.setAdvertisementCollection(new ArrayList<Advertisement>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Advertisement> attachedAdvertisementCollection = new ArrayList<Advertisement>();
            for (Advertisement advertisementCollectionAdvertisementToAttach : useraccount.getAdvertisementCollection()) {
                advertisementCollectionAdvertisementToAttach = em.getReference(advertisementCollectionAdvertisementToAttach.getClass(), advertisementCollectionAdvertisementToAttach.getId());
                attachedAdvertisementCollection.add(advertisementCollectionAdvertisementToAttach);
            }
            useraccount.setAdvertisementCollection(attachedAdvertisementCollection);
            em.persist(useraccount);
            for (Advertisement advertisementCollectionAdvertisement : useraccount.getAdvertisementCollection()) {
                Useraccount oldUserIDOfAdvertisementCollectionAdvertisement = advertisementCollectionAdvertisement.getUserID();
                advertisementCollectionAdvertisement.setUserID(useraccount);
                advertisementCollectionAdvertisement = em.merge(advertisementCollectionAdvertisement);
                if (oldUserIDOfAdvertisementCollectionAdvertisement != null) {
                    oldUserIDOfAdvertisementCollectionAdvertisement.getAdvertisementCollection().remove(advertisementCollectionAdvertisement);
                    oldUserIDOfAdvertisementCollectionAdvertisement = em.merge(oldUserIDOfAdvertisementCollectionAdvertisement);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUseraccount(useraccount.getId()) != null) {
                throw new PreexistingEntityException("Useraccount " + useraccount + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Useraccount useraccount) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Useraccount persistentUseraccount = em.find(Useraccount.class, useraccount.getId());
            Collection<Advertisement> advertisementCollectionOld = persistentUseraccount.getAdvertisementCollection();
            Collection<Advertisement> advertisementCollectionNew = useraccount.getAdvertisementCollection();
            Collection<Advertisement> attachedAdvertisementCollectionNew = new ArrayList<Advertisement>();
            for (Advertisement advertisementCollectionNewAdvertisementToAttach : advertisementCollectionNew) {
                advertisementCollectionNewAdvertisementToAttach = em.getReference(advertisementCollectionNewAdvertisementToAttach.getClass(), advertisementCollectionNewAdvertisementToAttach.getId());
                attachedAdvertisementCollectionNew.add(advertisementCollectionNewAdvertisementToAttach);
            }
            advertisementCollectionNew = attachedAdvertisementCollectionNew;
            useraccount.setAdvertisementCollection(advertisementCollectionNew);
            useraccount = em.merge(useraccount);
            for (Advertisement advertisementCollectionOldAdvertisement : advertisementCollectionOld) {
                if (!advertisementCollectionNew.contains(advertisementCollectionOldAdvertisement)) {
                    advertisementCollectionOldAdvertisement.setUserID(null);
                    advertisementCollectionOldAdvertisement = em.merge(advertisementCollectionOldAdvertisement);
                }
            }
            for (Advertisement advertisementCollectionNewAdvertisement : advertisementCollectionNew) {
                if (!advertisementCollectionOld.contains(advertisementCollectionNewAdvertisement)) {
                    Useraccount oldUserIDOfAdvertisementCollectionNewAdvertisement = advertisementCollectionNewAdvertisement.getUserID();
                    advertisementCollectionNewAdvertisement.setUserID(useraccount);
                    advertisementCollectionNewAdvertisement = em.merge(advertisementCollectionNewAdvertisement);
                    if (oldUserIDOfAdvertisementCollectionNewAdvertisement != null && !oldUserIDOfAdvertisementCollectionNewAdvertisement.equals(useraccount)) {
                        oldUserIDOfAdvertisementCollectionNewAdvertisement.getAdvertisementCollection().remove(advertisementCollectionNewAdvertisement);
                        oldUserIDOfAdvertisementCollectionNewAdvertisement = em.merge(oldUserIDOfAdvertisementCollectionNewAdvertisement);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = useraccount.getId();
                if (findUseraccount(id) == null) {
                    throw new NonexistentEntityException("The useraccount with id " + id + " no longer exists.");
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
            Useraccount useraccount;
            try {
                useraccount = em.getReference(Useraccount.class, id);
                useraccount.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The useraccount with id " + id + " no longer exists.", enfe);
            }
            Collection<Advertisement> advertisementCollection = useraccount.getAdvertisementCollection();
            for (Advertisement advertisementCollectionAdvertisement : advertisementCollection) {
                advertisementCollectionAdvertisement.setUserID(null);
                advertisementCollectionAdvertisement = em.merge(advertisementCollectionAdvertisement);
            }
            em.remove(useraccount);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Useraccount> findUseraccountEntities() {
        return findUseraccountEntities(true, -1, -1);
    }

    public List<Useraccount> findUseraccountEntities(int maxResults, int firstResult) {
        return findUseraccountEntities(false, maxResults, firstResult);
    }

    private List<Useraccount> findUseraccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Useraccount.class));
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

    public Useraccount findUseraccount(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Useraccount.class, id);
        } finally {
            em.close();
        }
    }

    public int getUseraccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Useraccount> rt = cq.from(Useraccount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
