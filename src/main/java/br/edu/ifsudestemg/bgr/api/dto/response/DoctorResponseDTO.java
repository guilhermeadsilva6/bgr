package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.Doctor;

public record DoctorResponseDTO(String name, String specialty) {

    public static DoctorResponseDTO entityToDto(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getName(),
                doctor.getSpecialty()
        );
    }
}