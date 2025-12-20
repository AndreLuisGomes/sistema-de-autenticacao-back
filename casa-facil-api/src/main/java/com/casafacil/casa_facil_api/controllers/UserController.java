package com.casafacil.casa_facil_api.controllers;

import com.casafacil.casa_facil_api.domain.user.User;
import com.casafacil.casa_facil_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping
    public ResponseEntity<Object> getUsersByName(@PathVariable(required = false) String name){
        List<User> user = repository.findByName(name);
        if(user.isEmpty()){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.ok(user);
    }


}
