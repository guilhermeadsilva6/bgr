package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.User;

import java.time.LocalDate;

public record UserResponseDTO(String name, String email, LocalDate birthDate) {

    public static UserResponseDTO entityToDto(User user) {
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getBirthDate()
        );
    }
}
