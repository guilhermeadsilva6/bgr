package br.edu.ifsudestemg.bgr.api.dto.request;

import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;
import jakarta.validation.constraints.NotBlank;

public record MedicalOfficeRequestDTO(
        @NotBlank(message = "O nome do local é um campo obrigatório!")
        String doctorsOffice,

        @NotBlank(message = "O país do local é um campo obrigatório!")
        String country,

        @NotBlank(message = "O estado do local é um campo obrigatório!")
        String state,

        @NotBlank(message = "A cidade do local é um campo obrigatório!")
        String city,

        @NotBlank(message = "O bairro do local é um campo obrigatório!")
        String neighborhood,

        @NotBlank(message = "A rua do local é um campo obrigatório!")
        String street,

        @NotBlank(message = "O número do local é um campo obrigatório!")
        String number,

        String complement
) {
    public MedicalOffice dtoToEntity() {
        MedicalOffice medicalOffice = new MedicalOffice();
        medicalOffice.setDoctorsOffice(this.doctorsOffice);
        medicalOffice.setCountry(this.country);
        medicalOffice.setState(this.state);
        medicalOffice.setCity(this.city);
        medicalOffice.setNeighborhood(this.neighborhood);
        medicalOffice.setStreet(this.street);
        medicalOffice.setNumber(this.number);
        medicalOffice.setComplement(this.complement);
        return medicalOffice;
    }
}
