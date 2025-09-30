package br.com.agendacultural.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testes")
public class TesteController {

    @GetMapping("/protegido")
    public ResponseEntity<String> rotaProtegida() {
        return ResponseEntity.ok("Você acessou uma rota protegida!");
    }
}