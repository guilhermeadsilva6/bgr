package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.MedicalAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalAppointmentRepository extends JpaRepository<MedicalAppointment, UUID> {
}
