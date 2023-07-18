package pl.zajavka.business.management;


import pl.zajavka.infrastructure.database.entity.MechanicEntity;
import pl.zajavka.infrastructure.database.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class FileDataPreparationService {


    public List<?> prepareInitData() {
        List<SalesmanEntity> salesmen = InputDataCache
                .getInputData(Keys.InputDataGroup.INIT, Keys.Entity.SALESMAN, InputDataMapper::mapSalesman);
        List<MechanicEntity> mechanics = InputDataCache
                .getInputData(Keys.InputDataGroup.INIT, Keys.Entity.MECHANIC, InputDataMapper::mapMechanic);
        List<CarToBuyEntity> cars = InputDataCache
                .getInputData(Keys.InputDataGroup.INIT, Keys.Entity.CAR, InputDataMapper::mapCar);
        List<ServiceEntity> services = InputDataCache
                .getInputData(Keys.InputDataGroup.INIT, Keys.Entity.SERVICE, InputDataMapper::mapService);
        List<PartEntity> parts = InputDataCache
                .getInputData(Keys.InputDataGroup.INIT, Keys.Entity.PART, InputDataMapper::mapPart);

        return Stream.of(salesmen, mechanics, cars, services, parts)
                .flatMap(Collection::stream)
                .toList();
    }
}
