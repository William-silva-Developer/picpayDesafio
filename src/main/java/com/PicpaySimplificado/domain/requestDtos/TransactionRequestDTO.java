package com.PicpaySimplificado.domain.requestDtos;

import java.math.BigDecimal;

public record TransactionRequestDTO(BigDecimal value, Long senderId, Long receiverId) {
    
    
}
