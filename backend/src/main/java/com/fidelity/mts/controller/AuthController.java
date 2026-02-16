package com.fidelity.mts.controller;
 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
 
@RestController
public class AuthController {
 
    @CrossOrigin(origins = "http://localhost:4200")  
    @GetMapping("/auth")
    public ResponseEntity<String> authenticate() {
        return ResponseEntity.ok("Authenticated");
    }
}