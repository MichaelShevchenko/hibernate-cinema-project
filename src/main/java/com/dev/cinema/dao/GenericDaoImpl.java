package com.dev.cinema.dao;

import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GenericDaoImpl<T> implements GenericDao<T> {
    private static final Logger logger = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public T add(T item) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            logger.info("Successfully added new entity " + item + " to the DB.");
            return item;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert " + item + " entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
