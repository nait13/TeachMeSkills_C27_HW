package com.lesson45.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class Card {
    private int id;
    private int clientId;
    private double balance;
    private String cardNumber;
    private CardTypes cardTypes;
}
