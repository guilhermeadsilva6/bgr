package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.BloodPressure;
import br.edu.ifsudestemg.bgr.model.repository.BloodPressureRepository;
import br.edu.ifsudestemg.bgr.service.validator.BloodPressureValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BloodPressureService {

    private final BloodPressureRepository repository;
    private final BloodPressureValidator validator;

    public void createBloodPressureRecord(BloodPressure bloodPressure) {
        validator.validate(bloodPressure);
        repository.save(bloodPressure);
    }

    public List<BloodPressure> searchForAllBloodPressureRecords() {
        return repository.findAll();
    }

    public Optional<BloodPressure> searchForBloodPressureRecordById(UUID id) {
        return repository.findById(id);
    }

    public void updateBloodPressureRecord(BloodPressure bloodPressure) {
        validator.validate(bloodPressure);
        repository.save(bloodPressure);
    }

    public void deleteBloodPressureRecord(UUID idBloodPressure) {
        repository.deleteById(idBloodPressure);
    }
}