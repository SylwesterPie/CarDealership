package pl.zajavka.infrastructure.database.repository;

import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.CarServiceRequestDAO;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CarServiceRequestRepository implements CarServiceRequestDAO {
    @Override
    public Set<CarServiceRequestEntity> findActiveServiceRequestByCarVin(String carVin) {

            String query = """
                    SELECT sr FROM CarServiceRequestEntity sr
                    WHERE
                    sr.carToService.vin = :vin
                    AND sr.completedDateTime IS NULL
                    """;

            List<CarServiceRequestEntity> result = session
                    .createQuery(query, CarServiceRequestEntity.class)
                    .setParameter("vin", carVin)
                    .list();

            return new HashSet<>(result);
        }
    }

