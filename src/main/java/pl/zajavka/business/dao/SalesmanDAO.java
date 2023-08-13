package pl.zajavka.business.dao;


import pl.zajavka.domain.Salesman;

import java.util.List;
import java.util.Optional;

public interface SalesmanDAO {
    Optional<Salesman> findSalesmanByPesel(String pesel);

    List<Salesman> findAvailableSalesmen();
}
