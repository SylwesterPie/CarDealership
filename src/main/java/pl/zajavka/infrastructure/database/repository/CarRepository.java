package pl.zajavka.infrastructure.database.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.zajavka.business.dao.CarDAO;
import pl.zajavka.infrastructure.database.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository implements CarDAO {
    @Override
    public CarToServiceEntity saveCarToService(CarToServiceEntity entity) {
         
            session.persist(entity);


            return entity;

    }

    @Override
    public Optional<CarToBuyEntity> findCarToBuyByVin(String vin) {
         
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToBuyEntity> criteriaQuery = criteriaBuilder.createQuery(CarToBuyEntity.class);
            Root<CarToBuyEntity> root = criteriaQuery.from(CarToBuyEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), parameter));

            Query<CarToBuyEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, vin);


    }

    @Override
    public Optional<CarToServiceEntity> findCarToServiceByVin(String vin) {
         
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CarToServiceEntity> criteriaQuery = criteriaBuilder.createQuery(CarToServiceEntity.class);
            Root<CarToServiceEntity> root = criteriaQuery.from(CarToServiceEntity.class);
            ParameterExpression<String> parameter = criteriaBuilder.parameter(String.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("vin"), parameter));

            Query<CarToServiceEntity> query = session.createQuery(criteriaQuery);
            query.setParameter(parameter, vin);

    }

    @Override
    public CarHistoryEntity findCarHistoryByVin(String vin) {
         
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

            return result;

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
