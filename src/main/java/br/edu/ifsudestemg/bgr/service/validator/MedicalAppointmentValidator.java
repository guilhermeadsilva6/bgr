package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import br.edu.ifsudestemg.bgr.model.entity.MedicalAppointment;
import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.enums.MedicalAppointmentStatus;
import br.edu.ifsudestemg.bgr.model.repository.DoctorRepository;
import br.edu.ifsudestemg.bgr.model.repository.MedicalAppointmentRepository;
import br.edu.ifsudestemg.bgr.model.repository.MedicalOfficeRepository;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicalAppointmentValidator {

    private final MedicalAppointmentRepository repository;
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

    public void putValidate(MedicalAppointment medicalAppointment) {
        validate(medicalAppointment);

        if (!validateAppointmentStatusChange(medicalAppointment)) {
            throw new BusinessRuleException("Alteração de status de constulta não permitida!");
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

    public boolean validateAppointmentStatusChange(MedicalAppointment medicalAppointment) {
        Optional<MedicalAppointment> oldMedicalAppointment = repository.findById(medicalAppointment.getId());
        if (oldMedicalAppointment.isEmpty()) {
            throw new BusinessRuleException("Consulta médica inexistente!");
        }

        String result = "";
        MedicalAppointmentStatus oldStatus = oldMedicalAppointment.get().getStatus();
        MedicalAppointmentStatus newStatus = medicalAppointment.getStatus();

        if (oldStatus == newStatus) {
            result = "true";
        } else {
            if (oldStatus == MedicalAppointmentStatus.CANCELED ||
                    oldStatus == MedicalAppointmentStatus.COMPLETED ||
                    oldStatus == MedicalAppointmentStatus.MISSED) {
                result = "false";
            } else {
                if (oldStatus == MedicalAppointmentStatus.SCHEDULED) {
                    if (newStatus == MedicalAppointmentStatus.CANCELED ||
                            newStatus == MedicalAppointmentStatus.CONFIRMED) {
                        result = "true";
                    } else {
                        result = "false";
                    }
                } else {
                    if (newStatus == MedicalAppointmentStatus.SCHEDULED) {
                        result = "false";
                    } else {
                        if (newStatus == MedicalAppointmentStatus.CANCELED) {
                            result = "true";
                        } else {
                            LocalDateTime appointmentDateTime = oldMedicalAppointment.get().getDateTime();
                            if (appointmentDateTime.isAfter(LocalDateTime.now())) {
                                result = "false";
                            } else {
                                result = "true";
                            }
                        }
                    }
                }
            }
        }
        return result.equals("true");
    }
}