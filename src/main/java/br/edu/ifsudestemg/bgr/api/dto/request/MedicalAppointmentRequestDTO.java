package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import br.edu.ifsudestemg.bgr.model.entity.MedicalAppointment;
import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.enums.MedicalAppointmentStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record MedicalAppointmentRequestDTO(
        @NotNull(message = "A consulta precisa de data e hora!")
        LocalDateTime dateTime,

        @NotNull(message = "A consulta precisa de um status!")
        MedicalAppointmentStatus status,

        @NotNull(message = "A consulta precisa estar vinculada a um paciente")
        String idPatient,

        @NotNull(message = "A consulta precisa estar vinculada a um local")
        String idDoctor,

        @NotNull(message = "A consulta precisa estar vinculada a um endereço")
        String idAddress
) {
    public MedicalAppointment dtoToEntity() {
        MedicalAppointment medicalAppointment = new MedicalAppointment();
        medicalAppointment.setDateTime(this.dateTime);
        medicalAppointment.setStatus(this.status);

        Patient patient = new Patient();
        patient.setId(UUID.fromString(this.idPatient));
        medicalAppointment.setPatient(patient);

        Doctor doctor = new Doctor();
        doctor.setId(UUID.fromString(this.idDoctor));
        medicalAppointment.setDoctor(doctor);

        MedicalOffice address = new MedicalOffice();
        address.setId(UUID.fromString(this.idAddress));
        medicalAppointment.setAddress(address);

        return medicalAppointment;
    }

}