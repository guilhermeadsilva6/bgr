package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.WaterIntakeRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.WaterIntakeResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.WaterIntake;
import br.edu.ifsudestemg.bgr.service.WaterIntakeService;
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
@RequestMapping("consumo-de-agua")
@RequiredArgsConstructor
public class WaterIntakeController {

    private final WaterIntakeService service;

    @PostMapping
    public ResponseEntity createWaterIntakeRecord(@Valid @RequestBody WaterIntakeRequestDTO requestDTO) {
        try {
            WaterIntake waterIntake = requestDTO.dtoToEntity();
            service.createWaterIntakeRecord(waterIntake);
            return new ResponseEntity(waterIntake, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllWaterIntakeRecords() {
        List<WaterIntake> waterIntakes = service.searchForAllWaterIntakeRecords();
        return ResponseEntity
                .ok(waterIntakes
                        .stream()
                        .map(WaterIntakeResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForWaterIntakeRecordById(@PathVariable("id") String id) {
        UUID idWaterIntake = UUID.fromString(id);
        Optional<WaterIntake> waterIntake = service.searchForWaterIntakeRecordById(idWaterIntake);

        if (waterIntake.isEmpty()) {
            return new ResponseEntity("Registro de consumo de água não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<WaterIntakeResponseDTO> responseDTO = waterIntake.map(WaterIntakeResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateWaterIntakeRecord(@PathVariable("id") String id, @Valid @RequestBody WaterIntakeRequestDTO requestDTO) {
        UUID idWaterIntake = UUID.fromString(id);
        Optional<WaterIntake> waterIntakeFound = service.searchForWaterIntakeRecordById(idWaterIntake);
        if (waterIntakeFound.isEmpty()) {
            return new ResponseEntity("Registro de consumo de água não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            WaterIntake waterIntake = requestDTO.dtoToEntity();
            waterIntake.setId(idWaterIntake);
            service.updateWaterIntakeRecord(waterIntake);
            return ResponseEntity.ok(waterIntake);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteWaterIntakeRecord(@PathVariable("id") String id) {
        UUID idWaterIntake = UUID.fromString(id);
        if (service.searchForWaterIntakeRecordById(idWaterIntake).isEmpty()) {
            return new ResponseEntity("Registro de consumo de água não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteWaterIntakeRecord(idWaterIntake);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

