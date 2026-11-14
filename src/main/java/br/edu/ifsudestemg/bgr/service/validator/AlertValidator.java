package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Alert;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlertValidator {
    
    private final PatientRepository patientRepository;

    public void validate(Alert alert) {
        if (alert.getPatient() == null || alert.getPatient().getId() == null) {
            throw new BusinessRuleException("O alerta deve estar vinculado a um paciente válido.");
        }

        if (!isThePatientValid(alert)) {
            throw new BusinessRuleException("Paciente inválido");
        }
    }

    public boolean isThePatientValid(Alert alert) {
        Optional<Patient> patientFound = patientRepository.findById(alert.getPatient().getId());
        return patientFound.isPresent();
    }
}
