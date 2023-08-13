package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.CarServiceRequestDAO;
import pl.zajavka.domain.*;
import pl.zajavka.domain.exception.ProcessingException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;

@AllArgsConstructor
@Service
public class CarServiceRequestService {

    private final MechanicService mechanicService;
    private final CarService carService;
    private final CustomerService customerService;
    private final CarServiceRequestDAO carServiceRequestDAO;

    public List<Mechanic> availableMechanics() {
        return mechanicService.findAvailableMechanic();
    }

    public List<CarServiceRequest> availableServiceRequest() {
        return carServiceRequestDAO.findAvailable();
    }

    @Transactional
    public void makeServiceRequest(CarServiceRequest serviceRequest) {
        if (serviceRequest.getCar().shouldExistingInCarToBuy()) {
            saveServiceRequestForExistingCar(serviceRequest);
        } else {
            saveServiceRequestForNewCar(serviceRequest);
        }

    }

    private void saveServiceRequestForExistingCar(CarServiceRequest request) {
        validate(request.getCar().getVin());

        CarToService car = carService.findCarToService(request.getCar().getVin())
                .orElse(findCarToBuyAndSaveInCarToService(request.getCar()));
        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());

        CarServiceRequest carServiceRequest = buildCarServiceRequest(request, car, customer);
        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
        existingCarServiceRequests.add(carServiceRequest);
        customer = customer.withCarServiceRequests(existingCarServiceRequests);
        customerService.saveServiceRequest(customer);
    }

    private void saveServiceRequestForNewCar(CarServiceRequest request) {
        validate(request.getCar().getVin());

        CarToService car = carService.findCarToService(request.getCar().getVin())
                .orElse(findCarToBuyAndSaveInCarToService(request.getCar()));
        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());

        CarServiceRequest carServiceRequest = buildCarServiceRequest(request, car, customer);
        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
        existingCarServiceRequests.add(carServiceRequest);
        customer = customer.withCarServiceRequests(existingCarServiceRequests);
        customerService.saveServiceRequest(customer);
    }

    private void validate(String vin) {
        Set<CarServiceRequest> activeServiceRequest = carServiceRequestDAO.findActiveServiceRequestByCarVin(vin);
        if (activeServiceRequest.size() == 1) {
            throw new ProcessingException(
                    "There should be only one active service request at a time, a car vin: [%s]".formatted(vin)
            );
        }
    }

    private CarToService findCarToBuyAndSaveInCarToService(CarToService car) {
        CarToBuy carToBuy = carService.findCarToBuy(car.getVin());
        return carService.saveCarToService(carToBuy);
    }

    private CarServiceRequest buildCarServiceRequest(
            CarServiceRequest request, CarToService car, Customer customer
    ) {
        OffsetDateTime when = OffsetDateTime.now();
        return CarServiceRequest.builder()
                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
                .receivedDateTime(when)
                .customerComment(request.getCustomerComment())
                .customer(customer)
                .car(car)
                .build();
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

    public CarServiceRequest findAnyActiveServiceRequest(String carVin) {
        Set<CarServiceRequest> serviceRequest = carServiceRequestDAO.findActiveServiceRequestByCarVin(carVin);
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
