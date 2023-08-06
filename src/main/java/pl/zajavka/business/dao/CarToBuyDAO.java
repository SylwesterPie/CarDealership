package pl.zajavka.business.dao;

import pl.zajavka.domain.CarToBuy;

import java.util.Optional;

public interface CarToBuyDAO {
    Optional<CarToBuy> findCarToBuyByVin(String vin);
}
