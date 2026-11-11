package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.MedicineRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.MedicineResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Medicine;
import br.edu.ifsudestemg.bgr.service.MedicineService;
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
@RequestMapping("medicamentos")
@RequiredArgsConstructor
public class MedicineController {

    public final MedicineService service;

    @PostMapping
    public ResponseEntity createMedicationRecord(@Valid @RequestBody MedicineRequestDTO requestDTO) {
        try {
            Medicine medicine = requestDTO.dtoToEntity();
            service.createMedicationRecord(medicine);
            return new ResponseEntity(medicine, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllMedicationRecords() {
        List<Medicine> medicines = service.searchForAllMedicationRecords();
        return ResponseEntity
                .ok(medicines
                        .stream()
                        .map(MedicineResponseDTO::entityToDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForMedicationRecordById(@PathVariable("id") String id) {
        UUID idMedicine = UUID.fromString(id);
        Optional<Medicine> medicine = service.searchForMedicationRecordById(idMedicine);

        if (medicine.isEmpty()) {
            return new ResponseEntity("Registro de medicamento não encontrado", HttpStatus.NOT_FOUND);
        }

        Optional<MedicineResponseDTO> responseDTO = medicine.map(MedicineResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateMedicationRecord(@PathVariable("id") String id, @Valid @RequestBody MedicineRequestDTO requestDTO) {
        UUID idMedicine = UUID.fromString(id);
        Optional<Medicine> medicineFound = service.searchForMedicationRecordById(idMedicine);
        if (medicineFound.isEmpty()) {
            return new ResponseEntity("Registro de medicamento não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            Medicine medicine = requestDTO.dtoToEntity();
            medicine.setId(idMedicine);
            medicine.setRegistrationDate(medicineFound.get().getRegistrationDate());
            service.updateMedicationRecord(medicine);
            return ResponseEntity.ok(medicine);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteMedicationRecord(@PathVariable("id") String id) {
        UUID idMedicine = UUID.fromString(id);
        if (service.searchForMedicationRecordById(idMedicine).isEmpty()) {
            return new ResponseEntity("Registro de medicamento não encontrado", HttpStatus.NOT_FOUND);
        }
        service.deleteMedicationRecord(idMedicine);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*
    @DeleteMapping("{id}")
    public ResponseEntity deleteBloodPressureRecord(@PathVariable("id") String id) {
        UUID idBloodPressure = UUID.fromString(id);
        if (service.searchForBloodPressureRecordById(idBloodPressure).isEmpty()) {
            return new ResponseEntity("Registro de pressão não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteBloodPressureRecord(idBloodPressure);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }*/
}
