package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;

import java.time.LocalDateTime;

public record BloodSugarResponseDTO(
        Integer glucoseLevel,
        LocalDateTime registrationDateTime,
        String patientName) {

    public static BloodSugarResponseDTO entityToDto(BloodSugar bloodSugar) {
        return new BloodSugarResponseDTO(
                bloodSugar.getGlucoseLevel(),
                bloodSugar.getRegistrationDateTime(),
                bloodSugar.getPatient().getName()
        );
    }
}
