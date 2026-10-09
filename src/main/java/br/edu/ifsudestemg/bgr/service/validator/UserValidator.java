package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository repository;

    public void validate(User user) {
        if (doesTheEmailAlreadyExist(user)) {
            throw new BusinessRuleException("E-mail já cadastrado no sistema.");
        }
    }

    public void putValidate(User user) {
       Optional<User> oldUser = repository.findById(user.getId());
       if (!oldUser.isPresent()) {
           throw new NoSuchElementException("Usuário não encontrado!");
       }

       if (!(oldUser.get().getEmail().equals(user.getEmail()))) {
           if (doesTheEmailAlreadyExist(user)) {
               throw new BusinessRuleException("E-mail já cadastrado no sistema.");
           }
       }
    }

    public boolean doesTheEmailAlreadyExist(User user) {
        Optional<User> userFound = repository.findByEmail(user.getEmail());
        return userFound.isPresent();
    }
}
