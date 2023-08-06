package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Table(name = "car_service_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "carServiceRequestId")
@ToString(of = {
        "carServiceRequestId", "carServiceRequestNumber",
        "receivedDateTime", "completedDateTime", "customerComment"
}
)
public class CarServiceRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_service_request_id")
    private Integer carServiceRequestId;

    @Column(name = "car_service_request_number", nullable = false, unique = true)
    private String carServiceRequestNumber;

    @Column(name = "received_date_time", nullable = false)
    private OffsetDateTime receivedDateTime;

    @Column(name = "completed_date_time")
    private OffsetDateTime completedDateTime;

    @Column(name = "customer_comment", columnDefinition = "TEXT")
    private String customerComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_to_service_id", nullable = false)
    private CarToServiceEntity car;

    @OneToMany(mappedBy = "carServiceRequest", fetch = FetchType.LAZY)
    private Set<ServiceMechanicEntity> serviceMechanics;

    @OneToMany(mappedBy = "carServiceRequest", fetch = FetchType.LAZY)
    private Set<ServicePartEntity> serviceParts;

}
