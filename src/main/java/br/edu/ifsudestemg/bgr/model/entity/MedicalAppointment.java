package br.edu.ifsudestemg.bgr.model.entity;

import br.edu.ifsudestemg.bgr.model.enums.MedicalAppointmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "medical_appointment")
@Data
public class MedicalAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private MedicalAppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private MedicalOffice address;
}
