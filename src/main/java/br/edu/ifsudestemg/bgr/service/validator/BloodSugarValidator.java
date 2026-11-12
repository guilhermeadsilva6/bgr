package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BloodSugarValidator {

    private final PatientRepository patientRepository;

    public void validate(BloodSugar bloodSugar) {
        if (bloodSugar.getPatient() == null || bloodSugar.getPatient().getId() == null) {
            throw new BusinessRuleException("O registro de glicose deve estar vinculado a um paciente válido.");
        }

        if (!isThePatientValid(bloodSugar)) {
            throw new BusinessRuleException("Paciente inválido");
        }
    }

    public boolean isThePatientValid(BloodSugar bloodSugar) {
        Optional<Patient> patientFound = patientRepository.findById(bloodSugar.getPatient().getId());
        return patientFound.isPresent();
    }
}
