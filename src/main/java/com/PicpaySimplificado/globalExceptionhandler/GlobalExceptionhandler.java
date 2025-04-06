package com.PicpaySimplificado.globalExceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionhandler {

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity threaDuplEntity(DataIntegrityViolationException exception){
            ExeceptionDTO execeptionDTO = new ExeceptionDTO("Usuário já cadastrado.", "400");
            return ResponseEntity.badRequest().body(execeptionDTO);
        
        }
        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity threaDupl404(EntityNotFoundException exception){
            return ResponseEntity.notFound().build();
            
        }
        
        @ExceptionHandler(Exception.class)
        public ResponseEntity threaGeralException(Exception exception){
            ExeceptionDTO execeptionDTO = new ExeceptionDTO(exception.getMessage(), "500");
            return ResponseEntity.internalServerError().body(execeptionDTO);
        
        }

}
