package com.joaovbrocchi.authTest.controller;

import com.joaovbrocchi.authTest.entity.user.AuthenticationDTO;
import com.joaovbrocchi.authTest.entity.user.RegisterDTO;
import com.joaovbrocchi.authTest.entity.user.User;
import com.joaovbrocchi.authTest.repository.IUserRepository;
import com.joaovbrocchi.authTest.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity CreateUser(@RequestBody @Validated AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return ResponseEntity
                .ok()
                .build();
    }
    @PostMapping("/resgiter")
    public ResponseEntity registerUser(@RequestBody @Validated RegisterDTO data){
        if(this.userRepository.findByEmail(data.email()) !=null){
            return ResponseEntity.badRequest().build();
        }
        String encryptedpassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.email(), encryptedpassword, data.role());

        this.userRepository.save(user);
        return  ResponseEntity.ok().build();
    }

}
