package it.epicode.progettoGestione.service;

import it.epicode.progettoGestione.exception.NotFoundException;
import it.epicode.progettoGestione.model.Dipendente;
import it.epicode.progettoGestione.model.DipendenteRequest;
import it.epicode.progettoGestione.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    public Page<Dipendente> cercaTuttiIDipendenti(Pageable pageable){
        return dipendenteRepository.findAll(pageable);
    }

    public Dipendente cercaDipendentePerId(int id){
        return dipendenteRepository.findById(id).
                orElseThrow(()->new NotFoundException("Dipendente con id="+ id + " non trovato"));
    }

    public Dipendente salvaDipendente(DipendenteRequest dipendenteRequest){
        Dipendente dipendente = new Dipendente();
        dipendente.setNome(dipendenteRequest.getNome());
        dipendente.setCognome(dipendenteRequest.getCognome());
        dipendente.setUsername(dipendenteRequest.getUsername());
        dipendente.setEmail(dipendenteRequest.getEmail());

        sendMail(dipendente.getEmail());

        return dipendenteRepository.save(dipendente);
    }

    public Dipendente aggiornaDipendente(int id, DipendenteRequest dipendenteRequest) throws NotFoundException{
        Dipendente d = cercaDipendentePerId(id);

        d.setNome(dipendenteRequest.getNome());
        d.setCognome(dipendenteRequest.getCognome());
        d.setUsername(dipendenteRequest.getUsername());
        d.setEmail(dipendenteRequest.getEmail());


        return dipendenteRepository.save(d);
    }

    public void cancellaDipendente(int id) throws NotFoundException{
        Dipendente d = cercaDipendentePerId(id);

        dipendenteRepository.delete(d);
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");
        message.setText("Registrazione al servizio rest avvenuta con successo");

        javaMailSender.send(message);
    }


    public Dipendente uploadImmagine(int id, String url) throws NotFoundException{
        Dipendente dipendente = cercaDipendentePerId(id);

        dipendente.setImmagineProfilo(url);

        return dipendenteRepository.save(dipendente);
    }


}
