package com.PicpaySimplificado.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PicpaySimplificado.domain.model.Transaction;
import com.PicpaySimplificado.domain.requestDtos.TransactionRequestDTO;
import com.PicpaySimplificado.domain.services.TransactionService;

@RestController
@RequestMapping("/api/v1/transaction/")
public class TransactionResource {

@Autowired
private TransactionService transactionService;

    @PostMapping("/criar")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequestDTO transaction) throws Exception{
        System.out.println("DENTRO DO CONTROLLER DA TRANSAÇÃO: "+transaction);
        Transaction newTransaction = this.transactionService.createTransaction(transaction);
        
        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    
    }
}
