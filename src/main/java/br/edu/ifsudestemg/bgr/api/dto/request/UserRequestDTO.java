package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.User;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequestDTO(@NotBlank(message = "Nome é um campo obrigatório!")
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

    public User dtoToEntity() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setBirthDate(this.birthDate);
        return user;
    }
}
