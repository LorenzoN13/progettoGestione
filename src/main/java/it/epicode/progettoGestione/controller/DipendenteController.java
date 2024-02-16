package it.epicode.progettoGestione.controller;

import com.cloudinary.Cloudinary;
import it.epicode.progettoGestione.exception.BadRequestException;
import it.epicode.progettoGestione.exception.NotFoundException;
import it.epicode.progettoGestione.model.CustomResponse;
import it.epicode.progettoGestione.model.Dipendente;
import it.epicode.progettoGestione.model.DipendenteRequest;
import it.epicode.progettoGestione.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<CustomResponse> getAll(Pageable pageable) {

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), dipendenteService.cercaTuttiIDipendenti(pageable), HttpStatus.OK);
        } catch (Exception e) {
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/dipendenti/{id}")
    public ResponseEntity<CustomResponse> getDipendenteById(@PathVariable int id) {

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), dipendenteService.cercaDipendentePerId(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/dipendenti")
    public ResponseEntity<CustomResponse> savePersona(@RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult)  {
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);

        }

        try{
            return CustomResponse.success(HttpStatus.OK.toString(), dipendenteService.salvaDipendente(dipendenteRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/dipendenti/{id}")
    public ResponseEntity<CustomResponse> updateDipendente(@PathVariable int id, @RequestBody @Validated DipendenteRequest dipendenteRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        try {
            return CustomResponse.success(HttpStatus.OK.toString(), dipendenteService.aggiornaDipendente(id, dipendenteRequest), HttpStatus.OK);
        } catch (NotFoundException e) {
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/dipendenti/{id}")
    public ResponseEntity<CustomResponse> deleteDipendente(@PathVariable int id) {

        try {
            dipendenteService.cancellaDipendente(id);
            return CustomResponse.emptyResponse("Dipendente con id=" + id + " cancellata", HttpStatus.OK);
        } catch (NotFoundException e) {
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return CustomResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("/dipendenti/{id}/upload")
    public ResponseEntity<CustomResponse> uploadImmagineProfilo(@PathVariable int id,@RequestParam("upload") MultipartFile file){
        try {

            Dipendente dipendente = dipendenteService.uploadImmagine(id, (String) cloudinary.uploader().upload(file.getBytes(), new HashMap()).get("url"));
            return CustomResponse.success(HttpStatus.OK.toString(), dipendente,HttpStatus.OK);

        }catch (IOException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
