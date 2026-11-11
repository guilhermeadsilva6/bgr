package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.WaterIntake;
import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WaterIntakeValidator {

    private final UserRepository userRepository;

    public void validate(WaterIntake waterIntake) {
        if (waterIntake.getUser() == null || waterIntake.getUser().getId() == null) {
            throw new BusinessRuleException("O registro de consumo de água deve estar vinculado a um usuário válido.");
        }

        if (!isTheUserValid(waterIntake)) {
            throw new BusinessRuleException("Usuário inválido");
        }
    }

    public boolean isTheUserValid(WaterIntake waterIntake) {
        Optional<User> userFound = userRepository.findById(waterIntake.getUser().getId());
        return userFound.isPresent();
    }
}
