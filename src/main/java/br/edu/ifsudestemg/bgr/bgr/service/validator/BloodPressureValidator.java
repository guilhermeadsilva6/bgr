package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.BloodPressure;
import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BloodPressureValidator {

    private final UserRepository userRepository;

    public void validate(BloodPressure bloodPressure) {
        if (bloodPressure.getUser() == null || bloodPressure.getUser().getId() == null) {
            throw new BusinessRuleException("O registro de pressão deve estar vinculado a um usuário válido.");
        }

        if (!isTheUserValid(bloodPressure)) {
            throw new BusinessRuleException("Usuário inválido");
        }
    }

    public boolean isTheUserValid(BloodPressure bloodPressure) {
        Optional<User> userFound = userRepository.findById(bloodPressure.getUser().getId());
        return userFound.isPresent();
    }
}
