package it.epicode.progettoGestione.repository;

import it.epicode.progettoGestione.model.Dipendente;

import it.epicode.progettoGestione.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer>, PagingAndSortingRepository<Dispositivo, Integer> {
}
