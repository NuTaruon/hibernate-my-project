package org.example;

import org.example.dao.CustomerDAO;
import org.example.dao.FilmDAO;
import org.example.dao.InventoryDAO;
import org.example.dao.RentalDAO;
import org.example.domain.Customer;
import org.example.domain.Film;
import org.example.domain.Inventory;
import org.example.domain.Rental;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.management.Query;
import java.time.LocalDateTime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
private  static CustomerDAO customerDAO = new CustomerDAO(MySessionFactory.getSessionFactory());
private   static FilmDAO filmDAO = new FilmDAO(MySessionFactory.getSessionFactory());
private static RentalDAO rentalDAO = new RentalDAO(MySessionFactory.getSessionFactory());
private static InventoryDAO inventoryDAO = new InventoryDAO(MySessionFactory.getSessionFactory());

    static void main() {

         Customer customer = customerDAO.getById((short)1);
         Inventory inventory = inventoryDAO.getById((short)4020);
         rentalDAO.returnRental(customer, inventory);

    }
}
