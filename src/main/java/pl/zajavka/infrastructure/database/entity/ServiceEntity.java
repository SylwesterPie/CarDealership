package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "serviceId")
@ToString(of = {"serviceId", "serviceCode", "description", "price"})
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Integer serviceId;

    @Column(name = "service_code", nullable = false, unique = true)
    private String serviceCode;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
    private Set<ServiceMechanicEntity> serviceMechanics;
}
