package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
}
