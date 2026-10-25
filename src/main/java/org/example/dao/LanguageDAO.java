package org.example.dao;

import org.example.domain.Language;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class LanguageDAO extends GenericlDAO<Language> {

    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }

    public Language getLanguageByName(String name) {
        Query <Language> query = getCurrentSession().createQuery("select n from Language n where n.name = :NAME");
        query.setParameter("NAME", name);
        return (Language) query.getSingleResult();
    }
}
