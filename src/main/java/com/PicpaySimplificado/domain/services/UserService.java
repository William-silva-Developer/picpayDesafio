package com.PicpaySimplificado.domain.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.PicpaySimplificado.domain.model.User;
import com.PicpaySimplificado.domain.model.UserType;
import com.PicpaySimplificado.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
    
        if(sender.getUserType() == UserType.MERCHANT){
            throw new Exception("Usuário do tipo logista não esta autorizado a realizar transações!");
        }
        
        if(sender.getBalance().compareTo(amount) <= 0 ){
            throw new Exception("Saldo insuficiente!");
        }
    
    }
    
    public User findUserById(Long id) throws Exception{
        
        return this.userRepository.findById(id).orElseThrow(()->  new Exception("Usuário não encontrado!"));
    }
    @Transactional
    public void saveUser(User user){
    this.userRepository.save(user);
    }


}
