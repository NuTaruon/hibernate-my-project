package org.example.dao;

import org.example.domain.Payment;
import org.hibernate.SessionFactory;

public class PaymentDAO extends GenericlDAO<Payment>{

    public PaymentDAO(SessionFactory sessionFactory) {
        super(Payment.class, sessionFactory);
    }
}
