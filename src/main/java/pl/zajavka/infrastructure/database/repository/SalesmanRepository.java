package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.zajavka.business.dao.SalesmanDAO;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.entity.SalesmanEntity;

import java.util.Objects;
import java.util.Optional;

public class SalesmanRepository implements SalesmanDAO {
    @Override
    public Optional<SalesmanEntity> findSalesmanByPesel(String pesel) {
        try (Session session = HibernateUtil.getSession()) {
            if(Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }

            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<SalesmanEntity> criteriaQuery = criteriaBuilder.createQuery(SalesmanEntity.class);
            Root<SalesmanEntity> root = criteriaQuery.from(SalesmanEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("pesel"), parameter));

            Query<SalesmanEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, pesel);
            try {
                SalesmanEntity result = query.getSingleResult();
                transaction.commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                transaction.commit();
                return Optional.empty();
            }
        }
    }
}
