package pl.zajavka.business.dao;


import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.CarToService;

import java.util.Optional;

public interface CarDAO {
    CarToService saveCarToService(CarToService entity);

    Optional<CarToBuy> findCarToBuyByVin(String vin);

    Optional<CarToService> findCarToServiceByVin(String vin);

    CarHistory findCarHistoryByVin(String vin);
}
