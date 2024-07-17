package com.lesson45.controller;

import com.lesson45.model.Card;
import com.lesson45.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable int id){
        //Сходит в БД вернуть клиента
        return  new ResponseEntity<>(new Client(), HttpStatus.OK);
    }

}
