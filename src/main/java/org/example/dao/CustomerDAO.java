package org.example.dao;

import org.example.domain.Address;
import org.example.domain.City;
import org.example.domain.Customer;
import org.example.domain.Store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CustomerDAO extends GenericlDAO<Customer> {

    public CustomerDAO(SessionFactory sessionFactory) {
      super(Customer.class, sessionFactory);
    }

    public Customer createNewCustomer() {
        try (Session session = getCurrentSession()){
            StoreDAO storeDAO = new StoreDAO(session.getSessionFactory());
            CityDAO cityDAO = new CityDAO(session.getSessionFactory());
            AddressDAO addressDAO = new AddressDAO(session.getSessionFactory());

            Transaction transaction = session.beginTransaction();
            Store store = storeDAO.getItem(0,1).get(0);
            City city = cityDAO.getByName("Akishima");

            Address address = new  Address();
            address.setCity(city);
            address.setAddress("568 Alaba str,");
            address.setDistrict("Guaga");
            address.setPhone("79564");
            addressDAO.save(address);

            Customer newCustomer = new Customer();
            newCustomer.setFirstName("FirstName");
            newCustomer.setLastName("LastName");
            newCustomer.setStore(store);
            newCustomer.setAddress(address);
            newCustomer.setActive(true);
            newCustomer.setEmail("email_test");
            save(newCustomer);
            transaction.commit();
            return newCustomer;
        }
    }
}
