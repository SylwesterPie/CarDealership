package pl.zajavka.infrastructure.database.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "invoiceId")
@ToString(of = {"invoiceId", "invoiceNumber", "dateTime"})
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer invoiceId;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;

    @Column(name = "date_time", nullable = false)
    private OffsetDateTime dateTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_to_buy_id", nullable = false)
    private CarToBuyEntity car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesman_id", nullable = false)
    private SalesmanEntity salesman;
}
