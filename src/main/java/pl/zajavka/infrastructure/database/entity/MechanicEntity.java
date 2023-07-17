package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "mechanic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "mechanicId")
@ToString(of = {"mechanicId", "name", "surname", "pesel"})
public class MechanicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mechanic_id")
    private Integer mechanicId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "pesel", nullable = false)
    private String pesel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mechanic")
    private Set<ServiceMechanicEntity> serviceMechanics;
}
