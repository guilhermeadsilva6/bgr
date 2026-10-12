package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.BloodSugarRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.BloodSugarResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.BloodSugar;
import br.edu.ifsudestemg.bgr.service.BloodSugarService;
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
@RequestMapping("glicoses")
@RequiredArgsConstructor
public class BloodSugarController {

    private final BloodSugarService service;

    @PostMapping
    public ResponseEntity createBloodSugarRecord(@Valid @RequestBody BloodSugarRequestDTO requestDTO) {
        try {
            BloodSugar bloodSugar = requestDTO.dtoToEntity();
            service.createBloodSugarRecord(bloodSugar);
            return new ResponseEntity(bloodSugar, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllBloodSugarRecords() {
        List<BloodSugar> bloodSugars = service.searchForAllBloodSugarRecords();
        return ResponseEntity
                .ok(bloodSugars
                        .stream()
                        .map(BloodSugarResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForBloodSugarRecordById(@PathVariable("id") String id) {
        UUID idBloodSugar = UUID.fromString(id);
        Optional<BloodSugar> bloodSugar = service.searchForBloodSugarRecordById(idBloodSugar);

        if (!bloodSugar.isPresent()) {
            return new ResponseEntity("Registro de glicose não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<BloodSugarResponseDTO> responseDTO = bloodSugar.map(BloodSugarResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateBloodSugarRecord(@PathVariable("id") String id, @Valid @RequestBody BloodSugarRequestDTO requestDTO) {
        UUID idBloodSugar = UUID.fromString(id);
        if (service.searchForBloodSugarRecordById(idBloodSugar).isEmpty()) {
            return new ResponseEntity("Registro de glicose não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            BloodSugar bloodSugar = requestDTO.dtoToEntity();
            bloodSugar.setId(idBloodSugar);
            service.updateBloodSugarRecord(bloodSugar);
            return ResponseEntity.ok(bloodSugar);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBloodSugarRecord(@PathVariable("id") String id) {
        UUID idBloodSugar = UUID.fromString(id);
        if (service.searchForBloodSugarRecordById(idBloodSugar).isEmpty()) {
            return new ResponseEntity("Registro de glicose não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteBloodSugarRecord(idBloodSugar);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

