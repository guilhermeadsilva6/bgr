package br.edu.ifsudestemg.bgr.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Patient extends Person{
    private String email;
    private String password; //preciso fazer hash para salvar

    @Column(name = "birth_date")
    private LocalDate birthDate;
}
