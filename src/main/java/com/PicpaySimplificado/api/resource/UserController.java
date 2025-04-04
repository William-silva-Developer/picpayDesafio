package com.PicpaySimplificado.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.PicpaySimplificado.domain.model.User;
import com.PicpaySimplificado.domain.requestDtos.UserRequestDTO;
import com.PicpaySimplificado.domain.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v2")
public class UserController {

    @Autowired
    private UserService userService;

    
    
    @PostMapping("/criar")
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO user) throws Exception{
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
    

}
