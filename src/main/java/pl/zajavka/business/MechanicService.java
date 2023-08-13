package pl.zajavka.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.business.dao.MechanicDAO;
import pl.zajavka.domain.Mechanic;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MechanicService {

    private final MechanicDAO mechanicDAO;

    @Transactional
    public Mechanic findMechanic(String pesel) {
        Optional<Mechanic> mechanic = mechanicDAO.findMechanicByPesel(pesel);
        if(mechanic.isEmpty()) {
            throw new RuntimeException("Could not find Mechanic by pesel: [%s]".formatted(pesel));
        }
        return mechanic.get();
    }

    @Transactional
    public List<Mechanic> findAvailableMechanic() {
        return mechanicDAO.findAvailableMechanic();
    }
}
