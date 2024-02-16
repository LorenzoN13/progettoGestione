package it.epicode.progettoGestione.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Dipendente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nome;
    private String cognome;
    private String username;
    private String email;

    private String immagineProfilo;

    @OneToMany(mappedBy = "dipendente")
    private List<Dispositivo> dispositivi;
}
