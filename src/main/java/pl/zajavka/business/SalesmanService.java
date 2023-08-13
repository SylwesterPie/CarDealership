package pl.zajavka.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.SalesmanDAO;
import pl.zajavka.domain.Salesman;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class SalesmanService {

    private final SalesmanDAO salesmanDAO;

    @Transactional
    public Salesman findSalesman(String pesel) {
        Optional<Salesman> salesman = salesmanDAO.findSalesmanByPesel(pesel);
        if(salesman.isEmpty()) {
            throw new RuntimeException("Could not find Salesman by pesel: [%s]".formatted(pesel));
        }
        return salesman.get();
    }

    @Transactional
    public List<Salesman> findAvailableSalesman() {
        List<Salesman> availableSalesmen = salesmanDAO.findAvailableSalesmen();
        log.info("Available Salesmen: [{}]", availableSalesmen);
        return availableSalesmen;
    }
}
