package com.dev.cinema.dao;

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

@Dao
public class MovieSessionDaoImpl extends GenericDaoImpl<MovieSession> implements MovieSessionDao {
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
