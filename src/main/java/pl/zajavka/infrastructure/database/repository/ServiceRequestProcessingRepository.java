package pl.zajavka.infrastructure.database.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import pl.zajavka.business.dao.ServiceRequestProcessingDAO;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.PartEntity;
import pl.zajavka.infrastructure.database.entity.ServiceMechanicEntity;
import pl.zajavka.infrastructure.database.entity.ServicePartEntity;

import java.util.Objects;

public class ServiceRequestProcessingRepository implements ServiceRequestProcessingDAO {
    @Override
    public void process(
            CarServiceRequestEntity serviceRequest,
            ServiceMechanicEntity serviceMechanicEntity
    ) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();

            session.persist(serviceMechanicEntity);
            if( Objects.nonNull(serviceRequest.getCompletedDateTime())){
                session.merge(serviceRequest);
            }

            transaction.commit();
        }
    }

    @Override
    public void process(
            CarServiceRequestEntity serviceRequest,
            ServiceMechanicEntity serviceMechanicEntity,
            ServicePartEntity servicePartEntity
    ) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }
            Transaction transaction = session.beginTransaction();

            session.persist(serviceMechanicEntity);

            Integer partId = servicePartEntity.getPart().getPartId();
            PartEntity part = session.find(PartEntity.class, partId);
            servicePartEntity.setPart(part);
            session.persist(servicePartEntity);

            if( Objects.nonNull(serviceRequest.getCompletedDateTime())){
                session.merge(serviceRequest);
            }

            transaction.commit();
        }
    }
}
