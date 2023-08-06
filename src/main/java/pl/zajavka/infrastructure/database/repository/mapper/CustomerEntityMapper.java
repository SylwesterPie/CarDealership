package pl.zajavka.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.zajavka.domain.Customer;
import pl.zajavka.infrastructure.database.entity.CustomerEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {

    Customer mapFromEntity(CustomerEntity entity);

    CustomerEntity mapToEntity(Customer customer);
}
