/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.roosevelt.controller;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author smarudwar
 */
@ControllerAdvice
public class MyExceptionHandler {
    //add this line to your code as is:
    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);
    
    
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("We have an exception: (MethodArgumentNotValidException)");
        
        
        //let's process
        ValidationErrorMessage vem = new ValidationErrorMessage();
        
        BindingResult br = e.getBindingResult();
        
        
        for (FieldError fe : br.getFieldErrors()) {
            FieldErrorMessage fem = new FieldErrorMessage();
            fem.setField(fe.getField());
            fem.setMessage(fe.getDefaultMessage());
            vem.addFieldErrorMessage(fem);
        }
        
        
        
        return new ResponseEntity(vem,HttpStatus.BAD_REQUEST);
        

    }
    
    
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        logger.warn("We have an exception (handleConstraintViolationException): " + e.getMessage());
        
        return new ResponseEntity<>("" + e, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleOtherException(Exception e) {
        logger.warn("We have an exception (" + e.getClass() + "): " + e.getMessage());
        return new ResponseEntity<>("" + e, HttpStatus.BAD_REQUEST);
    }
    
}
