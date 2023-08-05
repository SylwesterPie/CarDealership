package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zajavka.business.*;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.infrastructure.database.repository.*;


@Slf4j
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.0");

    private CarDealershipManagementService carDealershipManagementService;

    private CarPurchaseService carPurchaseService;
    private CarServiceRequestService carServiceRequestService;
    private CarServiceProcessingService carServiceProcessingService;
    private CarService carService;

    @DynamicPropertySource
    static void postgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", postgreSQLContainer::getJdbcUrl);
        registry.add("jdbc.user", postgreSQLContainer::getUsername);
        registry.add("jdbc.pass", postgreSQLContainer::getPassword);
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

    @BeforeEach
    void beforeEach() {
        CustomerRepository customerDAO = new CustomerRepository();
        SalesmanRepository salesmanDAO = new SalesmanRepository();
        CarRepository carDAO = new CarRepository();
        CustomerService customerService = new CustomerService(customerDAO);
        SalesmanService salesmanService = new SalesmanService(salesmanDAO);
        FileDataPreparationService fileDataPreparationService = new FileDataPreparationService();
        MechanicRepository mechanicDAO = new MechanicRepository();
        MechanicService mechanicService = new MechanicService(mechanicDAO);
        ServiceRepository serviceDAO = new ServiceRepository();
        ServiceCatalogService serviceCatalogService = new ServiceCatalogService(serviceDAO);
        PartRepository partDAO = new PartRepository();
        PartCatalogService partCatalogService = new PartCatalogService(partDAO);
        ServiceRequestProcessingRepository serviceRequestProcessingDAO = new ServiceRequestProcessingRepository();
        CarServiceRequestRepository carServiceRequestDAO = new CarServiceRequestRepository();

        carService = new CarService(
                carDAO
        );
        carDealershipManagementService = new CarDealershipManagementService(
                new CarDealershipManagementRepository(),
                fileDataPreparationService
        );
        carPurchaseService = new CarPurchaseService(
                fileDataPreparationService,
                customerService,
                salesmanService,
                carService
        );
        carServiceRequestService = new CarServiceRequestService(
                fileDataPreparationService,
                carService,
                customerService,
                carServiceRequestDAO
        );
        carServiceProcessingService = new CarServiceProcessingService(
            fileDataPreparationService,
                mechanicService,
                carService,
                carServiceRequestService,
                serviceCatalogService,
                partCatalogService,
                serviceRequestProcessingDAO
        );
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
        carServiceRequestService.requestService();

    }

    @Test
    @Order(5)
    void processServiceRequest() {
        log.info("### RUNNING ORDER 5");
        carServiceProcessingService.process();
    }

    @Test
    @Order(6)
    void printCarHistory() {
        log.info("### RUNNING ORDER 6");
        carService.printCarHistory("2C3CDYAG2DH731952");
        carService.printCarHistory("1GCEC19X27Z109567");
    }
}
