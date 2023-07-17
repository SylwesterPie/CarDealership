package pl.zajavka.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "salesman")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "salesmanId")
@ToString(of = {"salesmanId", "name", "surname", "pesel"})
public class SalesmanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salesman_id")
    private Integer salesmanId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "pesel", nullable = false)
    private String pesel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "salesman")
    private Set<InvoiceEntity> invoices;
}