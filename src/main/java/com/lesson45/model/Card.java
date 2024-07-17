package com.lesson45.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class Card {
    private int id;
    private String cardNumber;
    private BigDecimal balance;
}
