package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import br.edu.ifsudestemg.bgr.service.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserValidator validator;

    public void createUser(User user) {
        validator.validate(user);
        repository.save(user);
    }

    public List<User> searchForAllUsers() {
        return repository.findAll();
    }

    public Optional<User> searchForUserById(UUID id) {
        return repository.findById(id);
    }

    public void updateUser(User user) {
        validator.putValidate(user);
        repository.save(user);
    }

    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }
}
