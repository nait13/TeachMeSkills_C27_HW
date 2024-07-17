package com.lesson45.controller;

import com.lesson45.model.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {

    @GetMapping("{id}")
    public ResponseEntity<Card> getCardInfo(@PathVariable int id){
        //TODO сходить в бд вернуть карту
        return new ResponseEntity<>(new Card(), HttpStatus.OK);
    }
}
