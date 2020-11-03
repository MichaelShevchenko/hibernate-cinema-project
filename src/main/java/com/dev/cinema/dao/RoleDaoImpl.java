package com.dev.cinema.dao;

import com.dev.cinema.model.Role;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Role getRoleByName(String roleName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
            Root<Role> root = criteriaQuery.from(Role.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("roleName"), roleName));
            return session.createQuery(criteriaQuery).getSingleResult();
        }
    }
}
