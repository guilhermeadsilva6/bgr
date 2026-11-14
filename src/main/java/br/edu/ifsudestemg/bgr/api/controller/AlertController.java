package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.AlertRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.AlertResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Alert;
import br.edu.ifsudestemg.bgr.service.AlertService;
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
@RequestMapping("alertas")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService service;

    @PostMapping
    public ResponseEntity createAlertRecord(@Valid @RequestBody AlertRequestDTO requestDTO) {
        try {
            Alert alert = requestDTO.dtoToEntity();
            service.createAlertRecord(alert);
            return new ResponseEntity(alert, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllAlertRecords() {
        List<Alert> alerts = service.searchForAllAlertRecords();
        return ResponseEntity
                .ok(alerts
                        .stream()
                        .map(AlertResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForAlertRecordById(@PathVariable("id") String id) {
        UUID idAlert = UUID.fromString(id);
        Optional<Alert> alert = service.searchForAlertRecordById(idAlert);

        if (alert.isEmpty()) {
            return new ResponseEntity("Alerta não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<AlertResponseDTO> responseDTO = alert.map(AlertResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateAlertRecord(@PathVariable("id") String id, @Valid @RequestBody AlertRequestDTO requestDTO) {
        UUID idAlert = UUID.fromString(id);
        Optional<Alert> alertFound = service.searchForAlertRecordById(idAlert);
        if (alertFound.isEmpty()) {
            return new ResponseEntity("Alerta não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            Alert alert = requestDTO.dtoToEntity();
            alert.setId(idAlert);
            alert.setCreationDateTime(alertFound.get().getCreationDateTime());
            service.updateAlertRecord(alert);
            return ResponseEntity.ok(alert);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAlertRecord(@PathVariable("id") String id) {
        UUID idAlert = UUID.fromString(id);
        if (service.searchForAlertRecordById(idAlert).isEmpty()) {
            return new ResponseEntity("Alerta não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteAlertRecord(idAlert);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

