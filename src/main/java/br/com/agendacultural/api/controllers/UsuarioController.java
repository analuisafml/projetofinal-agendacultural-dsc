package br.com.agendacultural.api.controllers;

import br.com.agendacultural.api.dto.UsuarioCadastroDTO;
import br.com.agendacultural.api.models.Perfil;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastro/consumidor")
    public ResponseEntity<Usuario> cadastrarConsumidor(@Valid @RequestBody UsuarioCadastroDTO dados) {
        Usuario usuarioSalvo = usuarioService.cadastrar(dados, Perfil.ROLE_USUARIO);

        URI location = URI.create("/api/usuarios/" + usuarioSalvo.getId());
        return ResponseEntity.created(location).body(usuarioSalvo);
    }

    @PostMapping("/cadastro/produtor")
    public ResponseEntity<Usuario> cadastrarProdutor(@Valid @RequestBody UsuarioCadastroDTO dados) {
        Usuario usuarioSalvo = usuarioService.cadastrar(dados, Perfil.ROLE_PRODUTOR);

        URI location = URI.create("/api/usuarios/" + usuarioSalvo.getId());
        return ResponseEntity.created(location).body(usuarioSalvo);
    }
}