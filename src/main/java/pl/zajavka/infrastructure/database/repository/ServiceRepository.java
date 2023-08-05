package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.ServiceDAO;
import pl.zajavka.infrastructure.database.entity.ServiceEntity;

import java.util.Optional;

@Repository
public class ServiceRepository implements ServiceDAO {
    @Override
    public Optional<ServiceEntity> findPartByServiceCode(String serviceCode) {
         
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ServiceEntity> criteriaQuery = criteriaBuilder.createQuery(ServiceEntity.class);
            Root<ServiceEntity> root = criteriaQuery.from(ServiceEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("serviceCode"), parameter));

            Query<ServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, serviceCode);


    }
}
