package pl.zajavka.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;

@Repository
public interface CarServiceRequestJpaRepository  extends JpaRepository<CarServiceRequestEntity, Integer> {


}
