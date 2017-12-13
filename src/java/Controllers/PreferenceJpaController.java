/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.exceptions.IllegalOrphanException;
import Controllers.exceptions.NonexistentEntityException;
import Controllers.exceptions.PreexistingEntityException;
import Entities.Preference;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entities.Useraccount;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lenovo
 */
public class PreferenceJpaController implements Serializable {

    public PreferenceJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public PreferenceJpaController() {
        emf = Persistence.createEntityManagerFactory("AqarTestPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Preference preference) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Useraccount useraccountOrphanCheck = preference.getUseraccount();
        if (useraccountOrphanCheck != null) {
            Preference oldPreferenceOfUseraccount = useraccountOrphanCheck.getPreference();
            if (oldPreferenceOfUseraccount != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Useraccount " + useraccountOrphanCheck + " already has an item of type Preference whose useraccount column cannot be null. Please make another selection for the useraccount field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Useraccount useraccount = preference.getUseraccount();
            if (useraccount != null) {
                useraccount = em.getReference(useraccount.getClass(), useraccount.getId());
                preference.setUseraccount(useraccount);
            }
            em.persist(preference);
            if (useraccount != null) {
                useraccount.setPreference(preference);
                useraccount = em.merge(useraccount);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreference(preference.getAccountID()) != null) {
                throw new PreexistingEntityException("Preference " + preference + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Preference preference) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preference persistentPreference = em.find(Preference.class, preference.getAccountID());
            Useraccount useraccountOld = persistentPreference.getUseraccount();
            Useraccount useraccountNew = preference.getUseraccount();
            List<String> illegalOrphanMessages = null;
            if (useraccountNew != null && !useraccountNew.equals(useraccountOld)) {
                Preference oldPreferenceOfUseraccount = useraccountNew.getPreference();
                if (oldPreferenceOfUseraccount != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Useraccount " + useraccountNew + " already has an item of type Preference whose useraccount column cannot be null. Please make another selection for the useraccount field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (useraccountNew != null) {
                useraccountNew = em.getReference(useraccountNew.getClass(), useraccountNew.getId());
                preference.setUseraccount(useraccountNew);
            }
            preference = em.merge(preference);
            if (useraccountOld != null && !useraccountOld.equals(useraccountNew)) {
                useraccountOld.setPreference(null);
                useraccountOld = em.merge(useraccountOld);
            }
            if (useraccountNew != null && !useraccountNew.equals(useraccountOld)) {
                useraccountNew.setPreference(preference);
                useraccountNew = em.merge(useraccountNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preference.getAccountID();
                if (findPreference(id) == null) {
                    throw new NonexistentEntityException("The preference with id " + id + " no longer exists.");
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
            Preference preference;
            try {
                preference = em.getReference(Preference.class, id);
                preference.getAccountID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preference with id " + id + " no longer exists.", enfe);
            }
            Useraccount useraccount = preference.getUseraccount();
            if (useraccount != null) {
                useraccount.setPreference(null);
                useraccount = em.merge(useraccount);
            }
            em.remove(preference);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preference> findPreferenceEntities() {
        return findPreferenceEntities(true, -1, -1);
    }

    public List<Preference> findPreferenceEntities(int maxResults, int firstResult) {
        return findPreferenceEntities(false, maxResults, firstResult);
    }

    private List<Preference> findPreferenceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preference.class));
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

    public Preference findPreference(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preference.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preference> rt = cq.from(Preference.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
