package pl.zajavka.infrastructure.security;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "role")
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "car_dealership_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "role")
    private String role;
}

