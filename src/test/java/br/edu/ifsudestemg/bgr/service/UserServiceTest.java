package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import br.edu.ifsudestemg.bgr.service.validator.PatientValidator;
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
    private PatientRepository repository;

    @Mock
    private PatientValidator validator;

    @InjectMocks
    private PatientService service;

    @Test
    void shouldCreateUser() {
        Patient user = new Patient();
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
        Patient user1 = new Patient();
        user1.setName("User 1");
        Patient user2 = new Patient();
        user2.setName("User 2");

        when(repository.findAll()).thenReturn(List.of(user1, user2));
        List<Patient> result = service.searchForAllUsers();

        assertEquals(2, result.size());
        assertEquals("User 1", result.get(0).getName());
        assertEquals("User 2", result.get(1).getName());

        verify(repository).findAll();
    }

    @Test
    void shouldFindUserById() {
        Patient user = new Patient();
        user.setId(UUID.randomUUID());
        user.setName("User");

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<Patient> result = service.searchForUserById(user.getId());

        assertTrue(result.isPresent());
        assertEquals("User", result.get().getName());
        verify(repository).findById(user.getId());
    }

    @Test
    void shouldUpdateUser() {
        Patient user = new Patient();
        user.setId(UUID.randomUUID());

        service.updateUser(user);
        verify(validator).putValidate(user);
        verify(repository).save(user);
    }

    @Test
    void shouldDeleteUser() {
        Patient user = new Patient();
        UUID id = UUID.randomUUID();
        user.setId(id);

        service.deleteUser(id);
        verify(repository).deleteById(id);
    }
}