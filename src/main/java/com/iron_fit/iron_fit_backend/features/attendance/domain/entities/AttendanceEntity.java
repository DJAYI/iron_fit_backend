package com.iron_fit.iron_fit_backend.features.attendance.domain.entities;

import com.iron_fit.iron_fit_backend.features.people_management.clients.domain.entities.ClientEntity;
import com.iron_fit.iron_fit_backend.features.trainment_sessions.sessions.domain.entities.Session;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttendanceStatus status;

    @Column(nullable = false)
    private Boolean completed;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
