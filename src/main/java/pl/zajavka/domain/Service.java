package pl.zajavka.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "serviceId")
@ToString(of = {"serviceId", "serviceCode", "description", "price"})
public class Service {

    Integer serviceId;
    String serviceCode;
    String description;
    BigDecimal price;
    Set<ServiceMechanic> serviceMechanics;
}
