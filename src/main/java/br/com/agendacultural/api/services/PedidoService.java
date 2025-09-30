package br.com.agendacultural.api.services;

import br.com.agendacultural.api.dto.CheckoutRequestDTO;
import br.com.agendacultural.api.dto.IngressoCompradoDTO;
import br.com.agendacultural.api.dto.PedidoResponseDTO;
import br.com.agendacultural.api.exceptions.EstoqueInsuficienteException;
import br.com.agendacultural.api.exceptions.ResourceNotFoundException;
import br.com.agendacultural.api.models.IngressoComprado;
import br.com.agendacultural.api.models.Pedido;
import br.com.agendacultural.api.models.TipoIngresso;
import br.com.agendacultural.api.models.Usuario;
import br.com.agendacultural.api.repositories.PedidoRepository;
import br.com.agendacultural.api.repositories.TipoIngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;

    @Transactional
    public PedidoResponseDTO criarPedido(Long sessaoId, CheckoutRequestDTO dto, Usuario usuarioLogado) {
        TipoIngresso tipoIngresso = tipoIngressoRepository.findById(dto.tipoIngressoId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de ingresso não encontrado."));

        if (!tipoIngresso.getSessao().getId().equals(sessaoId)) {
            throw new IllegalArgumentException("O tipo de ingresso não pertence à sessão informada.");
        }

        int ingressosDisponiveis = tipoIngresso.getQuantidadeTotal() - tipoIngresso.getQuantidadeVendida();
        if (ingressosDisponiveis < dto.quantidade()) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o tipo de ingresso: " + tipoIngresso.getTipo());
        }

        tipoIngresso.setQuantidadeVendida(tipoIngresso.getQuantidadeVendida() + dto.quantidade());

        Pedido novoPedido = new Pedido();
        novoPedido.setUsuario(usuarioLogado);
        novoPedido.setDataCompra(LocalDateTime.now());
        novoPedido.setStatus("CONFIRMADO");
        novoPedido.setValorTotal(tipoIngresso.getPreco().multiply(new BigDecimal(dto.quantidade())));

        List<IngressoComprado> ingressosComprados = new ArrayList<>();
        for (int i = 0; i < dto.quantidade(); i++) {
            IngressoComprado ingresso = new IngressoComprado();
            ingresso.setPedido(novoPedido);
            ingresso.setTipoIngresso(tipoIngresso);
            ingresso.setQrCodeUnico(UUID.randomUUID().toString());
            ingressosComprados.add(ingresso);
        }
        novoPedido.setIngressos(ingressosComprados);

        Pedido pedidoSalvo = pedidoRepository.save(novoPedido);

        List<IngressoCompradoDTO> ingressosDTO = pedidoSalvo.getIngressos().stream()
                .map(ing -> new IngressoCompradoDTO(ing.getId(), ing.getTipoIngresso().getTipo(), ing.getQrCodeUnico()))
                .collect(Collectors.toList());

        return new PedidoResponseDTO(pedidoSalvo.getId(), pedidoSalvo.getDataCompra(), pedidoSalvo.getValorTotal(), pedidoSalvo.getStatus(), ingressosDTO);
    }
}