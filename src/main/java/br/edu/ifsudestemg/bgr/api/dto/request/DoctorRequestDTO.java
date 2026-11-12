package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import jakarta.validation.constraints.*;


public record DoctorRequestDTO(@NotBlank(message = "Nome é um campo obrigatório!")
                                String name,

                                @NotBlank(message = "A especialidade do médico é um campo obrigatório!")
                                String specialty) {

    public Doctor dtoToEntity() {
        Doctor doctor = new Doctor();
        doctor.setName(this.name);
        doctor.setSpecialty(this.specialty);
        return doctor;
    }
}