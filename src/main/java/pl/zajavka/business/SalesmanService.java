package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.SalesmanDAO;
import pl.zajavka.domain.Salesman;

import java.util.Optional;

@AllArgsConstructor
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
}
