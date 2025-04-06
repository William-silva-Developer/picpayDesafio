package com.PicpaySimplificado.domain.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.PicpaySimplificado.api.responseDtos.AutorizacaoResponseDTO;
import com.PicpaySimplificado.domain.model.Transaction;
import com.PicpaySimplificado.domain.model.User;
import com.PicpaySimplificado.domain.repositories.TransactionRepository;
import com.PicpaySimplificado.domain.requestDtos.AutorizacaoRequestDTO;
import com.PicpaySimplificado.domain.requestDtos.TransactionRequestDTO;

import jakarta.transaction.Transactional;
import reactor.core.publisher.Mono;


@Service
public class TransactionService {

    @Value("${api.authorized}")
    private String url;

    private final TransactionRepository transactionRepository;
    
    private final NotificationService notificationService;
    
    private final UserService userService;
    
    private final WebClient webClient;
    
   public TransactionService(TransactionRepository transactionRepository, UserService userService, WebClient webClient, NotificationService notificationService){
    this.transactionRepository = transactionRepository;
    this.userService = userService;
    this.webClient = webClient;
    this.notificationService = notificationService;
   }
   
   
   public boolean verifyAuthrized(Long senderId, BigDecimal value) throws Exception{
    
    AutorizacaoRequestDTO autorizacaoRequestDTO = new AutorizacaoRequestDTO(senderId, value);
    
    //CHAMANDO O ENDPOINT EXTERNO
    Mono<AutorizacaoResponseDTO> responseMono = webClient.post()
        .uri(url)
        .bodyValue(autorizacaoRequestDTO)
        .retrieve()
        .bodyToMono(AutorizacaoResponseDTO.class);
        
        
        AutorizacaoResponseDTO response = responseMono.block();
        
        return response != null && response.authorized();
   
   }
    
    
    @Transactional
    public Transaction createTransaction(TransactionRequestDTO transaction) throws Exception{
        try {
            User sender = this.userService.findUserById(transaction.senderId());    
        User receiver = this.userService.findUserById(transaction.receiverId());
        
        userService.validateTransaction(sender, transaction.value());
        System.out.println("ID DE QUEM ENVIA: "+transaction.senderId());
        boolean authorized = true; //verifyAuthrized(transaction.senderId(), transaction.value());
        System.out.println("AUTORIZAÇÃO: "+authorized);
        if (!authorized) {
            throw new Exception("Transação não autorizada pelo serviço externo.");
        }
        Transaction newTransaction = new Transaction();

        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());


        sender.setBalance(sender.getBalance().subtract(transaction.value())); // SUBTRAI O VALOR DE QUE ESTA ENVIANDO
        receiver.setBalance(receiver.getBalance().add(transaction.value())); // ADICIONA O VALOR NO RECEBEDOR

        //SALVANDO OS DADOS DA TRANSAÇÃO
        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(receiver);
        this.userService.saveUser(sender);
        
        // this.notificationService.sendNotification(receiver, "Transação recebida com sucesso!");
        // this.notificationService.sendNotification(sender, "transação realizada com sucesso!");
        
        return newTransaction;
        } catch (Exception e) {
            throw e;
        }
        
    
    }
    
    
}
