package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.BloodPressureRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.BloodPressureResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.BloodPressure;
import br.edu.ifsudestemg.bgr.service.BloodPressureService;
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
@RequestMapping("pressoes")
@RequiredArgsConstructor
public class BloodPressureController {

    private final BloodPressureService service;

    @PostMapping
    public ResponseEntity createBloodPressureRecord(@Valid @RequestBody BloodPressureRequestDTO requestDTO) {
        try {
            BloodPressure bloodPressure = requestDTO.dtoToEntity();
            service.createBloodPressureRecord(bloodPressure);
            return new ResponseEntity(bloodPressure, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllBloodPressureRecords() {
        List<BloodPressure> bloodPressures = service.searchForAllBloodPressureRecords();
        return ResponseEntity
                .ok(bloodPressures
                        .stream()
                        .map(BloodPressureResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForBloodPressureRecordById(@PathVariable("id") String id) {
        UUID idBloodPressure = UUID.fromString(id);
        Optional<BloodPressure> bloodPressure = service.searchForBloodPressureRecordById(idBloodPressure);

        if (bloodPressure.isEmpty()) {
            return new ResponseEntity("Registro de pressão não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<BloodPressureResponseDTO> responseDTO = bloodPressure.map(BloodPressureResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateBloodPressureRecord(@PathVariable("id") String id, @Valid @RequestBody BloodPressureRequestDTO requestDTO) {
        UUID idBloodPressure = UUID.fromString(id);
        Optional<BloodPressure> bloodPressureFound = service.searchForBloodPressureRecordById(idBloodPressure);
        if (bloodPressureFound.isEmpty()) {
            return new ResponseEntity("Registro de pressão não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            BloodPressure bloodPressure = requestDTO.dtoToEntity();
            bloodPressure.setId(idBloodPressure);
            bloodPressure.setRegistrationDate(bloodPressureFound.get().getRegistrationDate());
            service.updateBloodPressureRecord(bloodPressure);
            return ResponseEntity.ok(bloodPressure);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBloodPressureRecord(@PathVariable("id") String id) {
        UUID idBloodPressure = UUID.fromString(id);
        if (service.searchForBloodPressureRecordById(idBloodPressure).isEmpty()) {
            return new ResponseEntity("Registro de pressão não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteBloodPressureRecord(idBloodPressure);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

