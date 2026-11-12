package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Medicine;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineValidator {

    private final PatientRepository patientRepository;

    public void validate(Medicine medicine) {
        if (medicine.getPatient() == null || medicine.getPatient().getId() == null) {
            throw new BusinessRuleException("O registro de medicamento deve estar vinculado a um paciente válido.");
        }

        if (!isThePatientValid(medicine)) {
            throw new BusinessRuleException("Paciente inválido");
        }
    }

    public boolean isThePatientValid(Medicine medicine) {
        Optional<Patient> patientFound = patientRepository.findById(medicine.getPatient().getId());
        return patientFound.isPresent();
    }
}
