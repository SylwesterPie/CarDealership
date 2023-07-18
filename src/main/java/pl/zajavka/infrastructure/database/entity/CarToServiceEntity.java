package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "car_to_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "carToServiceId")
@ToString(of = {"carToServiceId", "vin", "brand", "model"})
public class CarToServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_to_service_id")
    private Integer carToServiceId;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private Integer year;

    @OneToMany(mappedBy = "carToService", fetch = FetchType.LAZY)
    private Set<CarServiceRequestEntity> carServiceRequests;
}
