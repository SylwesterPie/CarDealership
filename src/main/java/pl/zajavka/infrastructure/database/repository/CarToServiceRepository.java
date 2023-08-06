package pl.zajavka.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.CarToServiceDAO;
import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToService;
import pl.zajavka.infrastructure.database.entity.*;
import pl.zajavka.infrastructure.database.repository.jpa.CarToServiceJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarToServiceRepository implements CarToServiceDAO {

    private final CarToServiceJpaRepository carToServiceJpaRepository;
    private final CarToServiceMapper carToServiceMapper;


    @Override
    public Optional<CarToService> findCarToServiceByVin(String vin) {
        return carToServiceJpaRepository.findByVin(vin).stream()
                .map(obj -> carToServiceMapper.mapFromEntity(obj));
    }

    @Override
    public CarToService saveCarToService(CarToService entity) {
        CarToServiceEntity toSave = carToServiceMapper.mapToEntity(entity);
        CarToServiceEntity save = carToServiceJpaRepository.saveAndFlush(toSave);
        return carToServiceMapper.mapFromEntity(save);
    }

    @Override
    public CarHistory findCarHistoryByVin(String vin) {
        CarToServiceEntity carHistoryByVin = carToServiceJpaRepository.findCarHistoryByVin(vin);
        return carToServiceMapper.mapFromEntity(vin, carHistoryByVin);
    }
}
