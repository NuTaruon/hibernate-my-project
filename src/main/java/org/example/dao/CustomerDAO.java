package org.example.dao;

import org.example.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public void customerRentFilm(Customer customer){
        try (Session session = getCurrentSession()){
            FilmDAO filmDAO = new FilmDAO(session.getSessionFactory());
            StoreDAO storeDAO = new StoreDAO(session.getSessionFactory());
            InventoryDAO inventoryDAO = new InventoryDAO(session.getSessionFactory());
            RentalDAO rentalDAO = new RentalDAO(session.getSessionFactory());
            PaymentDAO paymentDAO = new PaymentDAO(session.getSessionFactory());
            Transaction transaction = session.beginTransaction();

            Film film = filmDAO.getFirstAvaliableFilmRent();
            Store store = storeDAO.getItem(0,1).get(0);

            Inventory inventory = new Inventory();
            inventory.setFilm(film);
            inventory.setStore(store);
            inventoryDAO.save(inventory);

            Staff staff = store.getManagerStaff();

            Rental rental = new Rental();
            rental.setStaff(staff);
            rental.setCustomer(customer);
            rental.setInventory(inventory);
            rental.setCreationDate(LocalDateTime.now());
            rentalDAO.save(rental);

            Payment payment = new Payment();
            payment.setCustomer(customer);
            payment.setRental(rental);
            payment.setAmount(BigDecimal.valueOf(35.5));
            payment.setStaff(staff);
            payment.setPaymentDate(LocalDateTime.now());
            paymentDAO.save(payment);

            transaction.commit();

            System.out.println(customer.getFirstName() + " " + customer.getLastName() +
                    " сходил в магазин на " +  store.getAddress().getAddress() +
                    " и арендовал там фильм " + inventory.getFilm().getTitle() +
                    ". При этом он сделал оплату на сумму " + payment.getAmount() +
                    " у продавца " + staff.getFirstName() + " " + staff.getLastName());
        }
    }
}
