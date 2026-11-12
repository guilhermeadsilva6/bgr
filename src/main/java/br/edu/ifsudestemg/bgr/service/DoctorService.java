package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import br.edu.ifsudestemg.bgr.model.repository.DoctorRepository;
import br.edu.ifsudestemg.bgr.service.validator.DoctorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;
    private final DoctorValidator validator;

    public void createDoctor(Doctor doctor) {
        validator.validate(doctor);
        repository.save(doctor);
    }

    public List<Doctor> searchForAllDoctors() {
        return repository.findAll();
    }

    public Optional<Doctor> searchForDoctorById(UUID id) {
        return repository.findById(id);
    }

    public void updateDoctor(Doctor doctor) {
        validator.validate(doctor);
        repository.save(doctor);
    }

    public void deleteDoctor(UUID id) {
        repository.deleteById(id);
    }
}
