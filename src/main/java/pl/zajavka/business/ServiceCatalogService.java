package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.ServiceDAO;
import pl.zajavka.domain.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class ServiceCatalogService {

    private final ServiceDAO serviceDAO;

    @Transactional
    public Service findService(String serviceCode) {
        Optional<Service> service = serviceDAO.findByServiceCode(serviceCode);
        if(service.isEmpty()) {
            throw new RuntimeException("Could not find service by service code: [%s]".formatted(serviceCode));
        }
        return service.get();
    }

    public List<Service> findAll() {
        return serviceDAO.findAll();
    }
}
