package org.example.dao;

import org.example.domain.Store;
import org.hibernate.SessionFactory;

public class StoreDAO extends GenericlDAO<Store> {

    public StoreDAO(SessionFactory sessionFactory) {
        super(Store.class, sessionFactory);
    }

}
