package org.example.dao;

import org.example.domain.Customer;
import org.example.domain.Inventory;
import org.example.domain.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;


public class RentalDAO extends GenericlDAO<Rental>{
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

   public Rental getAnyUnreturnedRental(Customer customer, Inventory inventory) {

            Query<Rental> rentalQuery = getCurrentSession().createQuery("select r from Rental r where r.customer = :customer and r.inventory = :inventory", Rental.class);
            rentalQuery.setParameter("customer", customer);
            rentalQuery.setParameter("inventory", inventory);
            Rental rental = rentalQuery.uniqueResult();

            return rental;

   }

   public void returnRental(Customer customer, Inventory inventory) {
        try (Session session = getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Rental rental = getAnyUnreturnedRental(customer, inventory);
            rental.setReturnDate(LocalDateTime.now());
            update(rental);
            transaction.commit();
            System.out.println("Покупатель "
                    + customer.getFirstName() + " " + customer.getLastName()
                    + " пошел и вернул ранее арендованный фильм");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Упала");
        }
   }
}
