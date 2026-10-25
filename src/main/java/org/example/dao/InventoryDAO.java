package org.example.dao;

import org.example.domain.Inventory;
import org.hibernate.SessionFactory;

public class InventoryDAO extends GenericlDAO<Inventory>{
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
