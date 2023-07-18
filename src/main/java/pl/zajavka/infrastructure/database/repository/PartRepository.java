package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.zajavka.business.dao.PartDAO;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.entity.PartEntity;

import java.util.Objects;
import java.util.Optional;

public class PartRepository implements PartDAO {


    @Override
    public Optional<PartEntity> findPartBySerialNumber(String partSerialNumber) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }

            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PartEntity> criteriaQuery = criteriaBuilder.createQuery(PartEntity.class);
            Root<PartEntity> root = criteriaQuery.from(PartEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("serialNumber"), parameter));

            Query<PartEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, partSerialNumber);
            try {
                PartEntity result = query.getSingleResult();
                transaction.commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                transaction.commit();
                return Optional.empty();
            }
        }
    }
}
