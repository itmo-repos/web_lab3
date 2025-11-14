package com.lab3.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lab3_table", schema = "s467669")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
    @SequenceGenerator(name = "sequence-generator", sequenceName = "lab3_table_id_seq", allocationSize = 1)
    private long id;

    @Column(precision = 38, scale = 20)
    private BigDecimal x;

    @Column(precision = 38, scale = 20)
    private BigDecimal y;
    
    @Column(precision = 38, scale = 20)
    private BigDecimal r;
    
    private boolean hit;
}
