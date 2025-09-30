package br.com.agendacultural.api.controllers;

import br.com.agendacultural.api.dto.*;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_PRODUTOR')")
    public ResponseEntity<EventoResponseDTO> criar(@RequestBody @Valid EventoRequestDTO dto, Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        EventoResponseDTO eventoCriado = eventoService.criarEvento(dto, usuarioLogado);

        URI location = URI.create("/api/eventos/" + eventoCriado.id());
        return ResponseEntity.created(location).body(eventoCriado);
    }

    @PostMapping("/{eventoId}/sessoes")
    @PreAuthorize("hasAuthority('ROLE_PRODUTOR')")
    public ResponseEntity<SessaoResponseDTO> adicionarSessao(@PathVariable Long eventoId,
                                                             @RequestBody @Valid SessaoRequestDTO dto,
                                                             Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        SessaoResponseDTO sessaoCriada = eventoService.adicionarSessao(eventoId, dto, usuarioLogado);

        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoCriada);
    }

    @GetMapping
    public ResponseEntity<List<EventoResumoDTO>> listar() {
        var eventos = eventoService.listarEventos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDetalheDTO> buscarPorId(@PathVariable Long id) {
        var evento = eventoService.buscarPorId(id);
        return ResponseEntity.ok(evento);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_PRODUTOR')")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id,
                                                       @RequestBody @Valid EventoRequestDTO dto,
                                                       Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        EventoResponseDTO eventoAtualizado = eventoService.atualizarEvento(id, dto, usuarioLogado);
        return ResponseEntity.ok(eventoAtualizado);
    }
}