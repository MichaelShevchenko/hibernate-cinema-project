package com.dev.cinema.dao;

import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class OrderDaoImpl implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);

    @Override
    public Order create(Order order) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            LOGGER.info("Successfully added new order " + order + " to the DB.");
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't create " + order + " entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder
                    .createQuery(Order.class);
            Root<Order> root = criteriaQuery.from(Order.class);
            root.fetch("tickets", JoinType.LEFT);
            Predicate selectUser = criteriaBuilder.equal(root.get("user"), user.getId());
            criteriaQuery.select(root).where(selectUser).distinct(true);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
