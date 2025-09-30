package br.com.agendacultural.api.controllers;

import br.com.agendacultural.api.dto.CheckoutRequestDTO;
import br.com.agendacultural.api.dto.PedidoResponseDTO;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/sessoes/{sessaoId}/checkout")
    @PreAuthorize("hasAuthority('ROLE_USUARIO')")
    public ResponseEntity<PedidoResponseDTO> checkout(@PathVariable Long sessaoId,
                                                      @RequestBody @Valid CheckoutRequestDTO dto,
                                                      Authentication authentication) {

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        PedidoResponseDTO pedido = pedidoService.criarPedido(sessaoId, dto, usuarioLogado);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
}