/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Adphoto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Advertisement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lenovo
 */
public class AdphotoJpaController implements Serializable {

    public AdphotoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Adphoto adphoto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advertisement adID = adphoto.getAdID();
            if (adID != null) {
                adID = em.getReference(adID.getClass(), adID.getId());
                adphoto.setAdID(adID);
            }
            em.persist(adphoto);
            if (adID != null) {
                adID.getAdphotoCollection().add(adphoto);
                adID = em.merge(adID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAdphoto(adphoto.getId()) != null) {
                throw new PreexistingEntityException("Adphoto " + adphoto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Adphoto adphoto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Adphoto persistentAdphoto = em.find(Adphoto.class, adphoto.getId());
            Advertisement adIDOld = persistentAdphoto.getAdID();
            Advertisement adIDNew = adphoto.getAdID();
            if (adIDNew != null) {
                adIDNew = em.getReference(adIDNew.getClass(), adIDNew.getId());
                adphoto.setAdID(adIDNew);
            }
            adphoto = em.merge(adphoto);
            if (adIDOld != null && !adIDOld.equals(adIDNew)) {
                adIDOld.getAdphotoCollection().remove(adphoto);
                adIDOld = em.merge(adIDOld);
            }
            if (adIDNew != null && !adIDNew.equals(adIDOld)) {
                adIDNew.getAdphotoCollection().add(adphoto);
                adIDNew = em.merge(adIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = adphoto.getId();
                if (findAdphoto(id) == null) {
                    throw new NonexistentEntityException("The adphoto with id " + id + " no longer exists.");
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
            Adphoto adphoto;
            try {
                adphoto = em.getReference(Adphoto.class, id);
                adphoto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The adphoto with id " + id + " no longer exists.", enfe);
            }
            Advertisement adID = adphoto.getAdID();
            if (adID != null) {
                adID.getAdphotoCollection().remove(adphoto);
                adID = em.merge(adID);
            }
            em.remove(adphoto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Adphoto> findAdphotoEntities() {
        return findAdphotoEntities(true, -1, -1);
    }

    public List<Adphoto> findAdphotoEntities(int maxResults, int firstResult) {
        return findAdphotoEntities(false, maxResults, firstResult);
    }

    private List<Adphoto> findAdphotoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Adphoto.class));
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

    public Adphoto findAdphoto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Adphoto.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdphotoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Adphoto> rt = cq.from(Adphoto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
