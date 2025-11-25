package com.iron_fit.iron_fit_backend.features.trainer_expertise.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainer_expertise")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrainerExpertise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
