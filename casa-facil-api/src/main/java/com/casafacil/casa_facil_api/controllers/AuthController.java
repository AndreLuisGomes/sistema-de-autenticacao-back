package com.casafacil.casa_facil_api.controllers;

import com.casafacil.casa_facil_api.dto.LoginRequestDTO;
import com.casafacil.casa_facil_api.dto.RegisterRequestDTO;
import com.casafacil.casa_facil_api.dto.ResponseDTO;
import com.casafacil.casa_facil_api.infra.security.TokenService;
import com.casafacil.casa_facil_api.models.owner.Owner;
import com.casafacil.casa_facil_api.models.renter.Renter;
import com.casafacil.casa_facil_api.services.OwnerService;
import com.casafacil.casa_facil_api.services.RenterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final OwnerService ownerService;
    private final RenterService renterService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body, SecurityContextHolder contextHolder) {

        if(body.password() == null || body.password().isBlank() || body.email() == null || body.email().isBlank()){
            return ResponseEntity.badRequest().body("Erro: Campo nome ou senha nulos!");
        }

        Optional<Renter> renter = this.renterService.findRenterByEmail(body.email());
        Optional<Owner> owner = this.ownerService.findOwnerByEmail(body.email());

        if (renter.isPresent()) {
            if (this.passwordEncoder.matches(body.password(), renter.get().getPassword())) {
                String token = this.tokenService.generateToken(body.email());
                return ResponseEntity.ok(new ResponseDTO(token, renter.get().getName(), renter.get().getRole()));
            }
        }else if (owner.isPresent()) {
            if (this.passwordEncoder.matches(body.password(), owner.get().getPassword())) {
                String token = this.tokenService.generateToken(body.email());
                return ResponseEntity.ok(new ResponseDTO(token, owner.get().getName(), owner.get().getRole()));
            }
        }
        return ResponseEntity.status(404).body("Email e senha incorretos!");
    }

    // Método de registro de usuário (owner ou renter) //

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Owner> owner = this.ownerService.findOwnerByEmail(body.email());
        Optional<Renter> renter = this.renterService.findRenterByEmail(body.email());

        if(owner.isEmpty() && renter.isEmpty()){
            if (body.role().equals("owner")) {
                this.ownerService.saveOwner(body);
                Optional<Owner> owner1 = this.ownerService.findOwnerByEmail(body.email());
                return ResponseEntity.ok().body(owner1);
            } else if(body.role().equals("renter")){
                this.renterService.saveRenter(body);
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já está em uso!");
    }
}

