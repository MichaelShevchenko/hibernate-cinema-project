package com.dev.cinema.dao;

import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.util.HibernateUtil;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    private static final Logger LOGGER = Logger.getLogger(ShoppingCartDaoImpl.class);

    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(shoppingCart);
            transaction.commit();
            LOGGER.info("Successfully added new shopping cart " + shoppingCart + " to the DB.");
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert " + shoppingCart + " entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ShoppingCart getByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ShoppingCart> criteriaQuery =
                                        criteriaBuilder.createQuery(ShoppingCart.class);
            Root<ShoppingCart> root = criteriaQuery.from(ShoppingCart.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("user"), user.getId()));
            ShoppingCart userShoppingCart = session.createQuery(criteriaQuery).uniqueResult();
            Hibernate.initialize(userShoppingCart.getTickets());
            return userShoppingCart;
        }
    }

    @Override
    public void update(ShoppingCart shoppingCart) {
        LOGGER.info("Calling update() method to update provided shopping cart: " + shoppingCart);
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.update(shoppingCart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update " + shoppingCart + " entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
