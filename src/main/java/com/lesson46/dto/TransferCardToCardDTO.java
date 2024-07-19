package com.lesson46.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class TransferCardToCardDTO {
    private int clientId;
    private String cardTo;
    private String cardFrom;
    private BigDecimal amount;
}
