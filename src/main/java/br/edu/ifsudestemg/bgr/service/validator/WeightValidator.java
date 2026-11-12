package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Weight;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WeightValidator {

    private final PatientRepository patientRepository;

    public void validate(Weight weight) {
        if (weight.getPatient() == null || weight.getPatient().getId() == null) {
            throw new BusinessRuleException("O registro de peso deve estar vinculado a um paciente válido.");
        }

        if (!isThePatientValid(weight)) {
            throw new BusinessRuleException("Paciente inválido");
        }
    }

    public boolean isThePatientValid(Weight weight) {
        Optional<Patient> patientFound = patientRepository.findById(weight.getPatient().getId());
        return patientFound.isPresent();
    }
}
