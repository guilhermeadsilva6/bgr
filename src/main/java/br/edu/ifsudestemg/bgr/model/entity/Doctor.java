package br.edu.ifsudestemg.bgr.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Doctor extends Person {
    private String specialty;
}
