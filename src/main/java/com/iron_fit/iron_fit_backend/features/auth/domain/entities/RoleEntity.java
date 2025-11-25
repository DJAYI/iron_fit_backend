package com.iron_fit.iron_fit_backend.features.auth.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
