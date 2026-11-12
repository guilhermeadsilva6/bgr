package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.PatientRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.PatientResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("pacientes")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @PostMapping
    public ResponseEntity createPatient(@Valid @RequestBody PatientRequestDTO requestDTO) {
        try {
            Patient patient = requestDTO.dtoToEntity();
            service.createPatient(patient);
            return new ResponseEntity(patient, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllPatients() {
        List<Patient> patients = service.searchForAllPatients();
        return ResponseEntity
                .ok(patients
                        .stream()
                        .map(PatientResponseDTO::entityToDto)
                .collect(Collectors
                        .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForPatientById(@PathVariable("id") String id) {
        UUID idPatient = UUID.fromString(id);
        Optional<Patient> patient = service.searchForPatientById(idPatient);

        if (patient.isEmpty()) {
            return new ResponseEntity("Paciente não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<PatientResponseDTO> responseDTO = patient.map(PatientResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updatePatient(@PathVariable("id") String id, @Valid @RequestBody PatientRequestDTO requestDTO) {
        UUID idPatient = UUID.fromString(id);
        if (service.searchForPatientById(idPatient).isEmpty()) {
            return new ResponseEntity("Paciente não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            Patient patient = requestDTO.dtoToEntity();
            patient.setId(idPatient);
            service.updatePatient(patient);
            return ResponseEntity.ok(patient);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletePatient(@PathVariable("id") String id) {
        UUID idPatient = UUID.fromString(id);
        if (service.searchForPatientById(idPatient).isEmpty()) {
            return new ResponseEntity("Paciente não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deletePatient(idPatient);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
