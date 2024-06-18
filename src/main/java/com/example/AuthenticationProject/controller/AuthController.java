package com.example.AuthenticationProject.controller;

import com.example.AuthenticationProject.dto.AuthenticateRequest;
import com.example.AuthenticationProject.entity.User;
import com.example.AuthenticationProject.service.AuthService;
import com.example.AuthenticationProject.dto.AuthenticationResponse;
import com.example.AuthenticationProject.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application/v1/auth")
@RequiredArgsConstructor
@Tag(name = "User" , description = "User Management Api ")
public class AuthController {
    private final AuthService authService;
    @Operation(
            summary = "Registering a user in a Database",
            description = "Get a Tutorial ofregistering a user in database.",
            tags = { "tutorials" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) throws Exception {
        AuthenticationResponse authenticateResponse = authService.register(registerRequest);
        return ResponseEntity.ok(authenticateResponse);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest){
        AuthenticationResponse authenticationResponse = authService.authenticate(authenticateRequest);
        return  ResponseEntity.ok(authenticationResponse) ;
    }

}
