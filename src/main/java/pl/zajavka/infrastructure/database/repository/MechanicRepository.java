package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.MechanicDAO;
import pl.zajavka.infrastructure.database.entity.MechanicEntity;

import java.util.Optional;

@Repository
public class MechanicRepository implements MechanicDAO {
    @Override
    public Optional<MechanicEntity> findMechanicByPesel(String pesel) {
         
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MechanicEntity> criteriaQuery = criteriaBuilder.createQuery(MechanicEntity.class);
            Root<MechanicEntity> root = criteriaQuery.from(MechanicEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("pesel"), parameter));

            Query<MechanicEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, pesel);
            try {
                MechanicEntity result = query.getSingleResult();
                transaction.commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                transaction.commit();
                return Optional.empty();
            }
        }
    }
}
