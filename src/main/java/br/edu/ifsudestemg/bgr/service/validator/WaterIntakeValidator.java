package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.WaterIntake;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WaterIntakeValidator {

    private final PatientRepository patientRepository;

    public void validate(WaterIntake waterIntake) {
        if (waterIntake.getPatient() == null || waterIntake.getPatient().getId() == null) {
            throw new BusinessRuleException("O registro de consumo de água deve estar vinculado a um paciente válido.");
        }

        if (!isThePatientValid(waterIntake)) {
            throw new BusinessRuleException("Paciente inválido");
        }
    }

    public boolean isThePatientValid(WaterIntake waterIntake) {
        Optional<Patient> patientFound = patientRepository.findById(waterIntake.getPatient().getId());
        return patientFound.isPresent();
    }
}
