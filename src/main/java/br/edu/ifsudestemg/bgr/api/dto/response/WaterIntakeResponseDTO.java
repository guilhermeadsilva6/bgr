package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.WaterIntake;

import java.time.LocalDateTime;

public record WaterIntakeResponseDTO(
        Integer amountMl,
        LocalDateTime dateTime,
        String patientName) {

    public static WaterIntakeResponseDTO entityToDto(WaterIntake waterIntake) {
        return new WaterIntakeResponseDTO(
                waterIntake.getAmountMl(),
                waterIntake.getDateTime(),
                waterIntake.getPatient().getName()
        );
    }
}
