package com.casafacil.casa_facil_api.controllers;

import com.casafacil.casa_facil_api.domain.user.User;
import com.casafacil.casa_facil_api.domain.user.UserDTO;
import com.casafacil.casa_facil_api.dto.LoginRequestDTO;
import com.casafacil.casa_facil_api.dto.RegisterRequestDTO;
import com.casafacil.casa_facil_api.dto.ResponseDTO;
import com.casafacil.casa_facil_api.infra.security.TokenService;
import com.casafacil.casa_facil_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO body){
        Optional<User> user = this.userRepository.findByEmail(body.email());
        if(user.isEmpty()){
            System.out.println("\n ================ Não existe usuário cadastrado com esse email! ===================\n");
            return ResponseEntity.notFound().build();
        }
        if(passwordEncoder.matches(body.password(), user.get().getPassword())){
            String token = this.tokenService.generateToken(user.get());
            return ResponseEntity.ok(new ResponseDTO(token, user.get().getName()));
        }
        System.out.println("\n ================ Não existe usuário cadastrado com esse senha! ===================\n");
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.userRepository.findByEmail(body.email());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(this.passwordEncoder.encode(body.password()));
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            this.userRepository.save(newUser);
            LoginRequestDTO bodyUser = new LoginRequestDTO(body.email(), body.password());
            return this.login(bodyUser);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email em uso!");
    }

//    @GetMapping("/user")
//    public ResponseEntity<List<User>> getUsers(){
//
//    }
}
