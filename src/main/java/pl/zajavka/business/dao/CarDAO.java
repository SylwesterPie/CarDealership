package pl.zajavka.business.dao;

import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.util.Optional;

public interface CarDAO {
    Optional<CarToBuyEntity> findCarToBuyByVin(String vin);
}
