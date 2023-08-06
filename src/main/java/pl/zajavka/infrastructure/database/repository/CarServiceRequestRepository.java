package pl.zajavka.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.CarServiceRequestDAO;
import pl.zajavka.domain.CarServiceRequest;
import pl.zajavka.infrastructure.database.repository.jpa.CarServiceRequestJpaRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CarServiceRequestRepository implements CarServiceRequestDAO {

    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final CarServiceRequestEntityMapper carServiceRequestEntityMapper;

    @Override
    public Set<CarServiceRequest> findActiveServiceRequestByCarVin(String carVin) {
        return carServiceRequestJpaRepository.findActiveServiceRequestByCarVin(carVin).stream()
                .map(obj -> carServiceRequestEntityMapper.mapFromEntity(obj))
                .collect(Collectors.toSet());
        }
    }

