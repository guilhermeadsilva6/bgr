package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;
import br.edu.ifsudestemg.bgr.model.entity.Weight;

import java.time.LocalDateTime;

public record WeightResponseDTO(
        Float weightRecord,
        LocalDateTime registrationDate,
        String userName) {

    public static WeightResponseDTO entityToDto(Weight weight) {
        return new WeightResponseDTO(
                weight.getWeightRecord(),
                weight.getRegistrationDate(),
                weight.getUser().getName()
        );
    }
}
