package com.iron_fit.iron_fit_backend.features.physical_evaluations.domain.entities;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.people_management.trainers.domain.entities.TrainerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "physical_evaluations")
public class PhysicalEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private TrainerEntity trainer;

    @Column(nullable = false)
    private LocalDate evaluationDate;

    @Column(nullable = false)
    private BigDecimal weight;

    @Column(nullable = false)
    private BigDecimal bmi;

    @Column(nullable = false)
    private BigDecimal bodyFatPercentage;

    @Column(nullable = false)
    private BigDecimal waistMeasurement;

    @Column(nullable = false)
    private BigDecimal hipMeasurement;

    @Column(nullable = false)
    private BigDecimal heightMeasurement;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
