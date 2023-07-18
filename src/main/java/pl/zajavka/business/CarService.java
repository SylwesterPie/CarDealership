package pl.zajavka.business;

import lombok.AllArgsConstructor;
import pl.zajavka.business.dao.CarDAO;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.util.Optional;


@AllArgsConstructor
public class CarService {

    private final CarDAO carDAO;

    public CarToBuyEntity findCarToBuy(String vin) {
        Optional<CarToBuyEntity> carToBuyByVin = carDAO.findCarToBuyByVin(vin);
        if(carToBuyByVin.isEmpty()) {
            throw new RuntimeException("Could not find car by vin: [%s]".formatted(vin));
        }
        return carToBuyByVin.get();
    }
}
