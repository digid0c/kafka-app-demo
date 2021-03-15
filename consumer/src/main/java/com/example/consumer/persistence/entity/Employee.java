package com.example.consumer.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 75)
    private String name;

    @Column(name = "surname", nullable = false, length = 75)
    private String surname;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;

    @OneToMany(mappedBy = "employee", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private List<WageRecord> wageRecords = new ArrayList<>();
}
