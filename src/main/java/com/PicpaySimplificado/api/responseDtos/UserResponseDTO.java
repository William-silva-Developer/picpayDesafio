package com.PicpaySimplificado.api.responseDtos;

import java.math.BigDecimal;

import com.PicpaySimplificado.domain.model.UserType;

public record UserResponseDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {

}
