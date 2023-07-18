package pl.zajavka.business.management;


import pl.zajavka.infrastructure.database.entity.MechanicEntity;
import pl.zajavka.infrastructure.database.entity.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    public List<Map<String, List<String>>> prepareFirstTimePurchaseData() {
        return InputDataCache.getInputData(Keys.InputDataGroup.BUY_FIRST_TIME, this::prepareMap);
    }

    public List<Map<String, List<String>>> prepareNextTimePurchaseData() {
        return InputDataCache.getInputData(Keys.InputDataGroup.BUY_AGAIN, this::prepareMap);
    }

    private Map<String, List<String>> prepareMap(String line) {
        List<String> grouped = Arrays.stream(line.split("->")).map(String::trim).toList();
        return IntStream.iterate(0, previous -> previous + 2)
                .boxed()
                .limit(3)
                .collect(Collectors.toMap(grouped::get, i -> List.of(grouped.get(i +1).split(";"))));

    }

    public CustomerEntity buildCustomerEntity(List<String> inputData, InvoiceEntity invoice) {
        return CustomerEntity.builder()
                .name(inputData.get(0))
                .surname(inputData.get(1))
                .phone(inputData.get(2))
                .email(inputData.get(3))
                .address(
                        AddressEntity.builder()
                                .country(inputData.get(4))
                                .city(inputData.get(5))
                                .postalCode(inputData.get(6))
                                .address(inputData.get(7))
                                .build()
                )
                .invoices(Set.of(invoice))
                .build();
    }
}
