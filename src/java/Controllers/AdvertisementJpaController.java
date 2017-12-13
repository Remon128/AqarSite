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
import Entities.Useraccount;
import Entities.Notification;
import java.util.ArrayList;
import java.util.Collection;
import Entities.Adphoto;
import Entities.Adcomment;
import Entities.Advertisement;
import Entities.Rating;
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
        if (advertisement.getNotificationCollection() == null) {
            advertisement.setNotificationCollection(new ArrayList<Notification>());
        }
        if (advertisement.getAdphotoCollection() == null) {
            advertisement.setAdphotoCollection(new ArrayList<Adphoto>());
        }
        if (advertisement.getAdcommentCollection() == null) {
            advertisement.setAdcommentCollection(new ArrayList<Adcomment>());
        }
        if (advertisement.getRatingCollection() == null) {
            advertisement.setRatingCollection(new ArrayList<Rating>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Useraccount accountID = advertisement.getAccountID();
            if (accountID != null) {
                accountID = em.getReference(accountID.getClass(), accountID.getId());
                advertisement.setAccountID(accountID);
            }
            Collection<Notification> attachedNotificationCollection = new ArrayList<Notification>();
            for (Notification notificationCollectionNotificationToAttach : advertisement.getNotificationCollection()) {
                notificationCollectionNotificationToAttach = em.getReference(notificationCollectionNotificationToAttach.getClass(), notificationCollectionNotificationToAttach.getId());
                attachedNotificationCollection.add(notificationCollectionNotificationToAttach);
            }
            advertisement.setNotificationCollection(attachedNotificationCollection);
            Collection<Adphoto> attachedAdphotoCollection = new ArrayList<Adphoto>();
            for (Adphoto adphotoCollectionAdphotoToAttach : advertisement.getAdphotoCollection()) {
                adphotoCollectionAdphotoToAttach = em.getReference(adphotoCollectionAdphotoToAttach.getClass(), adphotoCollectionAdphotoToAttach.getId());
                attachedAdphotoCollection.add(adphotoCollectionAdphotoToAttach);
            }
            advertisement.setAdphotoCollection(attachedAdphotoCollection);
            Collection<Adcomment> attachedAdcommentCollection = new ArrayList<Adcomment>();
            for (Adcomment adcommentCollectionAdcommentToAttach : advertisement.getAdcommentCollection()) {
                adcommentCollectionAdcommentToAttach = em.getReference(adcommentCollectionAdcommentToAttach.getClass(), adcommentCollectionAdcommentToAttach.getId());
                attachedAdcommentCollection.add(adcommentCollectionAdcommentToAttach);
            }
            advertisement.setAdcommentCollection(attachedAdcommentCollection);
            Collection<Rating> attachedRatingCollection = new ArrayList<Rating>();
            for (Rating ratingCollectionRatingToAttach : advertisement.getRatingCollection()) {
                ratingCollectionRatingToAttach = em.getReference(ratingCollectionRatingToAttach.getClass(), ratingCollectionRatingToAttach.getRatingPK());
                attachedRatingCollection.add(ratingCollectionRatingToAttach);
            }
            advertisement.setRatingCollection(attachedRatingCollection);
            em.persist(advertisement);
            if (accountID != null) {
                accountID.getAdvertisementCollection().add(advertisement);
                accountID = em.merge(accountID);
            }
            for (Notification notificationCollectionNotification : advertisement.getNotificationCollection()) {
                Advertisement oldAdIDOfNotificationCollectionNotification = notificationCollectionNotification.getAdID();
                notificationCollectionNotification.setAdID(advertisement);
                notificationCollectionNotification = em.merge(notificationCollectionNotification);
                if (oldAdIDOfNotificationCollectionNotification != null) {
                    oldAdIDOfNotificationCollectionNotification.getNotificationCollection().remove(notificationCollectionNotification);
                    oldAdIDOfNotificationCollectionNotification = em.merge(oldAdIDOfNotificationCollectionNotification);
                }
            }
            for (Adphoto adphotoCollectionAdphoto : advertisement.getAdphotoCollection()) {
                Advertisement oldAdIDOfAdphotoCollectionAdphoto = adphotoCollectionAdphoto.getAdID();
                adphotoCollectionAdphoto.setAdID(advertisement);
                adphotoCollectionAdphoto = em.merge(adphotoCollectionAdphoto);
                if (oldAdIDOfAdphotoCollectionAdphoto != null) {
                    oldAdIDOfAdphotoCollectionAdphoto.getAdphotoCollection().remove(adphotoCollectionAdphoto);
                    oldAdIDOfAdphotoCollectionAdphoto = em.merge(oldAdIDOfAdphotoCollectionAdphoto);
                }
            }
            for (Adcomment adcommentCollectionAdcomment : advertisement.getAdcommentCollection()) {
                Advertisement oldAdIDOfAdcommentCollectionAdcomment = adcommentCollectionAdcomment.getAdID();
                adcommentCollectionAdcomment.setAdID(advertisement);
                adcommentCollectionAdcomment = em.merge(adcommentCollectionAdcomment);
                if (oldAdIDOfAdcommentCollectionAdcomment != null) {
                    oldAdIDOfAdcommentCollectionAdcomment.getAdcommentCollection().remove(adcommentCollectionAdcomment);
                    oldAdIDOfAdcommentCollectionAdcomment = em.merge(oldAdIDOfAdcommentCollectionAdcomment);
                }
            }
            for (Rating ratingCollectionRating : advertisement.getRatingCollection()) {
                Advertisement oldAdvertisementOfRatingCollectionRating = ratingCollectionRating.getAdvertisement();
                ratingCollectionRating.setAdvertisement(advertisement);
                ratingCollectionRating = em.merge(ratingCollectionRating);
                if (oldAdvertisementOfRatingCollectionRating != null) {
                    oldAdvertisementOfRatingCollectionRating.getRatingCollection().remove(ratingCollectionRating);
                    oldAdvertisementOfRatingCollectionRating = em.merge(oldAdvertisementOfRatingCollectionRating);
                }
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

    public void edit(Advertisement advertisement) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Advertisement persistentAdvertisement = em.find(Advertisement.class, advertisement.getId());
            Useraccount accountIDOld = persistentAdvertisement.getAccountID();
            Useraccount accountIDNew = advertisement.getAccountID();
            Collection<Notification> notificationCollectionOld = persistentAdvertisement.getNotificationCollection();
            Collection<Notification> notificationCollectionNew = advertisement.getNotificationCollection();
            Collection<Adphoto> adphotoCollectionOld = persistentAdvertisement.getAdphotoCollection();
            Collection<Adphoto> adphotoCollectionNew = advertisement.getAdphotoCollection();
            Collection<Adcomment> adcommentCollectionOld = persistentAdvertisement.getAdcommentCollection();
            Collection<Adcomment> adcommentCollectionNew = advertisement.getAdcommentCollection();
            Collection<Rating> ratingCollectionOld = persistentAdvertisement.getRatingCollection();
            Collection<Rating> ratingCollectionNew = advertisement.getRatingCollection();
            List<String> illegalOrphanMessages = null;
            for (Adphoto adphotoCollectionOldAdphoto : adphotoCollectionOld) {
                if (!adphotoCollectionNew.contains(adphotoCollectionOldAdphoto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Adphoto " + adphotoCollectionOldAdphoto + " since its adID field is not nullable.");
                }
            }
            for (Adcomment adcommentCollectionOldAdcomment : adcommentCollectionOld) {
                if (!adcommentCollectionNew.contains(adcommentCollectionOldAdcomment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Adcomment " + adcommentCollectionOldAdcomment + " since its adID field is not nullable.");
                }
            }
            for (Rating ratingCollectionOldRating : ratingCollectionOld) {
                if (!ratingCollectionNew.contains(ratingCollectionOldRating)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Rating " + ratingCollectionOldRating + " since its advertisement field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accountIDNew != null) {
                accountIDNew = em.getReference(accountIDNew.getClass(), accountIDNew.getId());
                advertisement.setAccountID(accountIDNew);
            }
            Collection<Notification> attachedNotificationCollectionNew = new ArrayList<Notification>();
            for (Notification notificationCollectionNewNotificationToAttach : notificationCollectionNew) {
                notificationCollectionNewNotificationToAttach = em.getReference(notificationCollectionNewNotificationToAttach.getClass(), notificationCollectionNewNotificationToAttach.getId());
                attachedNotificationCollectionNew.add(notificationCollectionNewNotificationToAttach);
            }
            notificationCollectionNew = attachedNotificationCollectionNew;
            advertisement.setNotificationCollection(notificationCollectionNew);
            Collection<Adphoto> attachedAdphotoCollectionNew = new ArrayList<Adphoto>();
            for (Adphoto adphotoCollectionNewAdphotoToAttach : adphotoCollectionNew) {
                adphotoCollectionNewAdphotoToAttach = em.getReference(adphotoCollectionNewAdphotoToAttach.getClass(), adphotoCollectionNewAdphotoToAttach.getId());
                attachedAdphotoCollectionNew.add(adphotoCollectionNewAdphotoToAttach);
            }
            adphotoCollectionNew = attachedAdphotoCollectionNew;
            advertisement.setAdphotoCollection(adphotoCollectionNew);
            Collection<Adcomment> attachedAdcommentCollectionNew = new ArrayList<Adcomment>();
            for (Adcomment adcommentCollectionNewAdcommentToAttach : adcommentCollectionNew) {
                adcommentCollectionNewAdcommentToAttach = em.getReference(adcommentCollectionNewAdcommentToAttach.getClass(), adcommentCollectionNewAdcommentToAttach.getId());
                attachedAdcommentCollectionNew.add(adcommentCollectionNewAdcommentToAttach);
            }
            adcommentCollectionNew = attachedAdcommentCollectionNew;
            advertisement.setAdcommentCollection(adcommentCollectionNew);
            Collection<Rating> attachedRatingCollectionNew = new ArrayList<Rating>();
            for (Rating ratingCollectionNewRatingToAttach : ratingCollectionNew) {
                ratingCollectionNewRatingToAttach = em.getReference(ratingCollectionNewRatingToAttach.getClass(), ratingCollectionNewRatingToAttach.getRatingPK());
                attachedRatingCollectionNew.add(ratingCollectionNewRatingToAttach);
            }
            ratingCollectionNew = attachedRatingCollectionNew;
            advertisement.setRatingCollection(ratingCollectionNew);
            advertisement = em.merge(advertisement);
            if (accountIDOld != null && !accountIDOld.equals(accountIDNew)) {
                accountIDOld.getAdvertisementCollection().remove(advertisement);
                accountIDOld = em.merge(accountIDOld);
            }
            if (accountIDNew != null && !accountIDNew.equals(accountIDOld)) {
                accountIDNew.getAdvertisementCollection().add(advertisement);
                accountIDNew = em.merge(accountIDNew);
            }
            for (Notification notificationCollectionOldNotification : notificationCollectionOld) {
                if (!notificationCollectionNew.contains(notificationCollectionOldNotification)) {
                    notificationCollectionOldNotification.setAdID(null);
                    notificationCollectionOldNotification = em.merge(notificationCollectionOldNotification);
                }
            }
            for (Notification notificationCollectionNewNotification : notificationCollectionNew) {
                if (!notificationCollectionOld.contains(notificationCollectionNewNotification)) {
                    Advertisement oldAdIDOfNotificationCollectionNewNotification = notificationCollectionNewNotification.getAdID();
                    notificationCollectionNewNotification.setAdID(advertisement);
                    notificationCollectionNewNotification = em.merge(notificationCollectionNewNotification);
                    if (oldAdIDOfNotificationCollectionNewNotification != null && !oldAdIDOfNotificationCollectionNewNotification.equals(advertisement)) {
                        oldAdIDOfNotificationCollectionNewNotification.getNotificationCollection().remove(notificationCollectionNewNotification);
                        oldAdIDOfNotificationCollectionNewNotification = em.merge(oldAdIDOfNotificationCollectionNewNotification);
                    }
                }
            }
            for (Adphoto adphotoCollectionNewAdphoto : adphotoCollectionNew) {
                if (!adphotoCollectionOld.contains(adphotoCollectionNewAdphoto)) {
                    Advertisement oldAdIDOfAdphotoCollectionNewAdphoto = adphotoCollectionNewAdphoto.getAdID();
                    adphotoCollectionNewAdphoto.setAdID(advertisement);
                    adphotoCollectionNewAdphoto = em.merge(adphotoCollectionNewAdphoto);
                    if (oldAdIDOfAdphotoCollectionNewAdphoto != null && !oldAdIDOfAdphotoCollectionNewAdphoto.equals(advertisement)) {
                        oldAdIDOfAdphotoCollectionNewAdphoto.getAdphotoCollection().remove(adphotoCollectionNewAdphoto);
                        oldAdIDOfAdphotoCollectionNewAdphoto = em.merge(oldAdIDOfAdphotoCollectionNewAdphoto);
                    }
                }
            }
            for (Adcomment adcommentCollectionNewAdcomment : adcommentCollectionNew) {
                if (!adcommentCollectionOld.contains(adcommentCollectionNewAdcomment)) {
                    Advertisement oldAdIDOfAdcommentCollectionNewAdcomment = adcommentCollectionNewAdcomment.getAdID();
                    adcommentCollectionNewAdcomment.setAdID(advertisement);
                    adcommentCollectionNewAdcomment = em.merge(adcommentCollectionNewAdcomment);
                    if (oldAdIDOfAdcommentCollectionNewAdcomment != null && !oldAdIDOfAdcommentCollectionNewAdcomment.equals(advertisement)) {
                        oldAdIDOfAdcommentCollectionNewAdcomment.getAdcommentCollection().remove(adcommentCollectionNewAdcomment);
                        oldAdIDOfAdcommentCollectionNewAdcomment = em.merge(oldAdIDOfAdcommentCollectionNewAdcomment);
                    }
                }
            }
            for (Rating ratingCollectionNewRating : ratingCollectionNew) {
                if (!ratingCollectionOld.contains(ratingCollectionNewRating)) {
                    Advertisement oldAdvertisementOfRatingCollectionNewRating = ratingCollectionNewRating.getAdvertisement();
                    ratingCollectionNewRating.setAdvertisement(advertisement);
                    ratingCollectionNewRating = em.merge(ratingCollectionNewRating);
                    if (oldAdvertisementOfRatingCollectionNewRating != null && !oldAdvertisementOfRatingCollectionNewRating.equals(advertisement)) {
                        oldAdvertisementOfRatingCollectionNewRating.getRatingCollection().remove(ratingCollectionNewRating);
                        oldAdvertisementOfRatingCollectionNewRating = em.merge(oldAdvertisementOfRatingCollectionNewRating);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Collection<Adphoto> adphotoCollectionOrphanCheck = advertisement.getAdphotoCollection();
            for (Adphoto adphotoCollectionOrphanCheckAdphoto : adphotoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Advertisement (" + advertisement + ") cannot be destroyed since the Adphoto " + adphotoCollectionOrphanCheckAdphoto + " in its adphotoCollection field has a non-nullable adID field.");
            }
            Collection<Adcomment> adcommentCollectionOrphanCheck = advertisement.getAdcommentCollection();
            for (Adcomment adcommentCollectionOrphanCheckAdcomment : adcommentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Advertisement (" + advertisement + ") cannot be destroyed since the Adcomment " + adcommentCollectionOrphanCheckAdcomment + " in its adcommentCollection field has a non-nullable adID field.");
            }
            Collection<Rating> ratingCollectionOrphanCheck = advertisement.getRatingCollection();
            for (Rating ratingCollectionOrphanCheckRating : ratingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Advertisement (" + advertisement + ") cannot be destroyed since the Rating " + ratingCollectionOrphanCheckRating + " in its ratingCollection field has a non-nullable advertisement field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Useraccount accountID = advertisement.getAccountID();
            if (accountID != null) {
                accountID.getAdvertisementCollection().remove(advertisement);
                accountID = em.merge(accountID);
            }
            Collection<Notification> notificationCollection = advertisement.getNotificationCollection();
            for (Notification notificationCollectionNotification : notificationCollection) {
                notificationCollectionNotification.setAdID(null);
                notificationCollectionNotification = em.merge(notificationCollectionNotification);
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
