package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.User;
import br.edu.ifsudestemg.bgr.model.repository.UserRepository;
import br.edu.ifsudestemg.bgr.service.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserValidator validator;

    @InjectMocks
    private UserService service;

    @Test
    void shouldCreateUser() {
        User user = new User();
        user.setName("User");
        user.setEmail("user@email");
        user.setPassword("password");
        user.setBirthDate(LocalDate.parse("2000-01-01"));

        service.createUser(user);
        verify(validator).validate(user);
        verify(repository).save(user);
    }

    @Test
    void shouldFindAllUsers() {
        User user1 = new User();
        user1.setName("User 1");
        User user2 = new User();
        user2.setName("User 2");

        when(repository.findAll()).thenReturn(List.of(user1, user2));
        List<User> result = service.searchForAllUsers();

        assertEquals(2, result.size());
        assertEquals("User 1", result.get(0).getName());
        assertEquals("User 2", result.get(1).getName());

        verify(repository).findAll();
    }

    @Test
    void shouldFindUserById() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("User");

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> result = service.searchForUserById(user.getId());

        assertTrue(result.isPresent());
        assertEquals("User", result.get().getName());
        verify(repository).findById(user.getId());
    }

    @Test
    void shouldUpdateUser() {
        User user = new User();
        user.setId(UUID.randomUUID());

        service.updateUser(user);
        verify(validator).putValidate(user);
        verify(repository).save(user);
    }

    @Test
    void shouldDeleteUser() {
        User user = new User();
        UUID id = UUID.randomUUID();
        user.setId(id);

        service.deleteUser(id);
        verify(repository).deleteById(id);
    }
}