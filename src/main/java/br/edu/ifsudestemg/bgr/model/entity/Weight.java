package br.edu.ifsudestemg.bgr.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Weight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "weight_record")
    private Float weightRecord;

    @Column(name = "registration_date")
    @CreatedDate
    private LocalDateTime registrationDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}