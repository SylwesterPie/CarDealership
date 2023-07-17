package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "car_to_buy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "carToBuyId")
@ToString(of = {"carToBuyId", "vin", "brand", "model", "year"})
public class CarToBuyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_to_buy_id")
    private Integer carToBuyId;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "color")
    private String color;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "car")
    private InvoiceEntity invoice;
}
