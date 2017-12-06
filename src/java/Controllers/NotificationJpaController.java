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
import Entities.Notification;
import Entities.Useraccount;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author lenovo
 */
public class NotificationJpaController implements Serializable {

    public NotificationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notification notification) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advertisement adID = notification.getAdID();
            if (adID != null) {
                adID = em.getReference(adID.getClass(), adID.getId());
                notification.setAdID(adID);
            }
            Useraccount accountID = notification.getAccountID();
            if (accountID != null) {
                accountID = em.getReference(accountID.getClass(), accountID.getId());
                notification.setAccountID(accountID);
            }
            em.persist(notification);
            if (adID != null) {
                adID.getNotificationCollection().add(notification);
                adID = em.merge(adID);
            }
            if (accountID != null) {
                accountID.getNotificationCollection().add(notification);
                accountID = em.merge(accountID);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotification(notification.getId()) != null) {
                throw new PreexistingEntityException("Notification " + notification + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notification notification) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notification persistentNotification = em.find(Notification.class, notification.getId());
            Advertisement adIDOld = persistentNotification.getAdID();
            Advertisement adIDNew = notification.getAdID();
            Useraccount accountIDOld = persistentNotification.getAccountID();
            Useraccount accountIDNew = notification.getAccountID();
            if (adIDNew != null) {
                adIDNew = em.getReference(adIDNew.getClass(), adIDNew.getId());
                notification.setAdID(adIDNew);
            }
            if (accountIDNew != null) {
                accountIDNew = em.getReference(accountIDNew.getClass(), accountIDNew.getId());
                notification.setAccountID(accountIDNew);
            }
            notification = em.merge(notification);
            if (adIDOld != null && !adIDOld.equals(adIDNew)) {
                adIDOld.getNotificationCollection().remove(notification);
                adIDOld = em.merge(adIDOld);
            }
            if (adIDNew != null && !adIDNew.equals(adIDOld)) {
                adIDNew.getNotificationCollection().add(notification);
                adIDNew = em.merge(adIDNew);
            }
            if (accountIDOld != null && !accountIDOld.equals(accountIDNew)) {
                accountIDOld.getNotificationCollection().remove(notification);
                accountIDOld = em.merge(accountIDOld);
            }
            if (accountIDNew != null && !accountIDNew.equals(accountIDOld)) {
                accountIDNew.getNotificationCollection().add(notification);
                accountIDNew = em.merge(accountIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notification.getId();
                if (findNotification(id) == null) {
                    throw new NonexistentEntityException("The notification with id " + id + " no longer exists.");
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
            Notification notification;
            try {
                notification = em.getReference(Notification.class, id);
                notification.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notification with id " + id + " no longer exists.", enfe);
            }
            Advertisement adID = notification.getAdID();
            if (adID != null) {
                adID.getNotificationCollection().remove(notification);
                adID = em.merge(adID);
            }
            Useraccount accountID = notification.getAccountID();
            if (accountID != null) {
                accountID.getNotificationCollection().remove(notification);
                accountID = em.merge(accountID);
            }
            em.remove(notification);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notification> findNotificationEntities() {
        return findNotificationEntities(true, -1, -1);
    }

    public List<Notification> findNotificationEntities(int maxResults, int firstResult) {
        return findNotificationEntities(false, maxResults, firstResult);
    }

    private List<Notification> findNotificationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notification.class));
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

    public Notification findNotification(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notification.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notification> rt = cq.from(Notification.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
