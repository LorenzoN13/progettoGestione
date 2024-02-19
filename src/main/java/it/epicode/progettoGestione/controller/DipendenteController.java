package it.epicode.progettoGestione.controller;

import com.cloudinary.Cloudinary;
import it.epicode.progettoGestione.exception.BadRequestException;
import it.epicode.progettoGestione.exception.NotFoundException;
import it.epicode.progettoGestione.model.CustomResponse;
import it.epicode.progettoGestione.model.Dipendente;
import it.epicode.progettoGestione.model.DipendenteRequest;
import it.epicode.progettoGestione.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private Cloudinary cloudinary;
    @GetMapping("/dipendenti")
    public Page<Dipendente> getAll(Pageable pageable){

        return dipendenteService.cercaTuttiIDipendenti(pageable);
    }
    @GetMapping("/dipendenti/{id}")
    public Dipendente getDipendenteById(@PathVariable int id){
        return dipendenteService.cercaDipendentePerId(id);

    }
    @PostMapping("/dipendenti")
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return dipendenteService.salvaDipendente(dipendenteRequest);
    }
    @PutMapping("/dipendenti/{id}")
    public Dipendente updateDipendente(@PathVariable int id, @RequestBody @Validated  DipendenteRequest dipendenteRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        return dipendenteService.aggiornaDipendente(id, dipendenteRequest);
    }

    @DeleteMapping("/dipendenti/{id}")
    public void deleteDipendente(@PathVariable int id){
        dipendenteService.cancellaDipendente(id);
    }

    @PatchMapping("/dipendenti/{id}/upload")
    public Dipendente uploadAvatar(@PathVariable int id, @RequestParam("upload") MultipartFile file) throws IOException {
        return dipendenteService.uploadImmagine(id,
                (String) cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));

    }
}
