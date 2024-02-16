package it.epicode.progettoGestione.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DipendenteRequest  {

    @NotNull(message = "Nome obbligatorio")
    @NotEmpty(message = "il campo del Nome non deve essere vuoto")
    private String nome;

    @NotNull(message = "Nome obbligatorio")
    @NotEmpty(message = "il campo del Cognome non deve essere vuoto")
    private String cognome;

    @NotNull(message = "Nome obbligatorio")
    @NotEmpty(message = "il campo del Username non deve essere vuoto")
    private String username;

    @NotNull(message = "Email obbligatorio")
    @NotEmpty(message = "il campo dell' email non deve essere vuoto")
    @Column(unique = true)
    @Email(message = "Inserire email valida")
    private String email;
}
