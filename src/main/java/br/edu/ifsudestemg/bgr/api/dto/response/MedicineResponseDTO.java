package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.Medicine;
import br.edu.ifsudestemg.bgr.model.enums.MeasurementUnit;

import java.time.LocalDateTime;

public record MedicineResponseDTO(String name,
                                  Float dosage,
                                  MeasurementUnit measurementUnit,
                                  LocalDateTime registrationDateTime,
                                  String patientName) {

    public static MedicineResponseDTO entityToDto(Medicine medicine) {
        return new MedicineResponseDTO(
                medicine.getName(),
                medicine.getDosage(),
                medicine.getMeasurementUnit(),
                medicine.getRegistrationDateTime(),
                medicine.getPatient().getName()
        );
    }

}
