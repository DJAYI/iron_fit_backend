package com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities;

import com.iron_fit.iron_fit_backend.features.auth.domain.entities.UserEntity;
import com.iron_fit.iron_fit_backend.features.people_management.shared.enums.DocumentTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    private String firstName;
    private String lastName;

    private DocumentTypeEnum documentType;
    private String documentNumber;

    private String email;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    UserEntity user;
}