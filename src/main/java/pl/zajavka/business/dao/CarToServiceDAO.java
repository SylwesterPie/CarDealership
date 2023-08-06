package pl.zajavka.business.dao;

import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToService;

import java.util.Optional;

public interface CarToServiceDAO {

    CarToService saveCarToService(CarToService entity);

    Optional<CarToService> findCarToServiceByVin(String vin);

    CarHistory findCarHistoryByVin(String vin);
}
