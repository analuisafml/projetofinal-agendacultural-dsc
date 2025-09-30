package br.com.agendacultural.api.controllers;

import br.com.agendacultural.api.dto.EventoResumoDTO;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/me")
public class ProdutorController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/eventos")
    @PreAuthorize("hasRole('PRODUTOR')")
    public ResponseEntity<List<EventoResumoDTO>> meusEventos(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        List<EventoResumoDTO> eventos = eventoService.listarEventosPorProdutor(usuarioLogado);
        return ResponseEntity.ok(eventos);
    }
}