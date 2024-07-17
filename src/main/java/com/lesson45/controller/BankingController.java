package com.lesson45.controller;

import com.lesson45.dto.TransferCardToCardDTO;
import com.lesson45.model.Client;
import com.lesson45.service.BankingService;
import com.lesson45.util.AppError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/banking")
public class BankingController {

    @Autowired
    BankingService bankingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable int id){
        Client client = null;
        try {
            client = bankingService.getUserById(id);
            return  new ResponseEntity<>(client, HttpStatus.OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> transfer(@RequestBody TransferCardToCardDTO dto){
        System.out.println("DTO " + dto);
        try {
            bankingService.transfer(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(new AppError(400,e.getMessage()),HttpStatus.BAD_REQUEST);
        }catch (SQLException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
