package it.epicode.progettoGestione.controller;

import it.epicode.progettoGestione.exception.NotFoundException;
import it.epicode.progettoGestione.model.CustomResponse;
import it.epicode.progettoGestione.model.DispositivoRequest;
import it.epicode.progettoGestione.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("/dispositivi")
    public ResponseEntity<CustomResponse> getAll(Pageable pageable){
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.getAll(pageable), HttpStatus.OK);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/dispositivi/{id}")
    public ResponseEntity<CustomResponse> getDispositivoById(@PathVariable int id){
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.getDispositivoById(id), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/dispositivi")
    public ResponseEntity<CustomResponse> saveDispositivo(@RequestBody @Validated DispositivoRequest dispositivoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).toList().toString(), HttpStatus.BAD_REQUEST);
        }

        try{
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.saveDispositivo(dispositivoRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/dispositivi/{id}")
    public ResponseEntity<CustomResponse> updateDispositivo(@PathVariable int id, @RequestBody @Validated DispositivoRequest autoRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return CustomResponse.error(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }
        try{
            return CustomResponse.success(HttpStatus.OK.toString(), dispositivoService.updateDispositivo(id, autoRequest), HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/dispositivi/{id}")
    public ResponseEntity<CustomResponse> deleteDispositivo(@PathVariable int id){

        dispositivoService.deleteDispositivo(id);
        try{
            return CustomResponse.emptyResponse("Dispositivo con id=" + id + " cancellata", HttpStatus.OK);
        }
        catch (NotFoundException e){
            return CustomResponse.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return CustomResponse.error(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
