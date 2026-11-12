package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.MedicalOfficeRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.MedicalOfficeResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;
import br.edu.ifsudestemg.bgr.service.MedicalOfficeService;
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
@RequestMapping("consultorios_medicos")
@RequiredArgsConstructor
public class MedicalOfficeController {

    private final MedicalOfficeService service;

    @PostMapping
    public ResponseEntity createMedicalOffice(@Valid @RequestBody MedicalOfficeRequestDTO requestDTO) {
        try {
            MedicalOffice medicalOffice = requestDTO.dtoToEntity();
            service.createMedicalOffice(medicalOffice);
            return new ResponseEntity(medicalOffice, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllMedicalOffice() {
        List<MedicalOffice> medicalOffices = service.searchForAllMedicalOffice();
        return ResponseEntity
                .ok(medicalOffices
                        .stream()
                        .map(MedicalOfficeResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForMedicalOfficeById(@PathVariable("id") String id) {
        UUID idMedicalOffice = UUID.fromString(id);
        Optional<MedicalOffice> medicalOffice = service.searchForMedicalOfficeById(idMedicalOffice);

        if (medicalOffice.isEmpty()) {
            return new ResponseEntity("Consultório médico não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<MedicalOfficeResponseDTO> responseDTO = medicalOffice.map(MedicalOfficeResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateMedicalOffice(@PathVariable("id") String id, @Valid @RequestBody MedicalOfficeRequestDTO requestDTO) {
        UUID idMedicalOffice = UUID.fromString(id);
        Optional<MedicalOffice> medicalOfficeFound = service.searchForMedicalOfficeById(idMedicalOffice);
        if (medicalOfficeFound.isEmpty()) {
            return new ResponseEntity("Consultório médico não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            MedicalOffice medicalOffice = requestDTO.dtoToEntity();
            medicalOffice.setId(idMedicalOffice);
            service.updateMedicalOffice(medicalOffice);
            return ResponseEntity.ok(medicalOffice);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteMedicalOffice(@PathVariable("id") String id) {
        UUID idMedicalOffice = UUID.fromString(id);
        if (service.searchForMedicalOfficeById(idMedicalOffice).isEmpty()) {
            return new ResponseEntity("Consultório médico não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteMedicalOffice(idMedicalOffice);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

