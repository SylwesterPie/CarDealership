package pl.zajavka.business.management;


import lombok.experimental.UtilityClass;
import pl.zajavka.infrastructure.database.entity.*;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class InputDataMapper {
    public static SalesmanEntity mapSalesman(String line) {
        List<String> arguments = DataManipulationUtil.dataAsList(line);
        return SalesmanEntity.builder()
                .name(arguments.get(0))
                .surname(arguments.get(1))
                .pesel(arguments.get(2))
                .build();
    }

    public static MechanicEntity mapMechanic(String line) {
        List<String> arguments = DataManipulationUtil.dataAsList(line);
        return MechanicEntity.builder()
                .name(arguments.get(0))
                .surname(arguments.get(1))
                .pesel(arguments.get(2))
                .build();
    }

    public static CarToBuyEntity mapCar(String line) {
        List<String> arguments = DataManipulationUtil.dataAsList(line);
        return CarToBuyEntity.builder()
                .vin(arguments.get(0))
                .brand(arguments.get(1))
                .model(arguments.get(2))
                .year(Integer.parseInt(arguments.get(3)))
                .color(arguments.get(4))
                .price(new BigDecimal(arguments.get(5)))
                .build();
    }

    public static ServiceEntity mapService(String line) {
        List<String> arguments = DataManipulationUtil.dataAsList(line);
        return ServiceEntity.builder()
                .serviceCode(arguments.get(0))
                .description(arguments.get(1))
                .price(new BigDecimal(arguments.get(2)))
                .build();
    }

    public static PartEntity mapPart(String line) {
        List<String> arguments = DataManipulationUtil.dataAsList(line);
        return PartEntity.builder()
                .serialNumber(arguments.get(0))
                .description(arguments.get(1))
                .price(new BigDecimal(arguments.get(2)))
                .build();
    }
}
