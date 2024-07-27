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

import static com.lesson45.util.Validation.isValidAmount;
import static com.lesson45.util.Validation.isValidBalance;

@RestController
@RequestMapping("/banking")
public class BankingController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable int id){
        try {
            Client client = bankingService.getUserById(id);
            if (client != null) {
                return  new ResponseEntity<>(client, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/transfer",consumes = "application/json")
    public ResponseEntity<?> transfer(@RequestBody TransferCardToCardDTO dto){
        System.out.println(dto);

        if(!isValidAmount(dto)) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Сумма трансфера не может быть отрицательной."), HttpStatus.BAD_REQUEST);
        }
        if(!isValidBalance(dto)){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "На карте не достаточно средств"), HttpStatus.BAD_REQUEST);
        }
        try {
            bankingService.transfer(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (SQLException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
