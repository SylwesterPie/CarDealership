package pl.zajavka.business;

import lombok.AllArgsConstructor;
import pl.zajavka.business.dao.CarServiceRequestDAO;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.domain.CarServiceRequest;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CarServiceRequestService {

    private final FileDataPreparationService fileDataPreparationService;
    private final CarService carService;
    private final CustomerService customerService;
    private final CarServiceRequestDAO carServiceRequestDAO;

    public void requestService() {
        Map<Boolean, List<CarServiceRequest>> serviceRequest = fileDataPreparationService.createCarServiceRequest().stream()
                .collect(Collectors.groupingBy(element -> element.getCar().shouldExistInCarToBuy()));


        serviceRequest.get(true).forEach(this::saveServiceRequestForExistingCar);
        serviceRequest.get(false).forEach(this::saveServiceRequestForNewCar);
    }

    private void saveServiceRequestForExistingCar(CarServiceRequest request) {
        CarToServiceEntity car = carService.findCarToService(request.getCar().getVin())
                .orElse(findCarToBuyAndSaveInCarToService(request.getCar()));
        CustomerEntity customer = customerService.findCustomer(request.getCustomer().getEmail());

        CarServiceRequestEntity carServiceRequestEntity = buildCarServiceRequestEntity(request, car, customer);
        customer.addServiceRequest(carServiceRequestEntity);
        customerService.saveServiceRequest(customer);

    }

    private CarToServiceEntity findCarToBuyAndSaveInCarToService(CarServiceRequest.Car car) {
        CarToBuyEntity carToBuy = carService.findCarToBuy(car.getVin());
        return carService.saveCarToService(carToBuy);
    }

    private CarServiceRequestEntity buildCarServiceRequestEntity(
            CarServiceRequest request, CarToServiceEntity car, CustomerEntity customer
    ) {
        OffsetDateTime when = OffsetDateTime.now();
        return CarServiceRequestEntity.builder()
                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
                .receivedDateTime(when)
                .customerComment(request.getCustomerComment())
                .customer(customer)
                .carToService(car)
                .build();
    }

    private void saveServiceRequestForNewCar(CarServiceRequest request) {
        CarToServiceEntity car = carService.saveCarToService(request.getCar());
        CustomerEntity customer = customerService.saveCustomer(request.getCustomer());

        CarServiceRequestEntity carServiceRequestEntity = buildCarServiceRequestEntity(request, car, customer);
        customer.addServiceRequest(carServiceRequestEntity);
        customerService.saveServiceRequest(customer);
    }

    private String generateCarServiceRequestNumber(OffsetDateTime when) {
        return "%s.%s.%s-%s.%s.%s.%s".formatted(
                when.getYear(),
                when.getMonth().ordinal(),
                when.getDayOfMonth(),
                when.getHour(),
                when.getMinute(),
                when.getSecond(),
                randomInt(10,100)
        );
    }

    @SuppressWarnings("SameParameterValue")
    private int randomInt(int min, int max) {
        return new Random().nextInt(max-min)+min;
    }

    public CarServiceRequestEntity findAnyActiveServiceRequest(String carVin) {
        Set<CarServiceRequestEntity> serviceRequest = carServiceRequestDAO.findActiveServiceRequestByCarVin(carVin);
        if (serviceRequest.size() != 1) {
            throw new RuntimeException(
                    "There should be only active service request at a time, car vin: [%s]".formatted(carVin)
            );
        }
        return serviceRequest.stream().findAny()
                .orElseThrow(() -> new RuntimeException(
                        "Cloud not find any service request, car vin: [%s]".formatted(carVin)
                ));

    }
}
