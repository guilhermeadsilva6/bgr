package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.Weight;
import br.edu.ifsudestemg.bgr.model.repository.WeightRepository;
import br.edu.ifsudestemg.bgr.service.validator.WeightValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WeightService {

    private final WeightRepository repository;
    private final WeightValidator validator;

    public void createWeightRecord(Weight weight) {
        validator.validate(weight);
        repository.save(weight);
    }

    public List<Weight> searchForAllWeightRecords() {
        return repository.findAll();
    }

    public Optional<Weight> searchForWeightRecordById(UUID id) {
        return repository.findById(id);
    }

    public void updateWeightRecord(Weight weight) {
        validator.validate(weight);
        repository.save(weight);
    }

    public void deleteWeightRecord(UUID idWeight) {
        repository.deleteById(idWeight);
    }
}