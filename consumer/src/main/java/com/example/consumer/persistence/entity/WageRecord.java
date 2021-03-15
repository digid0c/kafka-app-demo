package com.example.consumer.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "wage_record")
@Data
public class WageRecord {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "base_wage", nullable = false)
    private BigDecimal baseWage;

    @Column(name = "tax_percent", nullable = false)
    private Integer taxPercent;

    @Column(name = "tax_added_wage", nullable = false)
    private BigDecimal taxAddedWage;

    @Column(name = "total_wage", nullable = false)
    private BigDecimal totalWage;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;
}
