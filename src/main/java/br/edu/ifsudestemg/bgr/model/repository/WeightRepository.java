package br.edu.ifsudestemg.bgr.model.repository;

import br.edu.ifsudestemg.bgr.model.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeightRepository extends JpaRepository<Weight, UUID> {
}
