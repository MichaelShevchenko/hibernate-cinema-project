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
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public MovieSession add(MovieSession session) {
        Transaction transaction = null;
        Session hibernateSession = null;
        try {
            hibernateSession = HibernateUtil.getSessionFactory().openSession();
            transaction = hibernateSession.beginTransaction();
            hibernateSession.save(session);
            transaction.commit();
            return session;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert MovieSession entity", e);
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
            Predicate first = criteriaBuilder.equal(root.get("movie"), movieId);
            Predicate second = criteriaBuilder.greaterThanOrEqualTo(root
                            .get("showTime"), date.atStartOfDay());
            Predicate third = criteriaBuilder.lessThan(root
                            .get("showTime"), date.atTime(LocalTime.MAX));
            Predicate allConditions = criteriaBuilder.and(first, second, third);
            criteriaQuery.select(root).where(allConditions);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get the list of available sessions: ", e);
        }
    }
}
