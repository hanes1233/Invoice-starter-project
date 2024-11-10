package cz.itnetwork.entity;
//region imports
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
//endregion

@Entity(name = "invoice")
@Builder
@Getter
@Setter
public class InvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int invoiceNumber;

    @Column(nullable = false)
    private Date issued;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private String product;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private int vat;

    @Column(nullable = false)
    private String note;

    @ManyToOne
    private PersonEntity buyer;

    @ManyToOne
    private PersonEntity seller;
}
