package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.Alert;
import br.edu.ifsudestemg.bgr.model.repository.AlertRepository;
import br.edu.ifsudestemg.bgr.service.validator.AlertValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository repository;
    private final AlertValidator validator;

    public void createAlertRecord(Alert alert) {
        validator.validate(alert);
        repository.save(alert);
    }

    public List<Alert> searchForAllAlertRecords() {
        return repository.findAll();
    }

    public Optional<Alert> searchForAlertRecordById(UUID id) {
        return repository.findById(id);
    }

    public void updateAlertRecord(Alert alert) {
        validator.validate(alert);
        repository.save(alert);
    }

    public void deleteAlertRecord(UUID id) {
        repository.deleteById(id);
    }
}
