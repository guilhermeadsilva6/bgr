package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private PatientValidator validator;

    @Test
    void shouldAcceptEmailWhenNotRegistered() {
        Patient user = new Patient();
        user.setEmail("user@email.com");

        when(repository.findByEmail("user@email.com"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.validate(user));

        verify(repository).findByEmail("user@email.com");
    }

    @Test
    void shouldThrowAnExceptionForAnExistingEmail() {
        Patient user = new Patient();
        user.setEmail("user@email");

        when(repository.findByEmail("user@email")).thenReturn(Optional.of(new Patient()));

        BusinessRuleException e = assertThrows(
                BusinessRuleException.class,
                () -> validator.validate(user)
        );

        assertEquals("E-mail já cadastrado no sistema.", e.getMessage());
        verify(repository).findByEmail("user@email");
    }

    @Test
    void shouldThrowAnExceptionIfUserNotExists() {
        Patient user = new Patient();
        UUID id = UUID.randomUUID();
        user.setId(id);

        when(repository.findById(id)).thenReturn(Optional.empty());

        NoSuchElementException e = assertThrows(
                NoSuchElementException.class,
                () -> validator.putValidate(user)
        );

        assertEquals("Usuário não encontrado!", e.getMessage());
        verify(repository).findById(id);
    }

    @Test
    void shouldThrowAnExceptionForAnExistingEmailPut() {
        UUID id = UUID.randomUUID();

        Patient oldUser = new Patient();
        oldUser.setId(id);
        oldUser.setEmail("old@email.com");

        Patient updatedUser = new Patient();
        updatedUser.setId(id);
        updatedUser.setEmail("existing@email.com");

        when(repository.findById(id)).thenReturn(Optional.of(oldUser));

        when(repository.findByEmail("existing@email.com")).thenReturn(Optional.of(new Patient()));

        BusinessRuleException e = assertThrows(
                BusinessRuleException.class,
                () -> validator.putValidate(updatedUser)
        );

        assertEquals("E-mail já cadastrado no sistema.", e.getMessage());

        verify(repository).findById(id);
        verify(repository).findByEmail("existing@email.com");
    }

    @Test
    void shouldAcceptTheEmailChange() {
        UUID id = UUID.randomUUID();

        Patient oldUser = new Patient();
        oldUser.setId(id);
        oldUser.setEmail("old@email.com");

        Patient updatedUser = new Patient();
        updatedUser.setId(id);
        updatedUser.setEmail("new@email.com");

        when(repository.findById(id)).thenReturn(Optional.of(oldUser));
        when(repository.findByEmail("new@email.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validator.putValidate(updatedUser));
        verify(repository).findById(id);
        verify(repository).findByEmail("new@email.com");
    }

    @Test
    void shouldNotCheckEmailWhenEmailRemainsTheSame() {
        UUID id = UUID.randomUUID();

        Patient oldUser = new Patient();
        oldUser.setId(id);
        oldUser.setEmail("user@email.com");

        Patient updatedUser = new Patient();
        updatedUser.setId(id);
        updatedUser.setEmail("user@email.com");

        when(repository.findById(id)).thenReturn(Optional.of(oldUser));

        assertDoesNotThrow(() -> validator.putValidate(updatedUser));
        verify(repository).findById(id);
        verify(repository, never()).findByEmail(anyString());
    }
}