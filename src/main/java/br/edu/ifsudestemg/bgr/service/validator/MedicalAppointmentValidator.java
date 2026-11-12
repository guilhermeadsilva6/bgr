package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import br.edu.ifsudestemg.bgr.model.entity.MedicalAppointment;
import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.DoctorRepository;
import br.edu.ifsudestemg.bgr.model.repository.MedicalOfficeRepository;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicalAppointmentValidator {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalOfficeRepository addressRepository;

    public void validate(MedicalAppointment medicalAppointment) {
        if (medicalAppointment.getPatient() == null || medicalAppointment.getPatient().getId() == null) {
            throw new BusinessRuleException("A consulta médica deve estar vinculada a um paciente válido.");
        }

        if (medicalAppointment.getDoctor() == null || medicalAppointment.getDoctor().getId() == null) {
            throw new BusinessRuleException("A consulta médica deve estar vinculada a um médico válido.");
        }

        if (medicalAppointment.getAddress() == null || medicalAppointment.getAddress().getId() == null) {
            throw new BusinessRuleException("A consulta médica deve estar vinculada a um endereço válido.");
        }

        if (!isThePatientValid(medicalAppointment)) {
            throw new BusinessRuleException("Paciente inválido");
        }

        if (!isTheDoctorValid(medicalAppointment)) {
            throw new BusinessRuleException("Médico inválido");
        }

        if (!isTheAddressValid(medicalAppointment)) {
            throw new BusinessRuleException("Endereço inválido");
        }
    }

    public boolean isThePatientValid(MedicalAppointment medicalAppointment) {
        Optional<Patient> patientFound = patientRepository.findById(medicalAppointment.getPatient().getId());
        return patientFound.isPresent();
    }

    public boolean isTheDoctorValid(MedicalAppointment medicalAppointment) {
        Optional<Doctor> doctorFound = doctorRepository.findById(medicalAppointment.getDoctor().getId());
        return doctorFound.isPresent();
    }

    public boolean isTheAddressValid(MedicalAppointment medicalAppointment) {
        Optional<MedicalOffice> addressFound = addressRepository.findById(medicalAppointment.getAddress().getId());
        return addressFound.isPresent();
    }
}