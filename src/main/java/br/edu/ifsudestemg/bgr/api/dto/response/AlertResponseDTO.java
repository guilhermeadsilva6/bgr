package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.Alert;
import br.edu.ifsudestemg.bgr.model.enums.AlertStatus;
import br.edu.ifsudestemg.bgr.model.enums.AlertType;

import java.time.LocalDateTime;

public record AlertResponseDTO(String description,
                               AlertType alertType,
                               LocalDateTime triggerDateTime,
                               AlertStatus alertStatus,
                               String patientName) {

    public static AlertResponseDTO entityToDto(Alert alert) {
        return new AlertResponseDTO(
                alert.getDescription(),
                alert.getAlertType(),
                alert.getTriggerDateTime(),
                alert.getAlertStatus(),
                alert.getPatient().getName()
        );
    }
}
