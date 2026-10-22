package org.example;

import org.example.dao.CustomerDAO;
import org.example.domain.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
private  static CustomerDAO customerDAO = new CustomerDAO(MySessionFactory.getSessionFactory());

    static void main() {
     Customer customer = customerDAO.createNewCustomer();



    }
}
