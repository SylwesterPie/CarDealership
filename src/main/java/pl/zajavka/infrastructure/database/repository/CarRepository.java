package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import pl.zajavka.business.dao.CarDAO;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.entity.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CarRepository implements CarDAO {
    @Override
    public CarToServiceEntity saveCarToService(CarToServiceEntity entity) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }

            Transaction transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();
            return entity;
        }
    }

    @Override
    public Optional<CarToBuyEntity> findCarToBuyByVin(String vin) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }

            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToBuyEntity> criteriaQuery = criteriaBuilder.createQuery(CarToBuyEntity.class);
            Root<CarToBuyEntity> root = criteriaQuery.from(CarToBuyEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), parameter));

            Query<CarToBuyEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, vin);
            try {
                CarToBuyEntity result = query.getSingleResult();
                transaction.commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                transaction.commit();
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<CarToServiceEntity> findCarToServiceByVin(String vin) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }

            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToServiceEntity> criteriaQuery = criteriaBuilder.createQuery(CarToServiceEntity.class);
            Root<CarToServiceEntity> root = criteriaQuery.from(CarToServiceEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), parameter));

            Query<CarToServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, vin);
            try {
                CarToServiceEntity result = query.getSingleResult();
                transaction.commit();
                return Optional.of(result);
            } catch (Throwable ex) {
                transaction.commit();
                return Optional.empty();
            }
        }
    }

    @Override
    public CarHistoryEntity findCarHistoryByVin(String vin) {
        try (Session session = HibernateUtil.getSession()) {
            if (Objects.isNull(session)) {
                throw new RuntimeException("Session is null");
            }

            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToServiceEntity> criteriaQuery = criteriaBuilder.createQuery(CarToServiceEntity.class);
            Root<CarToServiceEntity> root = criteriaQuery.from(CarToServiceEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), parameter));

            Query<CarToServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, vin);
            CarToServiceEntity carToServiceEntity = query.getSingleResult();

            CarHistoryEntity result = CarHistoryEntity.builder()
                    .carVin(vin)
                    .serviceRequest(carToServiceEntity.getCarServiceRequests().stream().map(this::mapServiceRequest).toList())
                    .build();
            transaction.commit();
            return result;
        }
    }

    private CarHistoryEntity.ServiceRequest mapServiceRequest(CarServiceRequestEntity entity) {
        return CarHistoryEntity.ServiceRequest.builder()
                .serviceRequestNumber(entity.getCarServiceRequestNumber())
                .receivedDataTime(entity.getReceivedDateTime())
                .completedDataTime(entity.getCompletedDateTime())
                .customerComment(entity.getCustomerComment())
                .services(mapServices(entity.getServiceMechanics().stream().map(ServiceMechanicEntity::getService).toList()))
                .parts(mapParts(entity.getServiceParts().stream().map(ServicePartEntity::getPart).toList()))
                .build();
    }

    private List<CarHistoryEntity.Service> mapServices(List<ServiceEntity> entities) {
        return entities.stream()
                .map(service -> CarHistoryEntity.Service.builder()
                        .serviceCode(service.getServiceCode())
                        .description(service.getDescription())
                        .price(service.getPrice())
                        .build())
                .toList();
    }

    private List<CarHistoryEntity.Part> mapParts(List<PartEntity> entities) {
        return entities.stream()
                .map(part -> CarHistoryEntity.Part.builder()
                        .serialNumber(part.getSerialNumber())
                        .description(part.getDescription())
                        .price(part.getPrice())
                        .build())
                .toList();
    }
}
