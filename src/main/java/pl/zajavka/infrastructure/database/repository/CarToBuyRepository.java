package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.CarDAO;
import pl.zajavka.business.dao.CarToBuyDAO;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.infrastructure.database.entity.*;
import pl.zajavka.infrastructure.database.repository.jpa.CarToBuyJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CarToBuyRepository implements CarToBuyDAO {

    private final CarToBuyJpaRepository carToBuyJpaRepository;
    private final CarToBuyMapper carToBuyMapper;

    @Override
    public Optional<CarToBuy> findCarToBuyByVin(String vin) {
        return carToBuyJpaRepository.findByVin(vin)
                .map(obj -> carToBuyMapper.mapFromEntity(obj));
    }


}
