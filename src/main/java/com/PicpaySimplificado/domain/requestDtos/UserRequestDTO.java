package com.PicpaySimplificado.domain.requestDtos;

import java.math.BigDecimal;

import com.PicpaySimplificado.domain.model.UserType;

public record UserRequestDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {

}
