package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record BloodSugarRequestDTO(
        @NotNull(message = "Nível de glicose não pode ser vazio!")
        @Positive(message = "Nível de glicose tem que ser maior que 0")
        Integer glucoseLevel,

        @NotNull(message = "O registro de glicose deve estar associado a um usuário!")
        String idPatient) {

    public BloodSugar dtoToEntity() {
        BloodSugar bloodSugar = new BloodSugar();
        bloodSugar.setGlucoseLevel(this.glucoseLevel);

        Patient patient = new Patient();
        patient.setId(UUID.fromString(this.idPatient));
        bloodSugar.setPatient(patient);

        return bloodSugar;
    }
}
