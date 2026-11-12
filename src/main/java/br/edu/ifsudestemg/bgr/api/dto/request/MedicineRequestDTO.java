package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.Medicine;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.enums.MeasurementUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record MedicineRequestDTO(
        @NotBlank(message = "O nome do medicamento é obrigatório!")
        String name,

        @NotNull(message = "A dosagem do medicamento não pode ser vazia!")
        @Positive(message = "A dosagem do medicamento tem que ser maior que 0")
        Float dosage,

        @NotNull(message = "O medicamento precisa de uma unidade de medida")
        MeasurementUnit measurementUnit,

        @NotNull(message = "O registro de medicamento deve estar associado a um usuário!")
        String idPatient
) {
    public Medicine dtoToEntity() {
        Medicine medicine = new Medicine();
        medicine.setName(this.name);
        medicine.setDosage(this.dosage);
        medicine.setMeasurementUnit(this.measurementUnit);

        Patient patient = new Patient();
        patient.setId(UUID.fromString(this.idPatient));
        medicine.setPatient(patient);

        return medicine;
    }
}
