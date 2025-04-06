package com.PicpaySimplificado.domain.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.PicpaySimplificado.domain.model.User;
import com.PicpaySimplificado.domain.model.UserType;
import com.PicpaySimplificado.domain.repositories.UserRepository;
import com.PicpaySimplificado.domain.requestDtos.UserRequestDTO;

import jakarta.transaction.Transactional;

@Service
public class UserService {

@Autowired
private  UserRepository userRepository;

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
    @Transactional
    public User createUser(UserRequestDTO user) throws Exception {
        try {
        System.out.println("SERVICE: "+user);
         User newUser = new User(user);
         User user2 = userRepository.save(newUser);
         return user2;

        } catch (Exception e) {
            throw e;
        }
    }

    public List<User> getAllUsers() {
        try {
            
            List<User> users = userRepository.findAll();
            System.out.println("USUARIOS: "+users);
            return users;
        } catch (Exception e) {
        throw e;
        }
    }


}
