package pl.zajavka.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.PartDAO;
import pl.zajavka.domain.Part;
import pl.zajavka.infrastructure.database.repository.jpa.PartJpaRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PartRepository implements PartDAO {

    private final PartJpaRepository partJpaRepository;
    private final PartEntityMapper partEntityMapper;

    @Override
    public Optional<Part> findPartBySerialNumber(String partSerialNumber) {
        return partJpaRepository.findBySerialNumber(partSerialNumber)
                .map(partEntityMapper::mapFromEntity);
    }
}
