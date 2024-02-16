package it.epicode.progettoGestione.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class CustomResponse {
    private String message;
    private LocalDateTime dateResponse;
    private Object response;

    public CustomResponse(String message, Object response) {
        this.message = message;
        this.response = response;
        dateResponse = LocalDateTime.now();
    }

    public CustomResponse(String message) {
        this.message = message;
        dateResponse = LocalDateTime.now();
    }

    public static ResponseEntity<CustomResponse> emptyResponse(String message, HttpStatus httpStatus){
        CustomResponse dipendenteResponse = new CustomResponse(message);
        return new ResponseEntity<>(dipendenteResponse, httpStatus);
    }

    public static ResponseEntity<CustomResponse> success(String message, Object obj, HttpStatus httpStatus){
        CustomResponse dipendenteResponse = new CustomResponse(message, obj);
        return new ResponseEntity<>(dipendenteResponse, httpStatus);
    }
    public static ResponseEntity<CustomResponse> success(Object obj, HttpStatus httpStatus){
        CustomResponse dipendenteResponse = new CustomResponse(httpStatus.toString(), obj);
        return new ResponseEntity<>(dipendenteResponse, httpStatus);
    }
    public static ResponseEntity<CustomResponse> error(String message, HttpStatus httpStatus){
        CustomResponse dipendenteResponse = new CustomResponse(message);
        return new ResponseEntity<>(dipendenteResponse, httpStatus);
    }
    public static ResponseEntity<CustomResponse> error(HttpStatus httpStatus) {
        CustomResponse dipendenteResponse = new CustomResponse(httpStatus.toString());
        return new ResponseEntity<>(dipendenteResponse, httpStatus);
    }
}
