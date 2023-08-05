package pl.zajavka.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "partId")
@ToString(of = {"partId", "serialNumber", "description", "price"})
public class Part {

    Integer partId;
    String serialNumber;
    String description;
    BigDecimal price;
    Set<ServicePart> serviceParts;
}
