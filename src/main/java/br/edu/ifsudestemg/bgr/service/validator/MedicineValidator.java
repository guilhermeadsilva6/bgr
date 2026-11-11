package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Medicine;
import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineValidator {

    private final UserRepository userRepository;

    public void validate(Medicine medicine) {
        if (medicine.getUser() == null || medicine.getUser().getId() == null) {
            throw new BusinessRuleException("O registro de medicamento deve estar vinculado a um usuário válido.");
        }

        if (!isTheUserValid(medicine)) {
            throw new BusinessRuleException("Usuário inválido");
        }
    }

    public boolean isTheUserValid(Medicine medicine) {
        Optional<User> userFound = userRepository.findById(medicine.getUser().getId());
        return userFound.isPresent();
    }
}
