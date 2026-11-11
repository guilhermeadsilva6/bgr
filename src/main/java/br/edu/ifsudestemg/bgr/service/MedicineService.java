package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.Medicine;
import br.edu.ifsudestemg.bgr.model.repository.MedicineRepository;
import br.edu.ifsudestemg.bgr.service.validator.MedicineValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository repository;
    private final MedicineValidator validator;

    public void createMedicationRecord(Medicine medicine) {
        validator.validate(medicine);
        repository.save(medicine);
    }

    public List<Medicine> searchForAllMedicationRecords() {
        return repository.findAll();
    }

    public Optional<Medicine> searchForMedicationRecordById(UUID id) {
        return repository.findById(id);
    }

    public void updateMedicationRecord(Medicine medicine) {
        validator.validate(medicine);
        repository.save(medicine);
    }

    public void deleteMedicationRecord(UUID id) {
        repository.deleteById(id);
    }
}
