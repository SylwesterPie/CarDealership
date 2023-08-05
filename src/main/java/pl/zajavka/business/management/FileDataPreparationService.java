package pl.zajavka.business.management;


import org.springframework.stereotype.Service;
import pl.zajavka.business.management.Keys.Domain;
import pl.zajavka.domain.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FileDataPreparationService {


    public List<CarPurchaseRequestInputData> prepareFirstTimePurchaseData() {
        return InputDataCache.getInputData(Keys.InputDataGroup.BUY_FIRST_TIME, this::prepareMap).stream()
                .map(this::prepareFirstTimePurchaseData)
                .toList();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private CarPurchaseRequestInputData prepareFirstTimePurchaseData(Map<String, List<String>> inputData) {
        List<String> customerData = inputData.get(Domain.CUSTOMER);
        return CarPurchaseRequestInputData.builder()
                .customerName(customerData.get(0))
                .customerSurname(customerData.get(1))
                .customerPhone(customerData.get(2))
                .customerEmail(customerData.get(3))
                .customerAddressCountry(customerData.get(4))
                .customerAddressCity(customerData.get(5))
                .customerAddressPostalCode(customerData.get(6))
                .customerAddressStreet(customerData.get(7))
                .carVin(inputData.get(Domain.CAR).get(0))
                .salesmanPesel(inputData.get(Domain.SALESMAN).get(0))
                .build();
    }

    public List<CarPurchaseRequestInputData> prepareNextTimePurchaseData() {
        return InputDataCache.getInputData(Keys.InputDataGroup.BUY_AGAIN, this::prepareMap).stream()
                .map(this::prepareNextTimePurchaseData)
                .toList();
    }


    @SuppressWarnings("SuspiciousMethodCalls")
    private CarPurchaseRequestInputData prepareNextTimePurchaseData(Map<String, List<String>> inputData) {
        return CarPurchaseRequestInputData.builder()
                .customerEmail(inputData.get(Domain.CUSTOMER).get(0))
                .carVin(inputData.get(Domain.CAR).get(0))
                .salesmanPesel(inputData.get(Domain.SALESMAN).get(0))
                .build();
    }

    public Customer buildCustomer(CarPurchaseRequestInputData inputData, Invoice invoice) {
        return Customer.builder()
                .name(inputData.getCustomerName())
                .surname(inputData.getCustomerSurname())
                .phone(inputData.getCustomerPhone())
                .email(inputData.getCustomerEmail())
                .address(Address.builder().
                        country(inputData.getCustomerAddressCountry()).
                        city(inputData.getCustomerAddressCity()).
                        postalCode(inputData.getCustomerAddressPostalCode())
                        .address(inputData.getCustomerAddressStreet())
                        .build())
                .invoices(Set.of(invoice))
                .build();
    }

    private Customer createCustomer(List<String> inputData) {
        if (inputData.size() == 1) {
            return Customer.builder().email(inputData.get(0)).build();
        }
        return Customer.builder().name(inputData.get(0))
                .surname(inputData.get(1))
                .phone(inputData.get(2))
                .email(inputData.get(3))
                .address(Address.builder().country(inputData.get(4))
                        .city(inputData.get(5))
                        .postalCode(inputData.get(6))
                        .address(inputData.get(7))
                        .build())
                .build();
    }

    public List<CarServiceRequest> createCarServiceRequest() {
        return InputDataCache.getInputData(Keys.InputDataGroup.SERVICE_REQUEST, this::prepareMap).stream().map(this::createCarServiceRequest).toList();
    }

    private CarServiceRequest createCarServiceRequest(Map<String, List<String>> inputData) {
        return CarServiceRequest.builder().customer(createCustomer(inputData.get(Domain.CUSTOMER.toString()))).car(createCar(inputData.get(Domain.CAR.toString()))).customerComment(inputData.get(Keys.Constants.WHAT.toString()).get(0)).build();
    }

    private CarToService createCar(List<String> inputData) {
        if (inputData.size() == 1) {
            return CarToService.builder().vin(inputData.get(0)).build();
        }
        return CarToService.builder().vin(inputData.get(0)).brand(inputData.get(1)).model(inputData.get(2)).year(Integer.parseInt(inputData.get(3))).build();
    }

    public List<CarServiceProcessingInputData> prepareServiceRequestToProcess() {
        return InputDataCache
                .getInputData(
                        Keys.InputDataGroup.DO_THE_SERVICE,
                        this::prepareMap
                ).stream()
                .map(this::createCarServiceRequestToProcess)
                .toList();
    }

    private CarServiceProcessingInputData createCarServiceRequestToProcess(Map<String, List<String>> inputData) {
        List<String> whats = inputData.get(Keys.Constants.WHAT.toString());
        return CarServiceProcessingInputData.builder()
                .mechanicPesel(inputData.get(Domain.MECHANIC.toString()).get(0))
                .carVin(inputData.get(Domain.CAR.toString()).get(0))
                .partSerialNumber(
                        Optional.of(whats.get(0))
                                .filter(val -> !val.isBlank())
                                .orElse(null)
                )
                .partQuantity(Optional.of(whats.get(1))
                        .filter(val -> !val.isBlank())
                        .map(Integer::parseInt)
                        .orElse(null)
                )
                .serviceCode(whats.get(2))
                .hours(Integer.parseInt(whats.get(3)))
                .comment(whats.get(4))
                .done(whats.get(5))
                .build();
    }

    private Map<String, List<String>> prepareMap(String line) {
        List<String> grouped = Arrays.stream(line.split("->")).map(String::trim).toList();
        return IntStream.iterate(0, previous -> previous + 2).boxed().limit(3).collect(Collectors.toMap(grouped::get, i -> List.of(grouped.get(i + 1).split(";"))));

    }
}
