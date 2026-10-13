package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.Weight;
import br.edu.ifsudestemg.bgr.model.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record WeightRequestDTO(
        @NotNull(message = "Peso não pode ser vazio!")
        @Positive(message = "Peso tem que ser maior que 0")
        Float weightRecord,

        @NotNull(message = "O registro de peso deve estar associado a um usuário!")
        String idUser) {

    public Weight dtoToEntity() {
        Weight weight = new Weight();
        weight.setWeightRecord(this.weightRecord);

        User user = new User();
        user.setId(UUID.fromString(idUser));
        weight.setUser(user);

        return weight;
    }
}
