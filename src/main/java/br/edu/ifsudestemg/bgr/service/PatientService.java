package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import br.edu.ifsudestemg.bgr.service.validator.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final PatientValidator validator;

    public void createPatient(Patient patient) {
        validator.validate(patient);
        repository.save(patient);
    }

    public List<Patient> searchForAllPatients() {
        return repository.findAll();
    }

    public Optional<Patient> searchForPatientById(UUID id) {
        return repository.findById(id);
    }

    public void updatePatient(Patient patient) {
        validator.putValidate(patient);
        repository.save(patient);
    }

    public void deletePatient(UUID id) {
        repository.deleteById(id);
    }
}
