package pl.zajavka.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.zajavka.api.dto.CarHistoryDTO;
import pl.zajavka.api.dto.CarToBuyDTO;
import pl.zajavka.api.dto.CarToServiceDTO;
import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.CarToService;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CarMapper extends OffsetDateTimeMapper{
    CarToBuyDTO mapToDTO(CarToBuy car);

    CarToServiceDTO mapToDTO(final CarToService carToService);

    @Mapping(source = "carServiceRequest", target = "carServiceRequest", qualifiedByName = "mapServiceRequest")
    CarHistoryDTO mapToDTO(CarHistory carHistory);

    @Named("mapServiceRequest")
    default List<CarHistoryDTO.ServiceRequestDTO> mapServiceRequest(List<CarHistory.CarServiceRequest> request){
        return request.stream().map(this::mapServiceRequest).toList();
    }

    @Mapping(source = "receivedDataTime", target = "receivedDataTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(source = "completedDataTime", target = "completedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    CarHistoryDTO.ServiceRequestDTO mapServiceRequest(CarHistory.CarServiceRequest carServiceRequest);
}
