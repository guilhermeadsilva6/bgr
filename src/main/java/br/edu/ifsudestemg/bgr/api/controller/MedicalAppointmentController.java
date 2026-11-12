package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.MedicalAppointmentRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.MedicalAppointmentResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.MedicalAppointment;
import br.edu.ifsudestemg.bgr.service.MedicalAppointmentService;
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
@RequestMapping("consultas-medicas")
@RequiredArgsConstructor
public class MedicalAppointmentController {

    private final MedicalAppointmentService service;

    @PostMapping
    public ResponseEntity createMedicalAppointment(@Valid @RequestBody MedicalAppointmentRequestDTO requestDTO) {
        try {
            MedicalAppointment medicalAppointment = requestDTO.dtoToEntity();
            service.createMedicalAppointment(medicalAppointment);
            return new ResponseEntity(medicalAppointment, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllMedicalAppointments() {
        List<MedicalAppointment> medicalAppointments = service.searchForAllMedicalAppointments();
        return ResponseEntity
                .ok(medicalAppointments
                        .stream()
                        .map(MedicalAppointmentResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForMedicalAppointmentById(@PathVariable("id") String id) {
        UUID idMedicalAppointment = UUID.fromString(id);
        Optional<MedicalAppointment> medicalAppointment = service.searchForMedicalAppointmentById(idMedicalAppointment);

        if (medicalAppointment.isEmpty()) {
            return new ResponseEntity("Registro de consulta médica não encontada", HttpStatus.NOT_FOUND);
        }

        Optional<MedicalAppointmentResponseDTO> responseDTO = medicalAppointment.map(MedicalAppointmentResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateMedicalAppointmentRecord(@PathVariable("id") String id, @Valid @RequestBody MedicalAppointmentRequestDTO requestDTO) {
        UUID idMedicalAppointment = UUID.fromString(id);
        Optional<MedicalAppointment> medicalAppointmentFound = service.searchForMedicalAppointmentById(idMedicalAppointment);
        if (medicalAppointmentFound.isEmpty()) {
            return new ResponseEntity("Registro de consulta médica não encontrada!", HttpStatus.NOT_FOUND);
        }
        try {
            MedicalAppointment medicalAppointment = requestDTO.dtoToEntity();
            medicalAppointment.setId(idMedicalAppointment);
            service.updateMedicalAppointment(medicalAppointment);
            return ResponseEntity.ok(medicalAppointment);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteMedicalAppointment(@PathVariable("id") String id) {
        UUID idMedicalAppointment = UUID.fromString(id);
        if (service.searchForMedicalAppointmentById(idMedicalAppointment).isEmpty()) {
            return new ResponseEntity("Registro de consulta médica não encontrada!", HttpStatus.NOT_FOUND);
        }
        service.deleteMedicalAppointment(idMedicalAppointment);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

