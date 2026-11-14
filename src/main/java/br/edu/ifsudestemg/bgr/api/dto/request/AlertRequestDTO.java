package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.Alert;
import br.edu.ifsudestemg.bgr.model.entity.Patient;
import br.edu.ifsudestemg.bgr.model.enums.AlertStatus;
import br.edu.ifsudestemg.bgr.model.enums.AlertType;

import java.time.LocalDateTime;
import java.util.UUID;

public record AlertRequestDTO(
        String description,
        AlertType alertType,
        LocalDateTime triggerDateTime,
        AlertStatus alertStatus,
        String idPatient
) {

    public Alert dtoToEntity() {
        Alert alert = new Alert();
        alert.setDescription(this.description);
        alert.setAlertType(this.alertType);
        alert.setTriggerDateTime(this.triggerDateTime);
        alert.setAlertStatus(this.alertStatus);

        Patient patient = new Patient();
        patient.setId(UUID.fromString(this.idPatient));
        alert.setPatient(patient);

        return alert;
    }
}
