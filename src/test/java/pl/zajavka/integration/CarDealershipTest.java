package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.business.CarPurchaseService;
import pl.zajavka.business.CarService;
import pl.zajavka.business.CustomerService;
import pl.zajavka.business.SalesmanService;
import pl.zajavka.business.management.CarDealershipManagementService;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.repository.CarDealershipManagementRepository;
import pl.zajavka.infrastructure.database.repository.CarRepository;
import pl.zajavka.infrastructure.database.repository.CustomerRepository;
import pl.zajavka.infrastructure.database.repository.SalesmanRepository;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipManagementService;
    private CarPurchaseService carPurchaseService;


    @BeforeEach
    void beforeEach() {
        FileDataPreparationService fileDataPreparationService = new FileDataPreparationService();
        carDealershipManagementService = new CarDealershipManagementService(
                new CarDealershipManagementRepository(),
                fileDataPreparationService
        );
        carPurchaseService = new CarPurchaseService(
                fileDataPreparationService,
                new CustomerService(new CustomerRepository()),
                new SalesmanService(new SalesmanRepository()),
                new CarService(new CarRepository())
        );
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

    @Test
    @Order(1)
    void purge() {
        log.info("### RUNNING ORDER 1");
        carDealershipManagementService.purge();
    }

    @Test
    @Order(2)
    void init() {
        log.info("### RUNNING ORDER 2");
        carDealershipManagementService.init();
    }

    @Test
    @Order(3)
    void purchase() {
        log.info("### RUNNING ORDER 3");
        carPurchaseService.purchase();
    }

    @Test
    @Order(4)
    void makeServiceRequest() {
        log.info("### RUNNING ORDER 4");

    }

    @Test
    @Order(5)
    void processServiceRequest() {
        log.info("### RUNNING ORDER 5");

    }

    @Test
    @Order(6)
    void printCarHistory() {
        log.info("### RUNNING ORDER 6");

    }
}
