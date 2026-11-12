package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.MedicalAppointment;
import br.edu.ifsudestemg.bgr.model.enums.MedicalAppointmentStatus;

import java.time.LocalDateTime;

public record MedicalAppointmentResponseDTO(
        LocalDateTime dateTime,
        MedicalAppointmentStatus status,
        String patientName,
        String doctorName,
        String medicalOfficeName,
        String medicalOfficeCountry,
        String medicalOfficeState,
        String medicalOfficeCity,
        String medicalOfficeNeighborhood,
        String medicalOfficeStreet,
        String medicalOfficeNumber,
        String medicalOfficeComplement
) {
    public static MedicalAppointmentResponseDTO entityToDto(MedicalAppointment medicalAppointment) {
        return new MedicalAppointmentResponseDTO(
                medicalAppointment.getDateTime(),
                medicalAppointment.getStatus(),
                medicalAppointment.getPatient().getName(),
                medicalAppointment.getDoctor().getName(),
                medicalAppointment.getAddress().getDoctorsOffice(),
                medicalAppointment.getAddress().getCountry(),
                medicalAppointment.getAddress().getState(),
                medicalAppointment.getAddress().getDoctorsOffice(),
                medicalAppointment.getAddress().getNeighborhood(),
                medicalAppointment.getAddress().getStreet(),
                medicalAppointment.getAddress().getNumber(),
                medicalAppointment.getAddress().getComplement()
        );
    }
}
