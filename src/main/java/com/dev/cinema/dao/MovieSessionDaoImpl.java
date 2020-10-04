package com.dev.cinema.dao;

import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.util.HibernateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
            throw new RuntimeException("Can't insert MovieSession entity", e);
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
            CriteriaQuery<MovieSession> criteriaQuery = criteriaBuilder
                                                        .createQuery(MovieSession.class);
            Root<MovieSession> root = criteriaQuery.from(MovieSession.class);
            Predicate first = criteriaBuilder.equal(root.get("movie"), movieId);
            Predicate second = criteriaBuilder.greaterThanOrEqualTo(root.get("showTime"),
                            LocalDateTime.of(date, LocalTime.of(0, 0)));
            Predicate third = criteriaBuilder.lessThan(root.get("showTime"),
                            LocalDateTime.of(date.plusDays(1), LocalTime.of(0, 0)));
            Predicate allConditions = criteriaBuilder.and(first, second, third);
            criteriaQuery.select(root).where(allConditions);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get the list of available sessions: ", e);
        }
    }
}
