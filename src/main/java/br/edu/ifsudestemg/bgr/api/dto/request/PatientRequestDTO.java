package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.Patient;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRequestDTO(@NotBlank(message = "Nome é um campo obrigatório!")
                             String name,

                                @NotBlank(message = "E-mail é um campo obrigatório!")
                             @Email(message = "E-mail inválido!")
                             String email,

                                @NotBlank(message = "A senha é um campo obrigatório!")
                             @Size(min = 8, max = 32, message = "A senha deve ter entre 8 e 32 caracteres!")
                             String password,

                                @NotNull(message = "Data de nascimento é um campo obrigatório!")
                             @PastOrPresent(message = "Data de nascimento inválida!")
                             LocalDate birthDate) {

    public Patient dtoToEntity() {
        Patient patient = new Patient();
        patient.setName(this.name);
        patient.setEmail(this.email);
        patient.setPassword(this.password);
        patient.setBirthDate(this.birthDate);
        return patient;
    }
}
