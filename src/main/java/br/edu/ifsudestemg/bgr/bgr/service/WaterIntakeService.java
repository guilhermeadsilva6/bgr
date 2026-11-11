package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.WaterIntake;
import br.edu.ifsudestemg.bgr.model.repository.WaterIntakeRepository;
import br.edu.ifsudestemg.bgr.service.validator.WaterIntakeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WaterIntakeService {

    private final WaterIntakeRepository repository;
    private final WaterIntakeValidator validator;

    public void createWaterIntakeRecord(WaterIntake waterIntake) {
        validator.validate(waterIntake);
        repository.save(waterIntake);
    }

    public List<WaterIntake> searchForAllWaterIntakeRecords() {
        return repository.findAll();
    }

    public Optional<WaterIntake> searchForWaterIntakeRecordById(UUID id) {
        return repository.findById(id);
    }

    public void updateWaterIntakeRecord(WaterIntake waterIntake) {
        validator.validate(waterIntake);
        repository.save(waterIntake);
    }

    public void deleteWaterIntakeRecord(UUID id) {
        repository.deleteById(id);
    }
}