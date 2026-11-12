package br.edu.ifsudestemg.bgr.api.controller;

import br.edu.ifsudestemg.bgr.api.dto.request.DoctorRequestDTO;
import br.edu.ifsudestemg.bgr.api.dto.response.DoctorResponseDTO;
import br.edu.ifsudestemg.bgr.exception.BusinessRuleException;
import br.edu.ifsudestemg.bgr.model.entity.Doctor;
import br.edu.ifsudestemg.bgr.service.DoctorService;
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
@RequestMapping("doutores")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;

    @PostMapping
    public ResponseEntity createDoctor(@Valid @RequestBody DoctorRequestDTO requestDTO) {
        try {
            Doctor doctor = requestDTO.dtoToEntity();
            service.createDoctor(doctor);
            return new ResponseEntity(doctor, HttpStatus.CREATED);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity searchForAllDoctors() {
        List<Doctor> doctors = service.searchForAllDoctors();
        return ResponseEntity
                .ok(doctors
                        .stream()
                        .map(DoctorResponseDTO::entityToDto)
                        .collect(Collectors
                                .toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity searchForDoctorById(@PathVariable("id") String id) {
        UUID idDoctor = UUID.fromString(id);
        Optional<Doctor> doctor = service.searchForDoctorById(idDoctor);

        if (doctor.isEmpty()) {
            return new ResponseEntity("Doutor não encontado", HttpStatus.NOT_FOUND);
        }

        Optional<DoctorResponseDTO> responseDTO = doctor.map(DoctorResponseDTO::entityToDto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity updateDoctor(@PathVariable("id") String id, @Valid @RequestBody DoctorRequestDTO requestDTO) {
        UUID idDoctor = UUID.fromString(id);
        if (service.searchForDoctorById(idDoctor).isEmpty()) {
            return new ResponseEntity("Doutor não encontrado!", HttpStatus.NOT_FOUND);
        }
        try {
            Doctor doctor = requestDTO.dtoToEntity();
            doctor.setId(idDoctor);
            service.updateDoctor(doctor);
            return ResponseEntity.ok(doctor);
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteDoctor(@PathVariable("id") String id) {
        UUID idDoctor = UUID.fromString(id);
        if (service.searchForDoctorById(idDoctor).isEmpty()) {
            return new ResponseEntity("Doutor não encontrado!", HttpStatus.NOT_FOUND);
        }
        service.deleteDoctor(idDoctor);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
