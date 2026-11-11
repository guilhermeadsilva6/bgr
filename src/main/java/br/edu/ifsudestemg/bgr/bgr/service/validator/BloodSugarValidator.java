package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;
import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BloodSugarValidator {

    private final UserRepository userRepository;

    public void validate(BloodSugar bloodSugar) {
        if (bloodSugar.getUser() == null || bloodSugar.getUser().getId() == null) {
            throw new BusinessRuleException("O registro de glicose deve estar vinculado a um usuário válido.");
        }

        if (!isTheUserValid(bloodSugar)) {
            throw new BusinessRuleException("Usuário inválido");
        }
    }

    public boolean isTheUserValid(BloodSugar bloodSugar) {
        Optional<User> userFound = userRepository.findById(bloodSugar.getUser().getId());
        return userFound.isPresent();
    }
}
