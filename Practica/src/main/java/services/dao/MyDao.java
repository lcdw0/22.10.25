package services.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import services.interfaces.ICRUD;
import util.JPAConexion;

import java.util.Collections;
import java.util.List;

public class MyDao implements ICRUD {

    @Override
    public <T> List<T> getAll(String nameQuery, Class<T> clazz) {
        try (EntityManager em = JPAConexion.getEntityManager()) {
            TypedQuery<T> query = em.createNamedQuery(nameQuery, clazz);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public <T> void insert(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public <T> void update(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public <T> void delete(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(entity)); // asegura estado managed
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx.isActive()) tx.rollback();
        } finally {
            em.close();
        }
    }

    @Override
    public <T> T findById(Integer id, Class<T> clazz) {
        try (EntityManager em = JPAConexion.getEntityManager()) {
            return em.find(clazz, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
