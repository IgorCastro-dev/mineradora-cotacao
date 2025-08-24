package org.mineradora.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "quotation")
public class QuotationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Column(name = "current_price", precision = 10, scale = 4)
    private BigDecimal currentPrice;

    @Column(name = "pct_change")
    private String pctChange;

    private String pair;
}
