package it.epicode.progettoGestione.service;

import it.epicode.progettoGestione.exception.NotFoundException;
import it.epicode.progettoGestione.model.Dipendente;
import it.epicode.progettoGestione.model.Dispositivo;
import it.epicode.progettoGestione.model.DispositivoRequest;
import it.epicode.progettoGestione.repository.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;



    @Autowired
    private DipendenteService dipendenteService;

    public Page<Dispositivo> getAll(Pageable pageable){
        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo getDispositivoById(int id) throws NotFoundException {
        return dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException("Dispositivo con id=" + id + " non trovata"));
    }

    public Dispositivo saveDispositivo(DispositivoRequest dispositivoRequest) throws NotFoundException {

        Dipendente dipendente = dipendenteService.cercaDipendentePerId(dispositivoRequest.getIdDispositivo());

        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setTipo(dispositivoRequest.getTipo());
        dispositivo.setStato(dispositivo.getStato());
        dispositivo.setDipendente(dipendente);

        return dispositivoRepository.save(dispositivo);

    }

    public Dispositivo updateDispositivo(int id, DispositivoRequest dispositivoRequest) throws NotFoundException {
        Dispositivo dispositivo = getDispositivoById(id);

        Dipendente dipendente = dipendenteService.cercaDipendentePerId(dispositivoRequest.getIdDispositivo());

        dispositivo.setTipo(dispositivoRequest.getTipo());
        dispositivo.setStato(dispositivo.getStato());
        dispositivo.setDipendente(dipendente);

        return dispositivoRepository.save(dispositivo);

    }

    public void deleteDispositivo(int id) throws NotFoundException {
        Dispositivo dispositivo = getDispositivoById(id);

        dispositivoRepository.delete(dispositivo);
    }

}
