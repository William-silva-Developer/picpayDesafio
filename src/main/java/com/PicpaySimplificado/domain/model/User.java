package com.PicpaySimplificado.domain.model;

import java.math.BigDecimal;

import com.PicpaySimplificado.domain.requestDtos.UserRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "usuarios")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    
    private String lastname;
    
    @Column(unique = true)
    private String document;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private UserType userType;


    public User(UserRequestDTO data){
        this.firstName = data.firstName();
        this.lastname = data.lastName();
        this.balance = data.balance();
        this.userType = data.userType();
        this.password = data.password();
        this.email = data.email();
    }

}
