package com.example.AuthenticationProject.service;

import com.example.AuthenticationProject.dto.AuthenticationResponse;
import com.example.AuthenticationProject.dto.AuthenticateRequest;
import com.example.AuthenticationProject.dto.RegisterRequest;
import com.example.AuthenticationProject.entity.User;
import com.example.AuthenticationProject.exception.ConflictException;
import com.example.AuthenticationProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) throws Exception {
        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        //check whether the email already exists on the database or not. If exists, thrown an exception telling that the username already exists.
        String mail = user.getEmail();
        Optional<User> findBymail = userRepository.findByEmail(mail);
        if(!findBymail.isPresent()){
            var saveduser = userRepository.save(user);
        }else{
            throw new ConflictException("User Already Exists in Database ");
//            throw new Exception("User Not Found");
        }
//        if(!userRepository.exists(mail)){
//            var saveduser = userRepository.save(user);
//        }

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().accessToken(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(),authenticateRequest.getPassword())

        );//if this call fails, an exception will be thrown.

        var user = userRepository.findByEmail(authenticateRequest.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(token).build();
    }

//    public ResponseEntity<HttpStatus> delete(Long id ,String token) throws Exception {
//
//        Optional<User> findUserById = userRepository.findById(id);
//        if(findUserById.isPresent()){
//            userRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        else{
//            throw new Exception("Id doesnot exsists please enter a valid id ");
//        }
//
//    }
}
