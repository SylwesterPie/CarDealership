package pl.zajavka.infrastructure.database.repository;

import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.ServiceRequestProcessingDAO;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.PartEntity;
import pl.zajavka.infrastructure.database.entity.ServiceMechanicEntity;
import pl.zajavka.infrastructure.database.entity.ServicePartEntity;

import java.util.Objects;


@Repository
public class ServiceRequestProcessingRepository implements ServiceRequestProcessingDAO {
    @Override
    public void process(
            CarServiceRequestEntity serviceRequest,
            ServiceMechanicEntity serviceMechanicEntity
    ) {
        

            session.persist(serviceMechanicEntity);
            if( Objects.nonNull(serviceRequest.getCompletedDateTime())){
                session.merge(serviceRequest);
            }

    }

    @Override
    public void process(
            CarServiceRequestEntity serviceRequest,
            ServiceMechanicEntity serviceMechanicEntity,
            ServicePartEntity servicePartEntity
    ) {
        

            session.persist(serviceMechanicEntity);

            Integer partId = servicePartEntity.getPart().getPartId();
            PartEntity part = session.find(PartEntity.class, partId);
            servicePartEntity.setPart(part);
            session.persist(servicePartEntity);

            if( Objects.nonNull(serviceRequest.getCompletedDateTime())){
                session.merge(serviceRequest);
            }
 
    }
}
