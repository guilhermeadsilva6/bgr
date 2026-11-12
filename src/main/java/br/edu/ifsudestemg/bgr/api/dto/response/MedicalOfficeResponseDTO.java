package br.edu.ifsudestemg.bgr.api.dto.response;

import br.edu.ifsudestemg.bgr.model.entity.MedicalOffice;

public record MedicalOfficeResponseDTO(
        String doctorsOffice,
        String country,
        String state,
        String city,
        String neighborhood,
        String street,
        String number,
        String complement
) {
    public static MedicalOfficeResponseDTO entityToDto(MedicalOffice medicalOffice) {
        return new MedicalOfficeResponseDTO(
                medicalOffice.getDoctorsOffice(),
                medicalOffice.getCountry(),
                medicalOffice.getState(),
                medicalOffice.getCity(),
                medicalOffice.getNeighborhood(),
                medicalOffice.getStreet(),
                medicalOffice.getNumber(),
                medicalOffice.getComplement()
        );
    }
}
