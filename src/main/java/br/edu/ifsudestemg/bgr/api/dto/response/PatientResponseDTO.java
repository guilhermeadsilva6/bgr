package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.Patient;

import java.time.LocalDate;

public record PatientResponseDTO(String name, String email, LocalDate birthDate) {

    public static PatientResponseDTO entityToDto(Patient patient) {
        return new PatientResponseDTO(
                patient.getName(),
                patient.getEmail(),
                patient.getBirthDate()
        );
    }
}
