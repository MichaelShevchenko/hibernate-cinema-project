package com.dev.cinema.dao;

import com.dev.cinema.exceptions.DataProcessingException;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    private static final Logger LOGGER = Logger.getLogger(MovieSessionDaoImpl.class);

    @Override
    public MovieSession add(MovieSession session) {
        Transaction transaction = null;
        Session hibernateSession = null;
        try {
            hibernateSession = HibernateUtil.getSessionFactory().openSession();
            transaction = hibernateSession.beginTransaction();
            hibernateSession.save(session);
            transaction.commit();
            LOGGER.info("Successfully added new movie session " + session + " to the DB.");
            return session;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert " + session + " entity", e);
        } finally {
            if (hibernateSession != null) {
                hibernateSession.close();
            }
        }
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MovieSession> criteriaQuery =
                            criteriaBuilder.createQuery(MovieSession.class);
            Root<MovieSession> root = criteriaQuery.from(MovieSession.class);
            Predicate moviePredicate = criteriaBuilder.equal(root.get("movie"), movieId);
            Predicate datePredicate = criteriaBuilder.between(root.get("showTime"),
                                        date.atStartOfDay(), date.atTime(LocalTime.MAX));
            Predicate allConditions = criteriaBuilder.and(moviePredicate, datePredicate);
            criteriaQuery.select(root).where(allConditions);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
