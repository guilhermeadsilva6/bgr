package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Weight;
import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WeightValidator {

    private final UserRepository userRepository;

    public void validate(Weight weight) {
        if (weight.getUser() == null || weight.getUser().getId() == null) {
            throw new BusinessRuleException("O registro de peso deve estar vinculado a um usuário válido.");
        }

        if (!isTheUserValid(weight)) {
            throw new BusinessRuleException("Usuário inválido");
        }
    }

    public boolean isTheUserValid(Weight weight) {
        Optional<User> userFound = userRepository.findById(weight.getUser().getId());
        return userFound.isPresent();
    }
}
