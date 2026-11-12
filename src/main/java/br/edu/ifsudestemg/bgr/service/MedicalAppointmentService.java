package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.MedicalAppointment;
import br.edu.ifsudestemg.bgr.model.repository.MedicalAppointmentRepository;
import br.edu.ifsudestemg.bgr.service.validator.MedicalAppointmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentService {

    private final MedicalAppointmentRepository repository;
    private final MedicalAppointmentValidator validator;

    public void createMedicalAppointment(MedicalAppointment medicalAppointment) {
        validator.validate(medicalAppointment);
        repository.save(medicalAppointment);
    }

    public List<MedicalAppointment> searchForAllMedicalAppointments() {
        return repository.findAll();
    }

    public Optional<MedicalAppointment> searchForMedicalAppointmentById(UUID id) {
        return repository.findById(id);
    }

    public void updateMedicalAppointment(MedicalAppointment medicalAppointment) {
        validator.validate(medicalAppointment);
        repository.save(medicalAppointment);
    }

    public void deleteMedicalAppointment(UUID id) {
        repository.deleteById(id);
    }
}