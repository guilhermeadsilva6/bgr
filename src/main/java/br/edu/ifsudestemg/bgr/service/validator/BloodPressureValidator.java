package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.BloodPressure;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BloodPressureValidator {

    private final PatientRepository patientRepository;

    public void validate(BloodPressure bloodPressure) {
        if (bloodPressure.getPatient() == null || bloodPressure.getPatient().getId() == null) {
            throw new BusinessRuleException("O registro de pressão deve estar vinculado a um paciente válido.");
        }

        if (!isThePatientValid(bloodPressure)) {
            throw new BusinessRuleException("Paciente inválido");
        }
    }

    public boolean isThePatientValid(BloodPressure bloodPressure) {
        Optional<Patient> patientFound = patientRepository.findById(bloodPressure.getPatient().getId());
        return patientFound.isPresent();
    }
}
