package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.PartDAO;
import pl.zajavka.domain.Part;

import java.util.Optional;


@AllArgsConstructor
public class PartCatalogService {

    private final PartDAO partDAO;

    @Transactional
    public Part findPart(String partSerialNumber) {
        Optional<Part> part = partDAO.findPartBySerialNumber(partSerialNumber);
        if(part.isEmpty()) {
            throw new RuntimeException("Could not find part by serial number: [%s]".formatted(partSerialNumber));
        }
        return part.get();
    }
}
