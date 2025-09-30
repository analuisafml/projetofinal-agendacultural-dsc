package br.com.agendacultural.api.controllers;

import br.com.agendacultural.api.dto.LoginRequestDTO;
import br.com.agendacultural.api.dto.LoginResponseDTO;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dados) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());

        Authentication authentication = manager.authenticate(authenticationToken);

        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();

        String tokenJWT = tokenService.gerarToken(usuarioAutenticado);

        return ResponseEntity.ok(new LoginResponseDTO(tokenJWT));
    }
}