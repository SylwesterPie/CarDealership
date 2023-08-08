package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zajavka.business.*;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.ServiceMechanicEntity;
import pl.zajavka.infrastructure.database.entity.ServicePartEntity;
import pl.zajavka.infrastructure.database.repository.*;
import pl.zajavka.infrastructure.database.repository.jpa.CarServiceRequestJpaRepository;
import pl.zajavka.infrastructure.database.repository.jpa.InvoiceJpaRepository;
import pl.zajavka.infrastructure.database.repository.jpa.ServiceMechanicJpaRepository;
import pl.zajavka.infrastructure.database.repository.jpa.ServicePartJpaRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CarDealershipTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.0");


    private final CarPurchaseService carPurchaseService;
    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceProcessingService carServiceProcessingService;
    private final CarService carService;
    private final InvoiceJpaRepository invoiceJpaRepository;
    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final ServiceMechanicJpaRepository serviceMechanicJpaRepository;
    private final ServicePartJpaRepository servicePartJpaRepository;

    @DynamicPropertySource
    static void postgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", postgreSQLContainer::getJdbcUrl);
        registry.add("jdbc.user", postgreSQLContainer::getUsername);
        registry.add("jdbc.pass", postgreSQLContainer::getPassword);
    }



    @Test
    @Order(1)
    void purchase() {
        log.info("### RUNNING ORDER 1");
        carPurchaseService.purchase();
    }

    @Test
    @Order(2)
    void makeServiceRequest() {
        log.info("### RUNNING ORDER 2");
        carServiceRequestService.requestService();

    }

    @Test
    @Order(3)
    void processServiceRequest() {
        log.info("### RUNNING ORDER 3");
        carServiceProcessingService.process();
    }

    @Test
    @Order(4)
    void printCarHistory() {
        log.info("### RUNNING ORDER 4");
        carService.printCarHistory("2C3CDYAG2DH731952");
        carService.printCarHistory("1GCEC19X27Z109567");
    }

    @Test
    @Order(5)
    void verify(){
        assertEquals(6, invoiceJpaRepository.findAll().size());

        List<CarServiceRequestEntity> allServiceRequest = carServiceRequestJpaRepository.findAll();
        assertEquals(3, allServiceRequest.size());
        assertEquals(2, allServiceRequest.stream().filter(
                sr -> Objects.nonNull(sr.getCompletedDateTime())).count()
        );

        List<ServiceMechanicEntity> allServiceMechanic = serviceMechanicJpaRepository.findAll();
        assertEquals(5, allServiceMechanic.size());

        List<ServicePartEntity> allServicePart = servicePartJpaRepository.findAll();
        assertEquals(4, allServicePart.size());
    }
}
