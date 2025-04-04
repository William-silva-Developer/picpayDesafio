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

import reactor.core.publisher.Mono;


@Service
public class TransactionService {

    @Value("${api.authorized}")
    private String url;

    private final TransactionRepository transactionRepository;
    
    private final UserService userService;
    
    private final WebClient webClient;
    
   public TransactionService(TransactionRepository transactionRepository, UserService userService, WebClient webClient){
    this.transactionRepository = transactionRepository;
    this.userService = userService;
    this.webClient = webClient;
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
    
    
    
    public void createTransaction(TransactionRequestDTO transaction) throws Exception{
        User sender = this.userService.findUserById(transaction.senderId());    
        User receiver = this.userService.findUserById(transaction.receiverId());
        
        userService.validateTransaction(sender, transaction.value());
        
        boolean authorized = verifyAuthrized(transaction.senderId(), transaction.value());
        
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
        
    
    }
    
    
}
