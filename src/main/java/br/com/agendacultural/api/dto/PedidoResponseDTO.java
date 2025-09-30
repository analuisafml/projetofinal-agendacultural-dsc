package br.com.agendacultural.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime dataCompra,
        BigDecimal valorTotal,
        String status,
        List<IngressoCompradoDTO> ingressos
) {}