package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;
import br.edu.ifsudestemg.bgr.model.repository.BloodSugarRepository;
import br.edu.ifsudestemg.bgr.service.validator.BloodSugarValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BloodSugarService {

    private final BloodSugarRepository repository;
    private final BloodSugarValidator validator;

    public void createBloodSugarRecord(BloodSugar bloodSugar) {
        validator.validate(bloodSugar);
        repository.save(bloodSugar);
    }

    public List<BloodSugar> searchForAllBloodSugarRecords() {
        return repository.findAll();
    }

    public Optional<BloodSugar> searchForBloodSugarRecordById(UUID id) {
        return repository.findById(id);
    }

    public void updateBloodSugarRecord(BloodSugar bloodSugar) {
        validator.validate(bloodSugar);
        repository.save(bloodSugar);
    }

    public void deleteBloodSugarRecord(UUID idBloodSugar) {
        repository.deleteById(idBloodSugar);
    }
}