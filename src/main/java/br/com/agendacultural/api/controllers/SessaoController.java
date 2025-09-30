package br.com.agendacultural.api.controllers;

import br.com.agendacultural.api.dto.TipoIngressoRequestDTO;
import br.com.agendacultural.api.dto.TipoIngressoResponseDTO;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.services.SessaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/{sessaoId}/tipos-ingresso")
    @PreAuthorize("hasAuthority('ROLE_PRODUTOR')")
    public ResponseEntity<TipoIngressoResponseDTO> adicionarTipoIngresso(@PathVariable Long sessaoId,
                                                                         @RequestBody @Valid TipoIngressoRequestDTO dto,
                                                                         Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        TipoIngressoResponseDTO tipoIngressoCriado = sessaoService.adicionarTipoIngresso(sessaoId, dto, usuarioLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoIngressoCriado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PRODUTOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        sessaoService.deletarSessao(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}