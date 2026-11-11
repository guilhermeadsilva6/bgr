package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.WaterIntake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WaterIntakeRepository extends JpaRepository<WaterIntake, UUID> {
}
