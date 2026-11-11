package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.BloodPressure;

import java.time.LocalDateTime;

public record BloodPressureResponseDTO(
        Integer bloodPressureLevel,
        LocalDateTime registrationDate,
        String userName) {

    public static BloodPressureResponseDTO entityToDto(BloodPressure bloodPressure) {
        return new BloodPressureResponseDTO(
                bloodPressure.getBloodPressureLevel(),
                bloodPressure.getRegistrationDate(),
                bloodPressure.getUser().getName()
        );
    }
}
