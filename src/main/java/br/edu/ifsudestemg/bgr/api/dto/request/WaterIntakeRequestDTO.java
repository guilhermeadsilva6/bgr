package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.entity.WaterIntake;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.UUID;

public record WaterIntakeRequestDTO(
        @NotNull(message = "A quantidade de água ingerida não pode ser vazia!")
        @Positive(message = "A quantidade de água ingerida tem que ser maior que 0mL!")
        Integer amountMl,

        @NotNull(message = "O registro de consumo de água precisa de data e hora!")
        LocalDateTime dateTime,

        @NotNull(message = "O registro de medicamento deve estar associado a um usuário!")
        String idPatient
) {
    public WaterIntake dtoToEntity() {
        WaterIntake waterIntake = new WaterIntake();
        waterIntake.setAmountMl(this.amountMl);
        waterIntake.setDateTime(this.dateTime);

        Patient patient = new Patient();
        patient.setId(UUID.fromString(this.idPatient));
        waterIntake.setPatient(patient);

        return waterIntake;
    }
}