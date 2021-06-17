/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DTO.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Municipios;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author madar
 */
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) throws PreexistingEntityException, Exception {
        if (departamento.getMunicipiosList() == null) {
            departamento.setMunicipiosList(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Municipios> attachedMunicipiosList = new ArrayList<Municipios>();
            for (Municipios municipiosListMunicipiosToAttach : departamento.getMunicipiosList()) {
                municipiosListMunicipiosToAttach = em.getReference(municipiosListMunicipiosToAttach.getClass(), municipiosListMunicipiosToAttach.getIdMunicipio());
                attachedMunicipiosList.add(municipiosListMunicipiosToAttach);
            }
            departamento.setMunicipiosList(attachedMunicipiosList);
            em.persist(departamento);
            for (Municipios municipiosListMunicipios : departamento.getMunicipiosList()) {
                Departamento oldIdDptoOfMunicipiosListMunicipios = municipiosListMunicipios.getIdDpto();
                municipiosListMunicipios.setIdDpto(departamento);
                municipiosListMunicipios = em.merge(municipiosListMunicipios);
                if (oldIdDptoOfMunicipiosListMunicipios != null) {
                    oldIdDptoOfMunicipiosListMunicipios.getMunicipiosList().remove(municipiosListMunicipios);
                    oldIdDptoOfMunicipiosListMunicipios = em.merge(oldIdDptoOfMunicipiosListMunicipios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDepartamento(departamento.getIdDpto()) != null) {
                throw new PreexistingEntityException("Departamento " + departamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIdDpto());
            List<Municipios> municipiosListOld = persistentDepartamento.getMunicipiosList();
            List<Municipios> municipiosListNew = departamento.getMunicipiosList();
            List<Municipios> attachedMunicipiosListNew = new ArrayList<Municipios>();
            for (Municipios municipiosListNewMunicipiosToAttach : municipiosListNew) {
                municipiosListNewMunicipiosToAttach = em.getReference(municipiosListNewMunicipiosToAttach.getClass(), municipiosListNewMunicipiosToAttach.getIdMunicipio());
                attachedMunicipiosListNew.add(municipiosListNewMunicipiosToAttach);
            }
            municipiosListNew = attachedMunicipiosListNew;
            departamento.setMunicipiosList(municipiosListNew);
            departamento = em.merge(departamento);
            for (Municipios municipiosListOldMunicipios : municipiosListOld) {
                if (!municipiosListNew.contains(municipiosListOldMunicipios)) {
                    municipiosListOldMunicipios.setIdDpto(null);
                    municipiosListOldMunicipios = em.merge(municipiosListOldMunicipios);
                }
            }
            for (Municipios municipiosListNewMunicipios : municipiosListNew) {
                if (!municipiosListOld.contains(municipiosListNewMunicipios)) {
                    Departamento oldIdDptoOfMunicipiosListNewMunicipios = municipiosListNewMunicipios.getIdDpto();
                    municipiosListNewMunicipios.setIdDpto(departamento);
                    municipiosListNewMunicipios = em.merge(municipiosListNewMunicipios);
                    if (oldIdDptoOfMunicipiosListNewMunicipios != null && !oldIdDptoOfMunicipiosListNewMunicipios.equals(departamento)) {
                        oldIdDptoOfMunicipiosListNewMunicipios.getMunicipiosList().remove(municipiosListNewMunicipios);
                        oldIdDptoOfMunicipiosListNewMunicipios = em.merge(oldIdDptoOfMunicipiosListNewMunicipios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = departamento.getIdDpto();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIdDpto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<Municipios> municipiosList = departamento.getMunicipiosList();
            for (Municipios municipiosListMunicipios : municipiosList) {
                municipiosListMunicipios.setIdDpto(null);
                municipiosListMunicipios = em.merge(municipiosListMunicipios);
            }
            em.remove(departamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
