package org.example.dao;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class GenericlDAO<T> {
    private  Class<T> clazz;

    private SessionFactory sessionFactory;

    public GenericlDAO(Class<T> clazzToSet, SessionFactory sessionFactory) {
        clazz = clazzToSet;
        this.sessionFactory = sessionFactory;
    }

    public T getById(Short id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    public List<T> getItem(int offset, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from "+clazz.getSimpleName());
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<T> findAll(){
        return sessionFactory.getCurrentSession().createQuery("from "+clazz.getSimpleName()).list();
    }

    public T save(T entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public void update(T entity) {
        sessionFactory.getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void deleteById(Short id) {
        sessionFactory.getCurrentSession().delete(getById(id));
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
