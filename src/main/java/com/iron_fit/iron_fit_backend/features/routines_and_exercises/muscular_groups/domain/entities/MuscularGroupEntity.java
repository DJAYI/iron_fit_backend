package com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "muscular_groups")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MuscularGroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
