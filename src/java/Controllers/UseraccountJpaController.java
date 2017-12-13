/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Preference;
import Entities.Notification;
import java.util.ArrayList;
import java.util.Collection;
import Entities.Adcomment;
import Entities.Advertisement;
import Entities.Rating;
import Entities.Useraccount;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lenovo
 */
public class UseraccountJpaController implements Serializable {

    public UseraccountJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public UseraccountJpaController() {
        emf = Persistence.createEntityManagerFactory("AqarTestPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Useraccount useraccount) throws PreexistingEntityException, Exception {
        if (useraccount.getNotificationCollection() == null) {
            useraccount.setNotificationCollection(new ArrayList<Notification>());
        }
        if (useraccount.getAdcommentCollection() == null) {
            useraccount.setAdcommentCollection(new ArrayList<Adcomment>());
        }
        if (useraccount.getAdvertisementCollection() == null) {
            useraccount.setAdvertisementCollection(new ArrayList<Advertisement>());
        }
        if (useraccount.getRatingCollection() == null) {
            useraccount.setRatingCollection(new ArrayList<Rating>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preference preference = useraccount.getPreference();
            if (preference != null) {
                preference = em.getReference(preference.getClass(), preference.getAccountID());
                useraccount.setPreference(preference);
            }
            Collection<Notification> attachedNotificationCollection = new ArrayList<Notification>();
            for (Notification notificationCollectionNotificationToAttach : useraccount.getNotificationCollection()) {
                notificationCollectionNotificationToAttach = em.getReference(notificationCollectionNotificationToAttach.getClass(), notificationCollectionNotificationToAttach.getId());
                attachedNotificationCollection.add(notificationCollectionNotificationToAttach);
            }
            useraccount.setNotificationCollection(attachedNotificationCollection);
            Collection<Adcomment> attachedAdcommentCollection = new ArrayList<Adcomment>();
            for (Adcomment adcommentCollectionAdcommentToAttach : useraccount.getAdcommentCollection()) {
                adcommentCollectionAdcommentToAttach = em.getReference(adcommentCollectionAdcommentToAttach.getClass(), adcommentCollectionAdcommentToAttach.getId());
                attachedAdcommentCollection.add(adcommentCollectionAdcommentToAttach);
            }
            useraccount.setAdcommentCollection(attachedAdcommentCollection);
            Collection<Advertisement> attachedAdvertisementCollection = new ArrayList<Advertisement>();
            for (Advertisement advertisementCollectionAdvertisementToAttach : useraccount.getAdvertisementCollection()) {
                advertisementCollectionAdvertisementToAttach = em.getReference(advertisementCollectionAdvertisementToAttach.getClass(), advertisementCollectionAdvertisementToAttach.getId());
                attachedAdvertisementCollection.add(advertisementCollectionAdvertisementToAttach);
            }
            useraccount.setAdvertisementCollection(attachedAdvertisementCollection);
            Collection<Rating> attachedRatingCollection = new ArrayList<Rating>();
            for (Rating ratingCollectionRatingToAttach : useraccount.getRatingCollection()) {
                ratingCollectionRatingToAttach = em.getReference(ratingCollectionRatingToAttach.getClass(), ratingCollectionRatingToAttach.getRatingPK());
                attachedRatingCollection.add(ratingCollectionRatingToAttach);
            }
            useraccount.setRatingCollection(attachedRatingCollection);
            em.persist(useraccount);
            if (preference != null) {
                Useraccount oldUseraccountOfPreference = preference.getUseraccount();
                if (oldUseraccountOfPreference != null) {
                    oldUseraccountOfPreference.setPreference(null);
                    oldUseraccountOfPreference = em.merge(oldUseraccountOfPreference);
                }
                preference.setUseraccount(useraccount);
                preference = em.merge(preference);
            }
            for (Notification notificationCollectionNotification : useraccount.getNotificationCollection()) {
                Useraccount oldAccountIDOfNotificationCollectionNotification = notificationCollectionNotification.getAccountID();
                notificationCollectionNotification.setAccountID(useraccount);
                notificationCollectionNotification = em.merge(notificationCollectionNotification);
                if (oldAccountIDOfNotificationCollectionNotification != null) {
                    oldAccountIDOfNotificationCollectionNotification.getNotificationCollection().remove(notificationCollectionNotification);
                    oldAccountIDOfNotificationCollectionNotification = em.merge(oldAccountIDOfNotificationCollectionNotification);
                }
            }
            for (Adcomment adcommentCollectionAdcomment : useraccount.getAdcommentCollection()) {
                Useraccount oldAccountIDOfAdcommentCollectionAdcomment = adcommentCollectionAdcomment.getAccountID();
                adcommentCollectionAdcomment.setAccountID(useraccount);
                adcommentCollectionAdcomment = em.merge(adcommentCollectionAdcomment);
                if (oldAccountIDOfAdcommentCollectionAdcomment != null) {
                    oldAccountIDOfAdcommentCollectionAdcomment.getAdcommentCollection().remove(adcommentCollectionAdcomment);
                    oldAccountIDOfAdcommentCollectionAdcomment = em.merge(oldAccountIDOfAdcommentCollectionAdcomment);
                }
            }
            for (Advertisement advertisementCollectionAdvertisement : useraccount.getAdvertisementCollection()) {
                Useraccount oldAccountIDOfAdvertisementCollectionAdvertisement = advertisementCollectionAdvertisement.getAccountID();
                advertisementCollectionAdvertisement.setAccountID(useraccount);
                advertisementCollectionAdvertisement = em.merge(advertisementCollectionAdvertisement);
                if (oldAccountIDOfAdvertisementCollectionAdvertisement != null) {
                    oldAccountIDOfAdvertisementCollectionAdvertisement.getAdvertisementCollection().remove(advertisementCollectionAdvertisement);
                    oldAccountIDOfAdvertisementCollectionAdvertisement = em.merge(oldAccountIDOfAdvertisementCollectionAdvertisement);
                }
            }
            for (Rating ratingCollectionRating : useraccount.getRatingCollection()) {
                Useraccount oldUseraccountOfRatingCollectionRating = ratingCollectionRating.getUseraccount();
                ratingCollectionRating.setUseraccount(useraccount);
                ratingCollectionRating = em.merge(ratingCollectionRating);
                if (oldUseraccountOfRatingCollectionRating != null) {
                    oldUseraccountOfRatingCollectionRating.getRatingCollection().remove(ratingCollectionRating);
                    oldUseraccountOfRatingCollectionRating = em.merge(oldUseraccountOfRatingCollectionRating);
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

    public void edit(Useraccount useraccount) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Useraccount persistentUseraccount = em.find(Useraccount.class, useraccount.getId());
            Preference preferenceOld = persistentUseraccount.getPreference();
            Preference preferenceNew = useraccount.getPreference();
            Collection<Notification> notificationCollectionOld = persistentUseraccount.getNotificationCollection();
            Collection<Notification> notificationCollectionNew = useraccount.getNotificationCollection();
            Collection<Adcomment> adcommentCollectionOld = persistentUseraccount.getAdcommentCollection();
            Collection<Adcomment> adcommentCollectionNew = useraccount.getAdcommentCollection();
            Collection<Advertisement> advertisementCollectionOld = persistentUseraccount.getAdvertisementCollection();
            Collection<Advertisement> advertisementCollectionNew = useraccount.getAdvertisementCollection();
            Collection<Rating> ratingCollectionOld = persistentUseraccount.getRatingCollection();
            Collection<Rating> ratingCollectionNew = useraccount.getRatingCollection();
            List<String> illegalOrphanMessages = null;
            if (preferenceOld != null && !preferenceOld.equals(preferenceNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Preference " + preferenceOld + " since its useraccount field is not nullable.");
            }
            for (Notification notificationCollectionOldNotification : notificationCollectionOld) {
                if (!notificationCollectionNew.contains(notificationCollectionOldNotification)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notification " + notificationCollectionOldNotification + " since its accountID field is not nullable.");
                }
            }
            for (Adcomment adcommentCollectionOldAdcomment : adcommentCollectionOld) {
                if (!adcommentCollectionNew.contains(adcommentCollectionOldAdcomment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Adcomment " + adcommentCollectionOldAdcomment + " since its accountID field is not nullable.");
                }
            }
            for (Advertisement advertisementCollectionOldAdvertisement : advertisementCollectionOld) {
                if (!advertisementCollectionNew.contains(advertisementCollectionOldAdvertisement)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Advertisement " + advertisementCollectionOldAdvertisement + " since its accountID field is not nullable.");
                }
            }
            for (Rating ratingCollectionOldRating : ratingCollectionOld) {
                if (!ratingCollectionNew.contains(ratingCollectionOldRating)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rating " + ratingCollectionOldRating + " since its useraccount field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (preferenceNew != null) {
                preferenceNew = em.getReference(preferenceNew.getClass(), preferenceNew.getAccountID());
                useraccount.setPreference(preferenceNew);
            }
            Collection<Notification> attachedNotificationCollectionNew = new ArrayList<Notification>();
            for (Notification notificationCollectionNewNotificationToAttach : notificationCollectionNew) {
                notificationCollectionNewNotificationToAttach = em.getReference(notificationCollectionNewNotificationToAttach.getClass(), notificationCollectionNewNotificationToAttach.getId());
                attachedNotificationCollectionNew.add(notificationCollectionNewNotificationToAttach);
            }
            notificationCollectionNew = attachedNotificationCollectionNew;
            useraccount.setNotificationCollection(notificationCollectionNew);
            Collection<Adcomment> attachedAdcommentCollectionNew = new ArrayList<Adcomment>();
            for (Adcomment adcommentCollectionNewAdcommentToAttach : adcommentCollectionNew) {
                adcommentCollectionNewAdcommentToAttach = em.getReference(adcommentCollectionNewAdcommentToAttach.getClass(), adcommentCollectionNewAdcommentToAttach.getId());
                attachedAdcommentCollectionNew.add(adcommentCollectionNewAdcommentToAttach);
            }
            adcommentCollectionNew = attachedAdcommentCollectionNew;
            useraccount.setAdcommentCollection(adcommentCollectionNew);
            Collection<Advertisement> attachedAdvertisementCollectionNew = new ArrayList<Advertisement>();
            for (Advertisement advertisementCollectionNewAdvertisementToAttach : advertisementCollectionNew) {
                advertisementCollectionNewAdvertisementToAttach = em.getReference(advertisementCollectionNewAdvertisementToAttach.getClass(), advertisementCollectionNewAdvertisementToAttach.getId());
                attachedAdvertisementCollectionNew.add(advertisementCollectionNewAdvertisementToAttach);
            }
            advertisementCollectionNew = attachedAdvertisementCollectionNew;
            useraccount.setAdvertisementCollection(advertisementCollectionNew);
            Collection<Rating> attachedRatingCollectionNew = new ArrayList<Rating>();
            for (Rating ratingCollectionNewRatingToAttach : ratingCollectionNew) {
                ratingCollectionNewRatingToAttach = em.getReference(ratingCollectionNewRatingToAttach.getClass(), ratingCollectionNewRatingToAttach.getRatingPK());
                attachedRatingCollectionNew.add(ratingCollectionNewRatingToAttach);
            }
            ratingCollectionNew = attachedRatingCollectionNew;
            useraccount.setRatingCollection(ratingCollectionNew);
            useraccount = em.merge(useraccount);
            if (preferenceNew != null && !preferenceNew.equals(preferenceOld)) {
                Useraccount oldUseraccountOfPreference = preferenceNew.getUseraccount();
                if (oldUseraccountOfPreference != null) {
                    oldUseraccountOfPreference.setPreference(null);
                    oldUseraccountOfPreference = em.merge(oldUseraccountOfPreference);
                }
                preferenceNew.setUseraccount(useraccount);
                preferenceNew = em.merge(preferenceNew);
            }
            for (Notification notificationCollectionNewNotification : notificationCollectionNew) {
                if (!notificationCollectionOld.contains(notificationCollectionNewNotification)) {
                    Useraccount oldAccountIDOfNotificationCollectionNewNotification = notificationCollectionNewNotification.getAccountID();
                    notificationCollectionNewNotification.setAccountID(useraccount);
                    notificationCollectionNewNotification = em.merge(notificationCollectionNewNotification);
                    if (oldAccountIDOfNotificationCollectionNewNotification != null && !oldAccountIDOfNotificationCollectionNewNotification.equals(useraccount)) {
                        oldAccountIDOfNotificationCollectionNewNotification.getNotificationCollection().remove(notificationCollectionNewNotification);
                        oldAccountIDOfNotificationCollectionNewNotification = em.merge(oldAccountIDOfNotificationCollectionNewNotification);
                    }
                }
            }
            for (Adcomment adcommentCollectionNewAdcomment : adcommentCollectionNew) {
                if (!adcommentCollectionOld.contains(adcommentCollectionNewAdcomment)) {
                    Useraccount oldAccountIDOfAdcommentCollectionNewAdcomment = adcommentCollectionNewAdcomment.getAccountID();
                    adcommentCollectionNewAdcomment.setAccountID(useraccount);
                    adcommentCollectionNewAdcomment = em.merge(adcommentCollectionNewAdcomment);
                    if (oldAccountIDOfAdcommentCollectionNewAdcomment != null && !oldAccountIDOfAdcommentCollectionNewAdcomment.equals(useraccount)) {
                        oldAccountIDOfAdcommentCollectionNewAdcomment.getAdcommentCollection().remove(adcommentCollectionNewAdcomment);
                        oldAccountIDOfAdcommentCollectionNewAdcomment = em.merge(oldAccountIDOfAdcommentCollectionNewAdcomment);
                    }
                }
            }
            for (Advertisement advertisementCollectionNewAdvertisement : advertisementCollectionNew) {
                if (!advertisementCollectionOld.contains(advertisementCollectionNewAdvertisement)) {
                    Useraccount oldAccountIDOfAdvertisementCollectionNewAdvertisement = advertisementCollectionNewAdvertisement.getAccountID();
                    advertisementCollectionNewAdvertisement.setAccountID(useraccount);
                    advertisementCollectionNewAdvertisement = em.merge(advertisementCollectionNewAdvertisement);
                    if (oldAccountIDOfAdvertisementCollectionNewAdvertisement != null && !oldAccountIDOfAdvertisementCollectionNewAdvertisement.equals(useraccount)) {
                        oldAccountIDOfAdvertisementCollectionNewAdvertisement.getAdvertisementCollection().remove(advertisementCollectionNewAdvertisement);
                        oldAccountIDOfAdvertisementCollectionNewAdvertisement = em.merge(oldAccountIDOfAdvertisementCollectionNewAdvertisement);
                    }
                }
            }
            for (Rating ratingCollectionNewRating : ratingCollectionNew) {
                if (!ratingCollectionOld.contains(ratingCollectionNewRating)) {
                    Useraccount oldUseraccountOfRatingCollectionNewRating = ratingCollectionNewRating.getUseraccount();
                    ratingCollectionNewRating.setUseraccount(useraccount);
                    ratingCollectionNewRating = em.merge(ratingCollectionNewRating);
                    if (oldUseraccountOfRatingCollectionNewRating != null && !oldUseraccountOfRatingCollectionNewRating.equals(useraccount)) {
                        oldUseraccountOfRatingCollectionNewRating.getRatingCollection().remove(ratingCollectionNewRating);
                        oldUseraccountOfRatingCollectionNewRating = em.merge(oldUseraccountOfRatingCollectionNewRating);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Preference preferenceOrphanCheck = useraccount.getPreference();
            if (preferenceOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Useraccount (" + useraccount + ") cannot be destroyed since the Preference " + preferenceOrphanCheck + " in its preference field has a non-nullable useraccount field.");
            }
            Collection<Notification> notificationCollectionOrphanCheck = useraccount.getNotificationCollection();
            for (Notification notificationCollectionOrphanCheckNotification : notificationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Useraccount (" + useraccount + ") cannot be destroyed since the Notification " + notificationCollectionOrphanCheckNotification + " in its notificationCollection field has a non-nullable accountID field.");
            }
            Collection<Adcomment> adcommentCollectionOrphanCheck = useraccount.getAdcommentCollection();
            for (Adcomment adcommentCollectionOrphanCheckAdcomment : adcommentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Useraccount (" + useraccount + ") cannot be destroyed since the Adcomment " + adcommentCollectionOrphanCheckAdcomment + " in its adcommentCollection field has a non-nullable accountID field.");
            }
            Collection<Advertisement> advertisementCollectionOrphanCheck = useraccount.getAdvertisementCollection();
            for (Advertisement advertisementCollectionOrphanCheckAdvertisement : advertisementCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Useraccount (" + useraccount + ") cannot be destroyed since the Advertisement " + advertisementCollectionOrphanCheckAdvertisement + " in its advertisementCollection field has a non-nullable accountID field.");
            }
            Collection<Rating> ratingCollectionOrphanCheck = useraccount.getRatingCollection();
            for (Rating ratingCollectionOrphanCheckRating : ratingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Useraccount (" + useraccount + ") cannot be destroyed since the Rating " + ratingCollectionOrphanCheckRating + " in its ratingCollection field has a non-nullable useraccount field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
