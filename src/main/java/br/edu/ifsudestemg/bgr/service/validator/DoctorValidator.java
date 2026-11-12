package br.edu.ifsudestemg.bgr.service.validator;

import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import br.edu.ifsudestemg.bgr.model.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DoctorValidator {

    private final DoctorRepository repository;

    public void validate(Doctor doctor) {

    }
}
