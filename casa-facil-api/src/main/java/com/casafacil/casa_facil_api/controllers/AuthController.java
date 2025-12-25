package com.casafacil.casa_facil_api.controllers;

import com.casafacil.casa_facil_api.domain.user.Owner;
import com.casafacil.casa_facil_api.domain.user.Renter;
import com.casafacil.casa_facil_api.dto.LoginRequestDTO;
import com.casafacil.casa_facil_api.dto.RegisterRequestDTO;
import com.casafacil.casa_facil_api.dto.ResponseDTO;
import com.casafacil.casa_facil_api.infra.security.TokenService;
import com.casafacil.casa_facil_api.services.OwnerService;
import com.casafacil.casa_facil_api.services.RenterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final OwnerService ownerService;
    private final RenterService renterService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {

        Optional<Owner> owner = this.ownerService.findOwnerByEmail(body.email());

        if (owner.isPresent()) {
            if (this.passwordEncoder.matches(body.password(), owner.get().getPassword())) {
                String token = this.tokenService.generateToken(body.email());
                return ResponseEntity.ok().body(new ResponseDTO(token, owner.get().getName(), owner.get().getRole()));
            }
        }

        Optional<Renter> renter = this.renterService.findRenterByEmail(body.email());

        if (renter.isPresent()) {
            if (this.passwordEncoder.matches(body.password(), renter.get().getPassword())) {
                String token = this.tokenService.generateToken(body.email());
                return ResponseEntity.ok().body(new ResponseDTO(token, renter.get().getName(), renter.get().getRole()));
            }
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().build();
    }


//        Optional<Owner> owner = this.userService.getOwner(body.email());
//
//        Optional<Renter> renter = this.userService.getRenter(body.email());
//
//        if(owner.isPresent()){
//           if(passwordEncoder.matches(body.password(), owner.get().getPassword())){
//               String token = this.tokenService.generateToken(body.email());
//               List<Role> newRole = new ArrayList<>();
//               newRole.add(new Role("owner"));
//               return ResponseEntity.ok(new ResponseDTO(token, owner.get().getName(), newRole));
//           }
//        }
//        if(renter.isPresent()){
//            if(passwordEncoder.matches(body.password(), renter.get().getPassword())){
//                String token = this.tokenService.generateToken(body.email());
//                List<Role> newRole = new ArrayList<>();
//                newRole.add(new Role("renter"));
//                return ResponseEntity.ok(new ResponseDTO(token, renter.get().getName(), newRole));
//            }
//        }
//        return ResponseEntity.notFound().build();


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Owner> owner = this.ownerService.findOwnerByEmail(body.email());
        Optional<Renter> renter = this.renterService.findRenterByEmail(body.email());

        if(owner.isEmpty() && renter.isEmpty()){
            if (body.role().equals("owner")) {
                this.ownerService.saveOwner(body);
            } else if(body.role().equals("renter")){
                this.renterService.saveRenter(body);
            }
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().build();
    }
}
//        Optional<User> user = this.userRepository.findByEmail(body.email());
//        if(user.isEmpty()){
//            System.out.println("\n ================ Não existe usuário cadastrado com esse email! ===================\n");
//            return ResponseEntity.notFound().build();
//        }
//        if(passwordEncoder.matches(body.password(), user.get().getPassword())){
//            String token = this.tokenService.generateToken(user.get());
//            return ResponseEntity.ok(new ResponseDTO(token, user.get().getName(), ));
//        }
//        System.out.println("\n ================ Não existe usuário cadastrado com esse senha! ===================\n");
//        return ResponseEntity.badRequest().build();
