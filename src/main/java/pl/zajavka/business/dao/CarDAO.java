package pl.zajavka.business.dao;

import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;

import java.util.Optional;

public interface CarDAO {
    CarToServiceEntity saveCarToService(CarToServiceEntity entity);

    Optional<CarToBuyEntity> findCarToBuyByVin(String vin);

    Optional<CarToServiceEntity> findCarToServiceByVin(String vin);
}
