package br.com.agendacultural.api.services;

import br.com.agendacultural.api.dto.UsuarioCadastroDTO;
import br.com.agendacultural.api.exceptions.EmailJaCadastradoException;
import br.com.agendacultural.api.models.Perfil;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario cadastrar(UsuarioCadastroDTO dados, Perfil perfil) {
        if (usuarioRepository.findByEmail(dados.email()).isPresent()) {
            throw new EmailJaCadastradoException("O e-mail '" + dados.email() + "' já está em uso.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dados.nome());
        novoUsuario.setEmail(dados.email());
        novoUsuario.setSenha(passwordEncoder.encode(dados.senha()));
        novoUsuario.setPerfis(Set.of(perfil));

        return usuarioRepository.save(novoUsuario);
    }
}