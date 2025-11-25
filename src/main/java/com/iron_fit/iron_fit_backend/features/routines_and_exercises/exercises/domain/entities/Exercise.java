package com.iron_fit.iron_fit_backend.features.routines_and_exercises.exercises.domain.entities;

import com.iron_fit.iron_fit_backend.features.routines_and_exercises.categories.domain.entities.CategoryEntity;
import com.iron_fit.iron_fit_backend.features.routines_and_exercises.muscular_groups.domain.entities.MuscularGroupEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exercises")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exercise_name", nullable = false)
    private String name;

    @Column(name = "exercise_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "muscular_group_id", nullable = false)
    private MuscularGroupEntity muscularGroup;
}