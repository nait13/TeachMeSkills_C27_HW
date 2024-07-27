package com.lesson45.dto;

import com.lesson45.model.Card;
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
    private Card cardFrom;
    private BigDecimal amount;
}
