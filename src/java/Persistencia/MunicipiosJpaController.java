/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Departamento;
import DTO.Municipios;
import DTO.Persona;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author madar
 */
public class MunicipiosJpaController implements Serializable {

    public MunicipiosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipios municipios) {
        if (municipios.getPersonaList() == null) {
            municipios.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento idDpto = municipios.getIdDpto();
            if (idDpto != null) {
                idDpto = em.getReference(idDpto.getClass(), idDpto.getIdDpto());
                municipios.setIdDpto(idDpto);
            }
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : municipios.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getCedula());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            municipios.setPersonaList(attachedPersonaList);
            em.persist(municipios);
            if (idDpto != null) {
                idDpto.getMunicipiosList().add(municipios);
                idDpto = em.merge(idDpto);
            }
            for (Persona personaListPersona : municipios.getPersonaList()) {
                Municipios oldIdMunicipioOfPersonaListPersona = personaListPersona.getIdMunicipio();
                personaListPersona.setIdMunicipio(municipios);
                personaListPersona = em.merge(personaListPersona);
                if (oldIdMunicipioOfPersonaListPersona != null) {
                    oldIdMunicipioOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldIdMunicipioOfPersonaListPersona = em.merge(oldIdMunicipioOfPersonaListPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipios municipios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios persistentMunicipios = em.find(Municipios.class, municipios.getIdMunicipio());
            Departamento idDptoOld = persistentMunicipios.getIdDpto();
            Departamento idDptoNew = municipios.getIdDpto();
            List<Persona> personaListOld = persistentMunicipios.getPersonaList();
            List<Persona> personaListNew = municipios.getPersonaList();
            if (idDptoNew != null) {
                idDptoNew = em.getReference(idDptoNew.getClass(), idDptoNew.getIdDpto());
                municipios.setIdDpto(idDptoNew);
            }
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getCedula());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            municipios.setPersonaList(personaListNew);
            municipios = em.merge(municipios);
            if (idDptoOld != null && !idDptoOld.equals(idDptoNew)) {
                idDptoOld.getMunicipiosList().remove(municipios);
                idDptoOld = em.merge(idDptoOld);
            }
            if (idDptoNew != null && !idDptoNew.equals(idDptoOld)) {
                idDptoNew.getMunicipiosList().add(municipios);
                idDptoNew = em.merge(idDptoNew);
            }
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    personaListOldPersona.setIdMunicipio(null);
                    personaListOldPersona = em.merge(personaListOldPersona);
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Municipios oldIdMunicipioOfPersonaListNewPersona = personaListNewPersona.getIdMunicipio();
                    personaListNewPersona.setIdMunicipio(municipios);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldIdMunicipioOfPersonaListNewPersona != null && !oldIdMunicipioOfPersonaListNewPersona.equals(municipios)) {
                        oldIdMunicipioOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldIdMunicipioOfPersonaListNewPersona = em.merge(oldIdMunicipioOfPersonaListNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipios.getIdMunicipio();
                if (findMunicipios(id) == null) {
                    throw new NonexistentEntityException("The municipios with id " + id + " no longer exists.");
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
            Municipios municipios;
            try {
                municipios = em.getReference(Municipios.class, id);
                municipios.getIdMunicipio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipios with id " + id + " no longer exists.", enfe);
            }
            Departamento idDpto = municipios.getIdDpto();
            if (idDpto != null) {
                idDpto.getMunicipiosList().remove(municipios);
                idDpto = em.merge(idDpto);
            }
            List<Persona> personaList = municipios.getPersonaList();
            for (Persona personaListPersona : personaList) {
                personaListPersona.setIdMunicipio(null);
                personaListPersona = em.merge(personaListPersona);
            }
            em.remove(municipios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipios> findMunicipiosEntities() {
        return findMunicipiosEntities(true, -1, -1);
    }

    public List<Municipios> findMunicipiosEntities(int maxResults, int firstResult) {
        return findMunicipiosEntities(false, maxResults, firstResult);
    }

    private List<Municipios> findMunicipiosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipios.class));
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

    public Municipios findMunicipios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipios.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipiosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipios> rt = cq.from(Municipios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
