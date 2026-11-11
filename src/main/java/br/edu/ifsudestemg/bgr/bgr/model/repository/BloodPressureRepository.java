package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.BloodPressure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodPressureRepository extends JpaRepository<BloodPressure, UUID> {
}
