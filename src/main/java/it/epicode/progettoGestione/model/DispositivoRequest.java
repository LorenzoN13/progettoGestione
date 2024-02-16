package it.epicode.progettoGestione.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DispositivoRequest {
    @NotNull(message = "contenuto obbligatorio")
    private Tipologie tipo;

    @NotNull(message = "stato obbligatorio")
    private Stato stato;

    @NotNull(message = "Dispositivo obbligatorio")
    private Integer idDispositivo;
}
