package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.BloodPressure;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record BloodPressureRequestDTO(
        @NotNull(message = "Nível de pressão não pode ser vazio!")
        @Positive(message = "Nível de pressão tem que ser maior que 0")
        Integer bloodPressureLevel,

        @NotNull(message = "O registro de pressão deve estar associado a um usuário!")
        String idPatient) {

    public BloodPressure dtoToEntity() {
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setBloodPressureLevel(this.bloodPressureLevel);

        Patient patient = new Patient();
        patient.setId(UUID.fromString(this.idPatient));
        bloodPressure.setPatient(patient);

        return bloodPressure;
    }
}
