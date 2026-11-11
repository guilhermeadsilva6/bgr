package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;

import java.time.LocalDateTime;

public record BloodSugarResponseDTO(
        Integer glucoseLevel,
        LocalDateTime registrationDate,
        String userName) {

    public static BloodSugarResponseDTO entityToDto(BloodSugar bloodSugar) {
        return new BloodSugarResponseDTO(
                bloodSugar.getGlucoseLevel(),
                bloodSugar.getRegistrationDate(),
                bloodSugar.getUser().getName()
        );
    }
}
