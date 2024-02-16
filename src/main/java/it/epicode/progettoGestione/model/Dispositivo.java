package it.epicode.progettoGestione.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private Tipologie tipo;

    @Enumerated(EnumType.STRING)
    private Stato stato;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;
}
