package com.PicpaySimplificado.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PicpaySimplificado.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
