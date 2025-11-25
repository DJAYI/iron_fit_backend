package com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
