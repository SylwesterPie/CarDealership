package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "country", "city", "postalCode", "address"})
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private CustomerEntity customer;
}

