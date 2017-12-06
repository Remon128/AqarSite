/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Adcomment;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Advertisement;
import Entities.Useraccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lenovo
 */
public class AdcommentJpaController implements Serializable {

    public AdcommentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Adcomment adcomment) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advertisement adID = adcomment.getAdID();
            if (adID != null) {
                adID = em.getReference(adID.getClass(), adID.getId());
                adcomment.setAdID(adID);
            }
            Useraccount accountID = adcomment.getAccountID();
            if (accountID != null) {
                accountID = em.getReference(accountID.getClass(), accountID.getId());
                adcomment.setAccountID(accountID);
            }
            em.persist(adcomment);
            if (adID != null) {
                adID.getAdcommentCollection().add(adcomment);
                adID = em.merge(adID);
            }
            if (accountID != null) {
                accountID.getAdcommentCollection().add(adcomment);
                accountID = em.merge(accountID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdcomment(adcomment.getId()) != null) {
                throw new PreexistingEntityException("Adcomment " + adcomment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adcomment adcomment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adcomment persistentAdcomment = em.find(Adcomment.class, adcomment.getId());
            Advertisement adIDOld = persistentAdcomment.getAdID();
            Advertisement adIDNew = adcomment.getAdID();
            Useraccount accountIDOld = persistentAdcomment.getAccountID();
            Useraccount accountIDNew = adcomment.getAccountID();
            if (adIDNew != null) {
                adIDNew = em.getReference(adIDNew.getClass(), adIDNew.getId());
                adcomment.setAdID(adIDNew);
            }
            if (accountIDNew != null) {
                accountIDNew = em.getReference(accountIDNew.getClass(), accountIDNew.getId());
                adcomment.setAccountID(accountIDNew);
            }
            adcomment = em.merge(adcomment);
            if (adIDOld != null && !adIDOld.equals(adIDNew)) {
                adIDOld.getAdcommentCollection().remove(adcomment);
                adIDOld = em.merge(adIDOld);
            }
            if (adIDNew != null && !adIDNew.equals(adIDOld)) {
                adIDNew.getAdcommentCollection().add(adcomment);
                adIDNew = em.merge(adIDNew);
            }
            if (accountIDOld != null && !accountIDOld.equals(accountIDNew)) {
                accountIDOld.getAdcommentCollection().remove(adcomment);
                accountIDOld = em.merge(accountIDOld);
            }
            if (accountIDNew != null && !accountIDNew.equals(accountIDOld)) {
                accountIDNew.getAdcommentCollection().add(adcomment);
                accountIDNew = em.merge(accountIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adcomment.getId();
                if (findAdcomment(id) == null) {
                    throw new NonexistentEntityException("The adcomment with id " + id + " no longer exists.");
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
            Adcomment adcomment;
            try {
                adcomment = em.getReference(Adcomment.class, id);
                adcomment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adcomment with id " + id + " no longer exists.", enfe);
            }
            Advertisement adID = adcomment.getAdID();
            if (adID != null) {
                adID.getAdcommentCollection().remove(adcomment);
                adID = em.merge(adID);
            }
            Useraccount accountID = adcomment.getAccountID();
            if (accountID != null) {
                accountID.getAdcommentCollection().remove(adcomment);
                accountID = em.merge(accountID);
            }
            em.remove(adcomment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Adcomment> findAdcommentEntities() {
        return findAdcommentEntities(true, -1, -1);
    }

    public List<Adcomment> findAdcommentEntities(int maxResults, int firstResult) {
        return findAdcommentEntities(false, maxResults, firstResult);
    }

    private List<Adcomment> findAdcommentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adcomment.class));
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

    public Adcomment findAdcomment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adcomment.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdcommentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adcomment> rt = cq.from(Adcomment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
