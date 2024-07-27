package com.lesson45.util;

import com.lesson45.dto.TransferCardToCardDTO;

import java.math.BigDecimal;

public class Validation {
    public static boolean isValidBalance(TransferCardToCardDTO dto){
        return dto.getCardFrom().getBalance().compareTo(dto.getAmount()) >= 0;
    }

    public static boolean isValidAmount(TransferCardToCardDTO dto) {
        return dto.getAmount().compareTo(BigDecimal.ZERO) > 0;
    }
}
