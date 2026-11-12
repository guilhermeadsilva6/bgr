package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByEmail(String email);
}
