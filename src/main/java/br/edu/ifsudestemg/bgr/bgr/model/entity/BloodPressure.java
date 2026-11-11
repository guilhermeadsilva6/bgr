package br.edu.ifsudestemg.bgr.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "blood_pressure")
@Data
@EntityListeners(AuditingEntityListener.class)
public class BloodPressure {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "blood_pressure_level")
    private Integer bloodPressureLevel;

    @Column(name = "registration_date")
    @CreatedDate
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}