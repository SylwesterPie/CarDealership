package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.business.management.CarDealershipManagementService;
import pl.zajavka.business.management.FileDataPreparationService;
import pl.zajavka.business.management.InputDataCache;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.repository.CarDealershipManagementRepository;

import java.util.List;
import java.util.Map;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealershipManagementService carDealershipManagementService;


    @BeforeEach
    void beforeEach() {
        carDealershipManagementService = new CarDealershipManagementService(
                new CarDealershipManagementRepository(),
                new FileDataPreparationService()
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
