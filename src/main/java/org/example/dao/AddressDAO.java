package org.example.dao;

import org.example.domain.Address;
import org.hibernate.SessionFactory;

public class AddressDAO extends GenericlDAO<Address> {

    public AddressDAO(SessionFactory sessionFactory) {
        super(Address.class, sessionFactory);
    }
}
