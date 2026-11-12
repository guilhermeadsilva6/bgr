package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalOfficeRepository extends JpaRepository<MedicalOffice, UUID> {
}
