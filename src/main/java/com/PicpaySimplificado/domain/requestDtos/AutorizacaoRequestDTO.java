package com.PicpaySimplificado.domain.requestDtos;

import java.math.BigDecimal;

public record AutorizacaoRequestDTO(Long senderId, BigDecimal value) {

}
