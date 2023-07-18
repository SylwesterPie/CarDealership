package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.zajavka.business.dao.ServiceDAO;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.entity.ServiceEntity;

import java.util.Objects;
import java.util.Optional;

public class ServiceRepository implements ServiceDAO {
    @Override
    public Optional<ServiceEntity> findPartByServiceCode(String serviceCode) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }

            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ServiceEntity> criteriaQuery = criteriaBuilder.createQuery(ServiceEntity.class);
            Root<ServiceEntity> root = criteriaQuery.from(ServiceEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("serviceCode"), parameter));

            Query<ServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, serviceCode);
            try {
                ServiceEntity result = query.getSingleResult();
                transaction.commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                transaction.commit();
                return Optional.empty();
            }
        }
    }
}
