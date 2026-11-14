package br.edu.ifsudestemg.bgr.model.entity;

import br.edu.ifsudestemg.bgr.model.enums.AlertStatus;
import br.edu.ifsudestemg.bgr.model.enums.AlertType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type")
    private AlertType alertType;

    @Column(name = "creation_date_time")
    @CreatedDate
    private LocalDateTime creationDateTime;

    @Column(name = "trigger_date_time")
    private LocalDateTime triggerDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_status")
    private AlertStatus alertStatus;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;
}
