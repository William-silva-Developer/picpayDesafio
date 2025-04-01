package com.PicpaySimplificado.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "transactions")
@Table(name = "transactions")
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal amount;
    @ManyToOne //UM USUARIO PODE TER VARIAS TRANSAÇÕES MAS UMA TRANSAÇÃO NÃO PODE TER VÁRIOS USUÁRIOS
    @JoinColumn(name = "sender_id")
    private User sender;
    
    @ManyToOne //UM USUARIO PODE TER VARIAS TRANSAÇÕES MAS UMA TRANSAÇÃO NÃO PODE TER VÁRIOS USUÁRIOS
    @JoinColumn(name = "receiver_id")
    private User receiver;
    
    private LocalDateTime timestamp;

}
