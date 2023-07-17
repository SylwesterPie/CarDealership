package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "service_part")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "servicePartId")
@ToString(of = {"servicePartId", "quantity"})
public class ServicePartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_part_id")
    private Integer servicePartId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_service_request_id", nullable = false)
    private CarServiceRequestEntity carServiceRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    private PartEntity part;
}
