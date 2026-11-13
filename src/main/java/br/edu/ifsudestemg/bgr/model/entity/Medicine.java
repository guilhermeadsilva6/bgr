package br.edu.ifsudestemg.bgr.model.entity;

import br.edu.ifsudestemg.bgr.model.enums.MeasurementUnit;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private Float dosage;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_unit")
    private MeasurementUnit measurementUnit;

    @Column(name = "registration_date_time")
    @CreatedDate
    private LocalDateTime registrationDateTime;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;
}
