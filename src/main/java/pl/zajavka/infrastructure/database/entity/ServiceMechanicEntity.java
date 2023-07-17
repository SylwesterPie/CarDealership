package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "service_mechanic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "serviceMechanicId")
@ToString(of = {"serviceMechanicId", "hours", "comment"})
public class ServiceMechanicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_mechanic_id")
    private Integer serviceMechanicId;

    @Column(name = "hours", nullable = false)
    private Integer hours;

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_service_request_id", nullable = false)
    private CarServiceRequestEntity carServiceRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_id", nullable = false)
    private MechanicEntity mechanic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity service;

}
