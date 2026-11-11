package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.WeightRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.WeightResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Weight;
import br.edu.ifsudestemg.bgr.service.WeightService;
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
@RequestMapping("pesos")
@RequiredArgsConstructor
public class WeightController {

    private final WeightService service;

    @PostMapping
    public ResponseEntity createWeightRecord(@Valid @RequestBody WeightRequestDTO requestDTO) {
        try {
            Weight weight = requestDTO.dtoToEntity();
            service.createWeightRecord(weight);
            return new ResponseEntity(weight, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllWeightRecords() {
        List<Weight> weights = service.searchForAllWeightRecords();
        return ResponseEntity
                .ok(weights
                        .stream()
                        .map(WeightResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForWeightRecordById(@PathVariable("id") String id) {
        UUID idWeight = UUID.fromString(id);
        Optional<Weight> weight = service.searchForWeightRecordById(idWeight);

        if (weight.isEmpty()) {
            return new ResponseEntity("Registro de peso não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<WeightResponseDTO> responseDTO = weight.map(WeightResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateWeightRecord(@PathVariable("id") String id, @Valid @RequestBody WeightRequestDTO requestDTO) {
        UUID idWeight = UUID.fromString(id);
        Optional<Weight> weightFound = service.searchForWeightRecordById(idWeight);
        if (weightFound.isEmpty()) {
            return new ResponseEntity("Registro de peso não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            Weight weight = requestDTO.dtoToEntity();
            weight.setId(idWeight);
            weight.setRegistrationDate(weightFound.get().getRegistrationDate());
            service.updateWeightRecord(weight);
            return ResponseEntity.ok(weight);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteWeightRecord(@PathVariable("id") String id) {
        UUID idWeight = UUID.fromString(id);
        if (service.searchForWeightRecordById(idWeight).isEmpty()) {
            return new ResponseEntity("Registro de peso não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteWeightRecord(idWeight);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

