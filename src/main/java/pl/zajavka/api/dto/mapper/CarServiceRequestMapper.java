package pl.zajavka.api.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.zajavka.api.dto.CarServiceCustomerRequestDTO;
import pl.zajavka.api.dto.CarServiceMechanicProcessingUnitDTO;
import pl.zajavka.api.dto.CarServiceRequestDTO;
import pl.zajavka.domain.*;

@Mapper(componentModel = "spring")
public interface CarServiceRequestMapper extends OffsetDateTimeMapper{
    default CarServiceRequest mapFromDTO(CarServiceCustomerRequestDTO requestDTO) {
        if (requestDTO.isNewCarCandidate()){
            return CarServiceRequest.builder()
                    .customer(Customer.builder()
                            .name(requestDTO.getCustomerName())
                            .surname(requestDTO.getCustomerSurname())
                            .phone(requestDTO.getCustomerPhone())
                            .email(requestDTO.getCustomerEmail())
                            .address(Address.builder()
                                    .country(requestDTO.getCustomerAddressCountry())
                                    .city(requestDTO.getCustomerAddressCity())
                                    .postalCode(requestDTO.getCustomerAddressPostalCode())
                                    .address(requestDTO.getCustomerAddressStreet())
                                    .build())
                            .build())
                    .car(CarToService.builder()
                            .vin(requestDTO.getCarVin())
                            .brand(requestDTO.getCarBrand())
                            .model(requestDTO.getCarModel())
                            .year(requestDTO.getCarYear())
                            .build())
                    .customerComment(requestDTO.getCustomerComment())
                    .build();
        } else {
            return CarServiceRequest.builder()
                    .customer(Customer.builder()
                            .email(requestDTO.getExistingCustomerEmail())
                            .build())
                    .car(CarToService.builder()
                            .vin(requestDTO.getExistingCarVin())
                            .build())
                    .customerComment(requestDTO.getCustomerComment())
                    .build();
        }
    }

    @Mapping(source = "car.vin", target = "carVin")
    @Mapping(source = "receivedDateTime", target = "receivedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    @Mapping(source = "completedDateTime", target = "completedDateTime", qualifiedByName = "mapOffsetDateTimeToString")
    CarServiceRequestDTO mapToDTO(CarServiceRequest request);

    @Mapping(source = "mechanicComment", target = "comment")
    CarServiceProcessingRequest mapFromDTO(CarServiceMechanicProcessingUnitDTO dto);
}
