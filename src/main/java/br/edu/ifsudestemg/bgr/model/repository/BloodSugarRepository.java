package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodSugarRepository extends JpaRepository<BloodSugar, UUID> {
}
