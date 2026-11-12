package br.edu.ifsudestemg.bgr.service;

import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;
import br.edu.ifsudestemg.bgr.model.repository.MedicalOfficeRepository;
import br.edu.ifsudestemg.bgr.service.validator.MedicalOfficeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalOfficeService {

    private final MedicalOfficeRepository repository;
    private final MedicalOfficeValidator validator;

    public void createMedicalOffice(MedicalOffice medicalOffice) {
        validator.validate(medicalOffice);
        repository.save(medicalOffice);
    }

    public List<MedicalOffice> searchForAllMedicalOffice() {
        return repository.findAll();
    }

    public Optional<MedicalOffice> searchForMedicalOfficeById(UUID id) {
        return repository.findById(id);
    }

    public void updateMedicalOffice(MedicalOffice medicalOffice) {
        validator.validate(medicalOffice);
        repository.save(medicalOffice);
    }

    public void deleteMedicalOffice(UUID id) {
        repository.deleteById(id);
    }
}
