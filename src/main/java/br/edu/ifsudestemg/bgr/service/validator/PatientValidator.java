package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatientValidator {

    private final PatientRepository repository;

    public void validate(Patient patient) {
        if (doesTheEmailAlreadyExist(patient)) {
            throw new BusinessRuleException("E-mail já cadastrado no sistema.");
        }
    }

    public void putValidate(Patient patient) {
       Optional<Patient> oldPatient = repository.findById(patient.getId());
       if (!oldPatient.isPresent()) {
           throw new NoSuchElementException("Paciente não encontrado!");
       }

       if (!(oldPatient.get().getEmail().equals(patient.getEmail()))) {
           if (doesTheEmailAlreadyExist(patient)) {
               throw new BusinessRuleException("E-mail já cadastrado no sistema.");
           }
       }
    }

    public boolean doesTheEmailAlreadyExist(Patient patient) {
        Optional<Patient> patientFound = repository.findByEmail(patient.getEmail());
        return patientFound.isPresent();
    }
}
